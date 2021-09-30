package com.anhnc2.ehealicords.job;

//import com.anhnc2.ehealicords.config.ZoneConfig;
//import com.anhnc2.ehealicords.constant.NotificationType;
//import com.anhnc2.ehealicords.constant.ScheduleType;
//import com.anhnc2.ehealicords.data.common.Notification;
//import com.anhnc2.ehealicords.repository.AppointmentRepository;
//import com.anhnc2.ehealicords.repository.OnlineAppointmentRepository;
//import com.anhnc2.ehealicords.repository.PrescriptionRepository;
//import com.anhnc2.ehealicords.service.lowlevel.AppExecutor;
//import com.anhnc2.ehealicords.service.notification.NotificationService;
//import lombok.AllArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.ZonedDateTime;
//
//@AllArgsConstructor
//@Component
//public class MedicineRemindJob {
//    private final PrescriptionRepository prescriptionRepository;
//    private final AppointmentRepository appointmentRepository;
//    private final OnlineAppointmentRepository onlineAppointmentRepository;
//    private final ZoneConfig zoneConfig;
//    private final NotificationService notificationService;
//    private final AppExecutor appExecutor;
//
//    @Scheduled(cron = "0 0 7,11,17 * * *", zone = "GMT+7:00")
//    public void notifyPatients() {
//        var today = ZonedDateTime.now(zoneConfig.getZoneId()).toLocalDate();
//        var prescriptions = prescriptionRepository.findAllByDate(today);
//        for (var prescription : prescriptions) {
//            appExecutor.submitIO(
//                    () -> {
//                        long userId;
//
//                        if (prescription.getAppointmentType() == ScheduleType.OFFLINE) {
//                            var appointment =
//                                    appointmentRepository.findById(prescription.getAppointmentId()).orElseThrow();
//                            userId = appointment.getUserId();
//                        } else {
//                            var appointment =
//                                    onlineAppointmentRepository
//                                            .findById(prescription.getAppointmentId())
//                                            .orElseThrow();
//                            userId = appointment.getUserId();
//                        }
//
//                        notificationService.createNotification(
//                                Notification.builder()
//                                        .userId(userId)
//                                        .title("Bạn có lời nhắc uống thuốc vào hôm nay")
//                                        .referenceId(prescription.getId())
//                                        .content("Nhấn để xem chi tiết lời nhắc uống thuốc của bạn")
//                                        .type(NotificationType.MEDICINE_REMIND)
//                                        .timestamp(System.currentTimeMillis())
//                                        .build());
//                    });
//        }
//    }
//}
