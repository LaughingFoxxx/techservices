package com.me.techservices.client;

import com.me.techservices.dto.request.RequestStatisticsDTO;
import com.me.techservices.dto.response.ResponseStatisticsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(name = "dwh-service", url = "${dwh.service.url}", configuration = FeignConfig.class)
public interface DwhFeignClient {
    @PostMapping("/api/statistics")
    ResponseStatisticsDTO sendStatistics(@RequestBody List<RequestStatisticsDTO> statistics);
}
