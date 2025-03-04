package com.me.techservices.scheduler;

import com.me.techservices.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatisticsScheduler {

    private final StatisticsService statisticsService;

    @Scheduled(cron = "0 0 0 * * *") // Runs daily at midnight
    public void sendDailyStatistics() {
        try {
            List<Long> processedIds = statisticsService.sendStatistics();
            if (processedIds == null || processedIds.isEmpty()) {
                log.warn("Statistics sending failed, retrying...");
                processedIds = statisticsService.sendStatistics();
            }
            log.info("Successfully sent statistics for {} records", processedIds.size());
        } catch (Exception e) {
            log.error("Error sending statistics", e);
        }
    }
}
