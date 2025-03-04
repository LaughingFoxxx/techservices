package com.me.techservices.dto.response;

import java.util.List;

public record ResponseStatisticsDTO(
        List<Long> processedIds
) {
}
