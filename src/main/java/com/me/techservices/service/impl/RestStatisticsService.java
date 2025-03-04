package com.me.techservices.service.impl;

import com.me.techservices.dto.request.RequestStatisticsDTO;
import com.me.techservices.dto.response.ResponseStatisticsDTO;
import com.me.techservices.service.StatisticsCollector;
import com.me.techservices.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;

@Service
@Profile("rest")
@RequiredArgsConstructor
public class RestStatisticsService implements StatisticsService {

    private final RestClient restClient;
    private final StatisticsCollector statisticsCollector;
    @Value("${dwh.service.url}")
    private String dwhUrl;

    @Override
    public List<Long> sendStatistics() {
        List<RequestStatisticsDTO> statistics = statisticsCollector.collectStatistics();
        ResponseStatisticsDTO response = restClient.post()
                .uri(dwhUrl + "/api/statistics")
                .body(statistics)
                .retrieve()
                .body(ResponseStatisticsDTO.class);
        return response.processedIds();
    }
}
