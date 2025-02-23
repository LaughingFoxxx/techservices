package com.me.techservices.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@Getter
@Setter
public class ServiceException extends ResponseStatusException { //класс исключения (ДЗ №8)
    public ServiceException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
