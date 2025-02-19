package com.me.techservices.service;

import org.springframework.stereotype.Service;

@Service
public class TechServiceImpl implements TechService {
    @Override
    public String bookService() {
        return "Услуга забронирована";
    }

    @Override
    public String createService(String name) {
        return "Услуга создана";
    }

    @Override
    public String updateService(int id) {
        return "Услуга отредактирована";
    }

    @Override
    public String getServiceList() {
        return "Получен список услуг";
    }

    @Override
    public String getServiceById(int id) {
        return "Получена услуга по идентификатору";
    }

    @Override
    public String cancelServiceBooking(int id) {
        return "Отмена созданной брони по идентификатору: " + id;
    }
}
