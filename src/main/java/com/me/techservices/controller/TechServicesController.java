package com.me.techservices.controller;

import com.me.techservices.dto.request.RequestBookingDTO;
import com.me.techservices.dto.request.RequestServiceDTO;
import com.me.techservices.dto.response.ResponseRevenueByDateDTO;
import com.me.techservices.entity.Booking;
import com.me.techservices.entity.Service;
import com.me.techservices.service.TechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping(value = "/create-booking", produces = "application/json")
    ResponseEntity<Booking> bookService(@RequestBody RequestBookingDTO createBookingDTO) {
        log.info("POST request. Service: {}", createBookingDTO);
        return new ResponseEntity<>(techService.createBooking(createBookingDTO), HttpStatus.OK);
    }

    //создание услуги (ДЗ №3)
    @PostMapping(value = "/create-service", produces = "application/json")
    ResponseEntity<Service> createService(@RequestBody RequestServiceDTO serviceDTO) {
        log.info("POST request. Service: {}", serviceDTO);
        return new ResponseEntity<>(techService.createService(serviceDTO), HttpStatus.OK);
    }

    //редактирование услуги (ДЗ №3)
    @PutMapping(value = "/update", produces = "application/json")
    ResponseEntity<Service> updateService(@RequestParam int id, @RequestBody RequestServiceDTO serviceDTO) {
        log.info("PUT request. Service: {}", id);
        return new ResponseEntity<>(techService.updateService(id, serviceDTO), HttpStatus.OK);
    }

    //получение списка услуг (ДЗ №3)
    @GetMapping(value = "/get-services-list", produces = "application/json")
    ResponseEntity<List<Service>> getServiceList() {
        List<Service> result = techService.getServiceList();
        log.info("GET request. Service: {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //получение услуги по идентификатору (ДЗ №3)
    @GetMapping("/get-service")
    ResponseEntity<Service> getServiceById(@RequestParam int id) {
        log.info("GET request. Service: {}", id);
        return new ResponseEntity<>(techService.getServiceById(id), HttpStatus.OK);
    }

    //отмена брони услуги (ДЗ №4)
    @DeleteMapping("/delete-service")
    ResponseEntity<Service> cancelServiceBooking(@RequestParam int id) {
        log.info("DELETE request. Service: {}", id);
        return new ResponseEntity<>(techService.cancelServiceBooking(id), HttpStatus.OK);
    }

    //получение брони по дате и времени записи (ДЗ №8)
    @GetMapping("/get-booking-by-datetime")
    ResponseEntity<Booking> getBookingByDateTime(@RequestParam LocalDateTime dateTime) {
        log.info("GET request. Service: {}", dateTime);
        return  new ResponseEntity<>(techService.getBookingByDateTime(dateTime), HttpStatus.OK);
    }

    //получение выручки за определенный период времени (ДЗ №8)
    @GetMapping("/get-revenue-by-datetime")
    ResponseEntity<List<ResponseRevenueByDateDTO>> getRevenueByDateTime(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        log.info("GET request. Service: {}, {}", startDate, endDate);
        return new ResponseEntity<>(techService.getRevenueByDateTime(startDate, endDate), HttpStatus.OK);
    }
}
