package com.me.techservices.controller;

import com.me.techservices.service.TechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/techservice")
public class TechServicesController {

    //условный слой сервиса
    final TechService techService;

    @Autowired
    public TechServicesController(TechService techService) {
        this.techService = techService;
    }

    //создание услуги
    //должен вернуть -> Услуга создана
    @PostMapping("/newservice")
    String createService(@RequestParam String serviceName) {
        return techService.createService(serviceName);
    }

    //редактирование услуги
    //должен вернуть -> Услуга отредактирована
    @PutMapping("/update/{id}")
    String updateService(@PathVariable int id) {
        return techService.updateService(id);
    }

    //получение списка услуг
    //должен вернуть -> Получен список услуг
    @GetMapping("/getlist")
    String getServiceList() {
        return techService.getServiceList();
    }

    //получение услуги по идентификатору
    //должен вернуть -> Получена услуга по идентификатору
    @GetMapping("/getservice/{id}")
    String getServiceById(@PathVariable int id) {
        return techService.getServiceById(id);
    }
}
