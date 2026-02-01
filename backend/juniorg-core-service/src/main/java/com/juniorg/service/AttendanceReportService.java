package com.juniorg.service;

import com.juniorg.client.NotificationClient;
import com.juniorg.enums.AttendanceStatus;
import com.juniorg.pojos.Attendance;
import com.juniorg.pojos.Student;
import com.juniorg.pojos.User;
import com.juniorg.repo.AttendanceRepository;
import com.juniorg.repo.StudentRepo;
import com.juniorg.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceReportService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final StudentRepo studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserRepo userRepo;
    private final EmailService emailService; // fall back
    private final NotificationClient notificationClient;

    /* -------------------------------------------------------------
       ENTRY POINTS
       ------------------------------------------------------------- */

    public void sendWeeklyReports() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        log.info("Triggering Weekly Report for duration: {} to {}", startOfWeek, endOfWeek);
        generateAndSendReports(startOfWeek, endOfWeek, "Weekly Attendance Report");
    }

    public void sendMonthlyReports() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        log.info("Triggering Monthly Report for duration: {} to {}", startOfMonth, endOfMonth);
        generateAndSendReports(startOfMonth, endOfMonth, "Monthly Attendance Report");
    }

    /* -------------------------------------------------------------
       CORE REPORT LOGIC
       ------------------------------------------------------------- */

    private void generateAndSendReports(LocalDate start, LocalDate end, String subjectPrefix) {
        List<Student> students = studentRepository.findAll();
        StringJoiner adminSummary = new StringJoiner("\n------------------------------------\n");

        // compute Mon–Fri working days within this period
        List<LocalDate> workingDays = workingDaysBetween(start, end);

        for (Student student : students) {
            List<Attendance> records =
                    attendanceRepository.findByStudentIdAndAttendanceDateBetween(student.getId(), start, end);

            AttendanceSummary summary = computeSummary(records, workingDays);
            adminSummary.add(formatAdminLine(student, summary));
            sendParentReport(student, summary, subjectPrefix, start, end);
        }

        sendAdminSummary(subjectPrefix, start, end, adminSummary.toString());
    }

    /* -------------------------------------------------------------
       COMPUTATION HELPERS
       ------------------------------------------------------------- */

    private List<LocalDate> workingDaysBetween(LocalDate startInclusive, LocalDate endInclusive) {
        return startInclusive.datesUntil(endInclusive.plusDays(1))
                .filter(d -> {
                    DayOfWeek dow = d.getDayOfWeek();
                    return !(dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY);
                })
                .collect(Collectors.toList());
    }

    private AttendanceSummary computeSummary(List<Attendance> records, List<LocalDate> workingDays) {
        // Collect all attendance dates where student was present
        Set<LocalDate> presentDates = records.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .map(Attendance::getAttendanceDate) // update field name if different
                .collect(Collectors.toSet());

        long totalWorkingDays = workingDays.size();
        long presentCount = workingDays.stream().filter(presentDates::contains).count();
        long absentCount = Math.max(0, totalWorkingDays - presentCount);
        double percent = totalWorkingDays > 0 ? (presentCount * 100.0 / totalWorkingDays) : 0.0;

        return new AttendanceSummary(totalWorkingDays, presentCount, absentCount, percent);
    }

    /* -------------------------------------------------------------
       EMAIL HANDLERS
       ------------------------------------------------------------- */

    private void sendParentReport(Student student, AttendanceSummary summary,
                                  String subjectPrefix, LocalDate start, LocalDate end) {
        if (student.getEmail() == null || student.getEmail().isBlank()) {
            log.warn("Skipping parent email for {} — no parent email found", student.getFirstName());
            return;
        }

        String body = prepareParentBody(student, summary, subjectPrefix, start, end);
        // Commenting this as we have introduced new Notification micro service
        // emailService.sendEmail(student.getEmail(), subjectPrefix, body);

        try {
            notificationClient.sendEmail(
                    student.getEmail(),
                    subjectPrefix,
                    body
            );
        } catch (Exception ex) {
            log.warn("Falling back to local EmailService for parent {}", student.getEmail());
            emailService.sendEmail(student.getEmail(), subjectPrefix, body);
        }
        log.info("Sent {} to parent of {}", subjectPrefix, student.getFirstName() + " " + student.getSurname());
    }

    private String prepareParentBody(Student s, AttendanceSummary summary,
                                     String subjectPrefix, LocalDate start, LocalDate end) {
        return String.format("""
                Dear Parent,

                Here is the %s for your child %s:
                Duration: %s to %s
                Total Working Days: %d
                Present: %d
                Absent: %d
                Attendance: %.2f%%

                Regards,
                Junior G International Preschool
                """, subjectPrefix, s.getFirstName() + " " + s.getSurname(),
                start, end,
                summary.totalDays, summary.presentDays,
                summary.absentDays, summary.percentage);
    }

    private String formatAdminLine(Student s, AttendanceSummary summary) {
        return String.format("Student: %s %s\nAttendance: %.2f%% (%d/%d)\nParent: %s",
                s.getFirstName(), s.getSurname(),
                summary.percentage, summary.presentDays,
                summary.totalDays, s.getEmail());
    }

    private void sendAdminSummary(String subjectPrefix, LocalDate start,
                                  LocalDate end, String summaryText) {
        List<User> admins = userRepo.findByRole(ROLE_ADMIN);

        if (admins.isEmpty()) {
            log.warn("No admin users found to send summary email.");
            return;
        }

        String adminSubject = subjectPrefix + " - Summary for All Students";
        String adminBody = String.format("""
                Dear Admin,

                Below is the %s for all students (%s to %s):

                %s

                Regards,
                Junior G International Preschool
                """, subjectPrefix, start, end, summaryText);

        admins.forEach(admin -> {

            // emailService.sendEmail(admin.getEmail(), adminSubject, adminBody);

            try {
                notificationClient.sendEmail(
                        admin.getEmail(),
                        adminSubject,
                        adminBody
                );
            } catch (Exception ex) {
                log.warn("Falling back to local EmailService for admin {}", admin.getEmail());
                emailService.sendEmail(admin.getEmail(), subjectPrefix, adminBody);
            }
            log.info("Sent {} summary to admin {}", subjectPrefix, admin.getEmail());
        });
    }

    /* -------------------------------------------------------------
       INTERNAL DTO
       ------------------------------------------------------------- */
    private record AttendanceSummary(long totalDays, long presentDays, long absentDays, double percentage) {}
}
