package com.me.techservices.controller.dwh;

import com.me.techservices.dto.request.RequestStatisticsDTO;
import com.me.techservices.dto.response.ResponseStatisticsDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class DwhController {

    @PostMapping("/api/statistics")
    public ResponseStatisticsDTO receiveStatistics(@RequestBody List<RequestStatisticsDTO> statistics) {
        Random random = new Random();
        List<Long> processedIds = statistics.stream()
                .filter(dto -> random.nextBoolean())
                .map(RequestStatisticsDTO::bookingId)
                .collect(Collectors.toList());

        return new ResponseStatisticsDTO(processedIds);
    }
}