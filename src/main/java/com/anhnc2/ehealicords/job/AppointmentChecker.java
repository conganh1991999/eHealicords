package com.anhnc2.ehealicords.job;

//import com.anhnc2.ehealicords.constant.AppointmentStatus;
//import com.anhnc2.ehealicords.constant.NotificationType;
//import com.anhnc2.ehealicords.data.common.Notification;
//import com.anhnc2.ehealicords.data.entity.AppointmentEntity;
//import com.anhnc2.ehealicords.data.entity.OnlineAppointmentEntity;
//import com.anhnc2.ehealicords.repository.AppointmentRepository;
//import com.anhnc2.ehealicords.repository.OnlineAppointmentRepository;
//import com.anhnc2.ehealicords.repository.SpecialistSchedulesRepository;
//import com.anhnc2.ehealicords.service.lowlevel.AppExecutor;
//import com.anhnc2.ehealicords.service.notification.NotificationService;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//@Component
//@AllArgsConstructor
//public class AppointmentChecker {
//    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentChecker.class);
//
//    private final AppointmentRepository appointmentRepository;
//    private final OnlineAppointmentRepository onlineAppointmentRepository;
//    private final SpecialistSchedulesRepository specialistSchedulesRepository;
//    private final NotificationService notificationService;
//    private final AppExecutor appExecutor;
//
//    @Scheduled(cron = "0 0 10,17 * * *", zone = "GMT+7:00")
//    public void notifyPatients() {
//        var tommorow = LocalDate.now().plusDays(1);
//
//        // Offline appointments
//        var schedules = specialistSchedulesRepository.findAllByDate(tommorow);
//        for (var schedule : schedules) {
//            var appointments =
//                    appointmentRepository.findAllByScheduleIdAndStatus(
//                            schedule.getId(), AppointmentStatus.WAITING);
//            for (var appointment : appointments) {
//                appExecutor.submitIO(() -> notifyPatient(appointment));
//            }
//        }
//
//        // Online appointments
//        for (var schedule : schedules) {
//            var appointments =
//                    onlineAppointmentRepository.findAllByScheduleIdAndStatus(
//                            schedule.getId(), AppointmentStatus.WAITING);
//            for (var appointment : appointments) {
//                appExecutor.submitIO(() -> notifyPatient(appointment));
//            }
//        }
//
//        LOGGER.info("Run crob job to notify user about appointment for date {}", tommorow);
//    }
//
//    private void notifyPatient(AppointmentEntity appointmentEntity) {
//        LOGGER.info("Send notify to user {}", appointmentEntity.getUserId());
//
//        notificationService.createNotification(
//                Notification.builder()
//                        .type(NotificationType.TOMORROW_APPOINTMENT)
//                        .title("Bạn có lịch khám vào ngày mai")
//                        .content("Bạn có lịch hẹn khám bệnh với chúng tôi vào ngày mai, nhấn để xem chi tiết")
//                        .referenceId(appointmentEntity.getId())
//                        .userId(appointmentEntity.getUserId())
//                        .build());
//    }
//
//    private void notifyPatient(OnlineAppointmentEntity appointmentDAO) {
//        LOGGER.info("Send notify to user {}", appointmentDAO.getUserId());
//
//        notificationService.createNotification(
//                Notification.builder()
//                        .type(NotificationType.TOMORROW_ONLINE_APPOINTMENT)
//                        .title("Bạn có lịch tư vấn vào ngày mai")
//                        .content("Bạn có lịch hẹn tư vấn với chúng tôi vào ngày mai, nhấn để xem chi tiết")
//                        .referenceId(appointmentDAO.getId())
//                        .userId(appointmentDAO.getUserId())
//                        .build());
//    }
//}
