package com.me.techservices.controller;

import com.me.techservices.dto.request.RequestBookingDTO;
import com.me.techservices.dto.request.RequestOperatorDTO;
import com.me.techservices.dto.request.RequestServiceDTO;
import com.me.techservices.dto.response.ResponseRevenueByDateDTO;
import com.me.techservices.entity.Booking;
import com.me.techservices.entity.Operator;
import com.me.techservices.entity.Service;
import com.me.techservices.service.TechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tech-service") //контроллер по ДЗ №3
public class TechServicesController {

    private final TechService techService;

    //Создание брони на услугу сервиса (ДЗ №3)
    @Secured("ROLE_USER")
    @PostMapping(value = "/create-booking", produces = "application/json")
    ResponseEntity<Booking> bookService(@RequestBody RequestBookingDTO createBookingDTO) {
        log.info("POST request. Service: {}", createBookingDTO);
        return new ResponseEntity<>(techService.createBooking(createBookingDTO), HttpStatus.OK);
    }

    //создание услуги (ДЗ №3)
    @Secured("ROLE_USER")
    @PostMapping(value = "/create-service", produces = "application/json")
    ResponseEntity<Service> createService(@RequestBody RequestServiceDTO serviceDTO) {
        log.info("POST request. Service: {}", serviceDTO);
        return new ResponseEntity<>(techService.createService(serviceDTO), HttpStatus.OK);
    }

    //редактирование услуги (ДЗ №3)
    @Secured("ROLE_USER")
    @PutMapping(value = "/update-service", produces = "application/json")
    ResponseEntity<Service> updateService(@RequestParam int id, @RequestBody RequestServiceDTO serviceDTO) {
        log.info("PUT request. Service: {}", id);
        return new ResponseEntity<>(techService.updateService(id, serviceDTO), HttpStatus.OK);
    }

    //получение списка услуг (ДЗ №3)
    @Secured("ROLE_USER")
    @GetMapping(value = "/get-services-list", produces = "application/json")
    ResponseEntity<List<Service>> getServiceList() {
        List<Service> result = techService.getServiceList();
        log.info("GET request. Service: {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //получение услуги по идентификатору (ДЗ №3)
    @Secured("ROLE_USER")
    @GetMapping(value = "/get-service", produces = "application/json")
    ResponseEntity<Service> getServiceById(@RequestParam int id) {
        log.info("GET request. Service: {}", id);
        return new ResponseEntity<>(techService.getServiceById(id), HttpStatus.OK);
    }

    //отмена брони услуги (ДЗ №4)
    @Secured("ROLE_USER")
    @DeleteMapping(value = "/delete-service", produces = "application/json")
    ResponseEntity<Service> cancelServiceBooking(@RequestParam int id) {
        log.info("DELETE request. Service: {}", id);
        techService.cancelServiceBooking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //получение брони по дате и времени записи (ДЗ №8)
    @Secured("ROLE_USER")
    @GetMapping(value = "/get-booking-by-datetime", produces = "application/json")
    ResponseEntity<Booking> getBookingByDateTime(@RequestParam LocalDateTime dateTime) {
        log.info("GET request. Service: {}", dateTime);
        return new ResponseEntity<>(techService.getBookingByDateTime(dateTime), HttpStatus.OK);
    }

    //получение выручки за определенный период времени (ДЗ №8)
    @Secured("ROLE_USER")
    @GetMapping(value = "/get-revenue-by-datetime", produces = "application/json")
    ResponseEntity<List<ResponseRevenueByDateDTO>> getRevenueByDateTime(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        log.info("GET request. Service: startDate={}, endDate={}", startDate, endDate);
        return new ResponseEntity<>(techService.getRevenueByDateTime(startDate, endDate), HttpStatus.OK);
    }

    //создание оператора (ДЗ №9)
    @Secured("ROLE_USER")
    @PostMapping(value = "/create-operator", produces = "application/json")
    ResponseEntity<Operator> createOperator(@RequestBody RequestOperatorDTO requestOperatorDTO) {
        log.info("POST request. Service: {}", requestOperatorDTO);
        return new ResponseEntity<>(techService.createOperator(requestOperatorDTO), HttpStatus.OK);
    }

    //редактирование оператора (ДЗ №9)
    @Secured("ROLE_USER")
    @PutMapping(value = "/update-operator", produces = "application/json")
    ResponseEntity<Operator> updateOperator(@RequestParam int id, @RequestBody RequestOperatorDTO operatorDTO) {
        log.info("PUT request. Service: id={}, operatorDTO={}", id, operatorDTO);
        return new ResponseEntity<>(techService.updateOperator(id, operatorDTO), HttpStatus.OK);
    }

    //редактирование брони пользователя (ДЗ №9)
    @Secured("ROLE_USER")
    @PutMapping(value = "/update-booking-of-user", produces = "application/json")
    ResponseEntity<Booking> updateBookingOfUser(@RequestParam int userId,
                                          @RequestParam int bookingId,
                                          @RequestBody RequestBookingDTO bookingDTO) {
        log.info("PUT request. Service: userId={}, bookingId={}, bookingDTO={}", userId, bookingId, bookingDTO);
        return new ResponseEntity<>(techService.updateBookingOfUser((long) userId, (long) bookingId, bookingDTO), HttpStatus.OK);
    }

    //назначение скидки на все брони (ДЗ №9)
    @Secured("ROLE_USER")
    @PutMapping(value = "/update-all-discounts", produces = "application/json")
    ResponseEntity<Long> updateDiscountsForAllBookings(@RequestParam String targetDiscount) {
        log.info("PUT request. Service: targerDiscount={}", targetDiscount);
        return new ResponseEntity<>(techService.updateAllBookingsDiscounts(targetDiscount), HttpStatus.OK);
    }

    //удаление скидки на все брони (ДЗ №9)
    @Secured("ROLE_USER")
    @DeleteMapping(value = "/delete-all-discounts", produces = "application/json")
    ResponseEntity<Long> deleteDiscountsForAllBookings() {
        log.info("DELETE request. Service: delete discounts for all bookings");
        return new ResponseEntity<>(techService.deleteAllBookingsDiscounts(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/give-discount-to-all-cancelled-users")
    ResponseEntity<?> giveDiscountToAllCancelledUsers(String message) {
        log.info("POST request. Service: {}", message);
        return new ResponseEntity<>(techService.giveDiscountToAllCancelledUsers(message), HttpStatus.OK);
    }
}
