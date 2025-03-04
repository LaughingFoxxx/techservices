package com.me.techservices.service.impl;

import com.me.techservices.client.DwhFeignClient;
import com.me.techservices.service.StatisticsService;
import com.me.techservices.dto.request.RequestStatisticsDTO;
import com.me.techservices.service.StatisticsCollector;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("feign")
@RequiredArgsConstructor
public class FeignStatisticsService implements StatisticsService {

    private final DwhFeignClient dwhFeignClient;
    private final StatisticsCollector statisticsCollector;

    @Override
    public List<Long> sendStatistics() {
        List<RequestStatisticsDTO> statistics = statisticsCollector.collectStatistics();
        return dwhFeignClient.sendStatistics(statistics).processedIds();
    }
}
