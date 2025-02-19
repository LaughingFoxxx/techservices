package com.me.techservices.constant;

import lombok.Getter;

@Getter
public enum ServiceServices {
    REPAIR("Ремонт"),
    EXCHANGE("Обмен"),
    DEVICE("Устройство");

    private final String service;

    ServiceServices(String service) {
        this.service = service;
    }
}
