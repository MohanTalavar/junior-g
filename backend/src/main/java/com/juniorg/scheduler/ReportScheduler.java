package com.juniorg.scheduler;

import com.juniorg.service.AttendanceReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final AttendanceReportService attendanceReportService;

    // Every Sunday at 6 PM
    @Scheduled(cron = "0 0 18 ? * SUN")
    public void sendWeeklyReports() {
        log.info("Triggered weekly attendance report scheduler");
        attendanceReportService.sendWeeklyReports();
    }

    // Last day of each month at 6 PM
    @Scheduled(cron = "0 0 18 L * ?")
    public void sendMonthlyReports() {
        log.info("Triggered monthly attendance report scheduler");
        attendanceReportService.sendMonthlyReports();
    }
}