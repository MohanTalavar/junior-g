package com.juniorg.service;

import com.juniorg.scheduler.ReportScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReportSchedulerIntegrationTest {

    @Autowired
    private ReportScheduler reportScheduler;

    @Autowired
    private AttendanceReportService attendanceReportService;

    @Test
    void contextLoads() {
        // sanity check — beans should be initialized by Spring
        assertThat(reportScheduler).isNotNull();
        assertThat(attendanceReportService).isNotNull();
    }

    @Test
    void shouldTriggerWeeklyReport() {
        // manually invoke scheduled method
        reportScheduler.sendWeeklyReports();
    }

    @Test
    void shouldTriggerMonthlyReport() {
        reportScheduler.sendMonthlyReports();
    }
}
