package com.me.techservices.service.security_service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTUtils {

    @Value("${security.jwtSecret}")
    private String jwtSecret;

    @Value("${security.jwtRefreshSecret}")
    private String jwtRefreshSecret;

    @Value("${security.jwtSecretExpiration}")
    private long jwtExpirationMs;


    @Qualifier("JWTProcessor")
    private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;

    @Qualifier("JWEEncrypter")
    private final JWEEncrypter encrypter;

    @Autowired
    public JWTUtils(ConfigurableJWTProcessor<SecurityContext> jwtProcessor, JWEEncrypter encrypter) {
        this.jwtProcessor = jwtProcessor;
        this.encrypter = encrypter;
    }

    public String generateToken(UserDetails user) throws JOSEException {
        JWEObject jweObject = createJweObject(user.getUsername(), "additional user info");
        encryptJwe(jweObject);
        return jweObject.serialize();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public String getSubject(String token) throws ParseException, JOSEException, BadJOSEException {
        JWTClaimsSet claimsSet = jwtProcessor.process(token, null);
        return claimsSet.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws ParseException, JOSEException, BadJOSEException {
        String username = getSubject(token);
        Date expiration = extractClaims(token).getExpirationTime();
        return username.equals(userDetails.getUsername()) && expiration.after(new Date());
    }

    public String getClaim(String token, String claimKey) throws ParseException, JOSEException, BadJOSEException {
        JWTClaimsSet claims = extractClaims(token);
        return claims.getStringClaim(claimKey);
    }

    @Bean("JWEEncrypter")
    public JWEEncrypter createEncrypter() throws KeyLengthException {
        return new DirectEncrypter(jwtSecret.getBytes());
    }

    @Bean("JWTProcessor")
    public ConfigurableJWTProcessor<SecurityContext> createJwtProcessor() {
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

        // Источник ключа (симметричный ключ)
        JWKSource<SecurityContext> keySource = new ImmutableSecret<>(jwtSecret.getBytes());

        // Настройка JWE (расшифровка)
        JWEKeySelector<SecurityContext> jweKeySelector = new JWEDecryptionKeySelector<>(
                JWEAlgorithm.DIR,
                EncryptionMethod.A128CBC_HS256,
                keySource
        );
        jwtProcessor.setJWEKeySelector(jweKeySelector);

        // Настройка JWS
        JWSKeySelector<SecurityContext> jwsKeySelector = new JWSVerificationKeySelector<>(
                JWSAlgorithm.HS256,
                keySource
        );
        jwtProcessor.setJWSKeySelector(jwsKeySelector);

        return jwtProcessor;
    }

    private JWEObject createJweObject(String subject, String additionalInfo) throws JOSEException {
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM)
                .contentType("JWT")
                .build();
        Payload payload = new Payload(signJwt(subject, additionalInfo, jwtSecret));
        return new JWEObject(header, payload);
    }

    private JWTClaimsSet createPayload(String subject, String additionalInfo) {
        return new JWTClaimsSet.Builder()
                .subject(subject)
                .claim("city", additionalInfo)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .build();
    }

    private SignedJWT signJwt(String subject, String additionalInfo, String jwtSecret) throws JOSEException {
        JWSSigner signer = new MACSigner(jwtSecret.getBytes());

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                createPayload(subject, additionalInfo)
        );

        signedJWT.sign(signer);
        return signedJWT;
    }

    private void encryptJwe(JWEObject jweObject) throws JOSEException {
        jweObject.encrypt(encrypter);
    }

    private JWTClaimsSet extractClaims(String token) throws ParseException, JOSEException, BadJOSEException {
        return jwtProcessor.process(token, null);
    }


}
