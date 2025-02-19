package com.me.techservices.controller;

import com.me.techservices.dto.ServiceBookingDTO;
import com.me.techservices.service.TechService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/techservice") //контроллер по ДЗ №3
public class TechServicesController {

    //условный слой сервиса
    private final TechService techService;

    @Autowired
    public TechServicesController(TechService techService) {
        this.techService = techService;
    }

    //Создание брони на услугу сервиса (ДЗ №3)
    @PostMapping("/bookservice")
    ResponseEntity<?> bookService(@RequestBody ServiceBookingDTO serviceBookingDTO) {
        log.info("HA3-controller. POST request. Service: {}", serviceBookingDTO);
        return new ResponseEntity<>(techService.bookService(), HttpStatus.OK);
    }

    //создание услуги (ДЗ №3)
    @PostMapping("/newservice")
    ResponseEntity<?> createService(@RequestParam String serviceName) {
        log.info("HA3-controller. POST request. Service: {}", serviceName);
        return new ResponseEntity<>(techService.createService(serviceName), HttpStatus.OK);
    }

    //редактирование услуги (ДЗ №3)
    @PutMapping("/update")
    ResponseEntity<?> updateService(@RequestParam int id) {
        log.info("HA3-controller. PUT request. Service: {}", id);
        return new ResponseEntity<>(techService.updateService(id), HttpStatus.OK);
    }

    //получение списка услуг (ДЗ №3)
    @GetMapping(value = "/getlist", produces = "application/json")
    ResponseEntity<?> getServiceList() {
        log.info("HA3-controller. GET request.");
        return new ResponseEntity<>(techService.getServiceList(), HttpStatus.OK);
    }

    //получение услуги по идентификатору (ДЗ №3)
    @GetMapping("/getservice")
    ResponseEntity<?> getServiceById(@RequestParam int id) {
        log.info("HA3-controller. GET request. Service: {}", id);
        return new ResponseEntity<>(techService.getServiceById(id), HttpStatus.OK);
    }

    //отмена брони услуги (ДЗ №4)
    @DeleteMapping
    ResponseEntity<?> cancelServiceBooking(@RequestParam int id) {
        log.info("HA3-controller. DELETE request. Service: {}", id);
        return new ResponseEntity<>(techService.cancelServiceBooking(id), HttpStatus.OK);
    }
}
