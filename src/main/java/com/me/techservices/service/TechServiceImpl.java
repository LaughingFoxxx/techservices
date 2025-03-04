package com.me.techservices.service;

import com.me.techservices.dto.request.RequestBookingDTO;
import com.me.techservices.dto.request.RequestOperatorDTO;
import com.me.techservices.dto.request.RequestServiceDTO;
import com.me.techservices.dto.response.ResponseRevenueByDateDTO;
import com.me.techservices.entity.Booking;
import com.me.techservices.entity.Operator;
import com.me.techservices.entity.Service;
import com.me.techservices.entity.User;
import com.me.techservices.exception.ServiceException;
import com.me.techservices.mapper.MapperDTOToEntity;
import com.me.techservices.repository.BookingRepository;
import com.me.techservices.repository.OperatorRepository;
import com.me.techservices.repository.ServiceRepository;
import com.me.techservices.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class TechServiceImpl implements TechService {
    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final OperatorRepository operatorRepository;
    private final UserRepository userRepository;
    private final MapperDTOToEntity dtoMapper;
    private final JavaMailSender javaMailSender;

    @Value("{mail.name.from}")
    String from;

    private SimpleMailMessage createSimpleMessage(String message, String subject, String[] setTo){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("admin@yandex.ru");
        mailMessage.setTo(setTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        return mailMessage;
    }

    @Override
    public Booking createBooking(RequestBookingDTO bookingDTO) {
        String userEmail = bookingDTO.userDTO().email();

        javaMailSender
                .send(createSimpleMessage("Добрый день, " +
                        bookingDTO.userDTO().name() +
                        ". Спасибо, что воспользовались нашими услугами. Ваша бронь готова.",
                        "Уведомление о забронированной услуге. От кого: " + from,
                        new String[]{userEmail}));

        return bookingRepository.save(dtoMapper.mapRequestBookingDTOToBookingEntity(bookingDTO));
    }

    @Override
    public Service createService(RequestServiceDTO serviceDTO) {
        return dtoMapper.mapRequestServiceDTOToServiceEntity(serviceDTO);
    }

    @Override
    public Service updateService(int id, RequestServiceDTO serviceDTO) {
        Service service = serviceRepository
                .findById((long) id)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Указанная услуга не найдена"));

        service.setName(serviceDTO.name());
        service.setDescription(serviceDTO.description());
        service.setPrice(BigDecimal.valueOf(Long.parseLong(serviceDTO.price())));

        serviceRepository.save(service);

        return service;
    }

    @Override
    public List<Service> getServiceList() {
        return serviceRepository.findAll();
    }

    @Override
    public Service getServiceById(int id) {
        return serviceRepository
                .findById((long) id)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Указанная услуга не найдена"));
    }

    @Override
    public void cancelServiceBooking(long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Пользователь не найден."));

        bookingRepository.deleteAllByUserId(userId);

        String userEmail = user.getEmail();

        javaMailSender
                .send(createSimpleMessage("Добрый день, " +
                                user.getName() +
                                ". Спасибо, что воспользовались нашими услугами. Все ваши брони были удалены.",
                        "Уведомление о забронированной услуге. От кого: " + from,
                        new String[]{userEmail}));
    }

    @Override
    public Booking getBookingByDateTime(LocalDateTime bookingDateTime) {
        return bookingRepository.getBookingByBookedTime(bookingDateTime);
    }

    @Override
    public List<ResponseRevenueByDateDTO> getRevenueByDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findRevenueByDate(startDate, endDate);
    }

    @Override
    public Operator createOperator(RequestOperatorDTO operatorDTO) {
        operatorRepository.save(dtoMapper.mapRequestOperatorDTOToOperatorEntity(operatorDTO));
        return operatorRepository.findOperatorByLastName(operatorDTO.lastName());
    }

    @Override
    public Operator updateOperator(int id, RequestOperatorDTO operatorDTO) {
        Operator operator = operatorRepository
                .findById((long) id)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Указанный оператор не найден"));

        operator.setName(operatorDTO.name());
        operator.setLastName(operatorDTO.lastName());

        operatorRepository.save(operator);
        return operator;
    }

    @Override
    public Booking getBookingForUser(Long userId, Long bookingId) {
        return bookingRepository
                .findByIdAndUserId(userId, bookingId)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Указанный оператор не найден"));
    }

    @Override
    public Booking updateBookingOfUser(Long userId, Long bookingId, RequestBookingDTO bookingDTO) {
        Booking existingBooking = getBookingForUser(userId, bookingId);

        existingBooking.setStatus(bookingDTO.status());
        existingBooking.setBookedTime(LocalDateTime.parse(bookingDTO.bookedTime()));

        bookingRepository.save(existingBooking);

        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Указанный оператор не найден"));
        String userEmail = user.getEmail();

        javaMailSender
                .send(createSimpleMessage("Добрый день, " +
                                user.getName() +
                                ". Спасибо, что воспользовались нашими услугами. Ваша бронь обновлена.",
                        "Уведомление о забронированной услуге. От кого: " + from,
                        new String[]{userEmail}));

        return existingBooking;
    }

    @Override
    public Long updateAllBookingsDiscounts(String targetDiscount) {
        List<Booking> list = getAllBookingsList();

        long affectedBookingsCount = 0L;
        for (Booking booking : list) {
            booking.setDiscount(targetDiscount);
            affectedBookingsCount += 1;
        }

        return affectedBookingsCount;
    }

    @Override
    public Long deleteAllBookingsDiscounts() {
        List<Booking> list = getAllBookingsList();

        long affectedBookingsCount = 0L;
        for (Booking booking : list) {
            booking.setDiscount("0%");
            affectedBookingsCount += 1;
        }

        return affectedBookingsCount;
    }

    @Override
    public List<Booking> getAllBookingsList() {
        return bookingRepository.findAll();
    }

    @Override
    public List<User> giveDiscountToAllCancelledUsers(String message) {
        List<User> users = userRepository.findAll();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getBookings() != null) {
                users.remove(i);
            }
        }

        String[] userEmails = users
                .stream()
                .map(User::getEmail)
                .toArray(String[]::new);

        javaMailSender
                .send(createSimpleMessage(message,
                        "От кого: " + from,
                        userEmails));
        return users;
    }
}
