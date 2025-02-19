package com.me.techservices.service;

public interface TechService {

    String createService(String name);

    String updateService(int id);

    String getServiceList();

    String getServiceById(int id);

    String bookService();

    String cancelServiceBooking(int id);
}
