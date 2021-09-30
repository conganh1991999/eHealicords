package com.anhnc2.ehealicords.job;

//import com.anhnc2.ehealicords.constant.AppointmentStatus;
//import com.anhnc2.ehealicords.repository.AppointmentRepository;
//import com.anhnc2.ehealicords.repository.OnlineAppointmentRepository;
//import com.anhnc2.ehealicords.service.lowlevel.AppExecutor;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@AllArgsConstructor
//@Slf4j
//public class AppointmentStatusUpdater {
//    private final AppointmentRepository appointmentRepository;
//    private final OnlineAppointmentRepository onlineAppointmentRepository;
//    private final AppExecutor appExecutor;
//
//    @Scheduled(cron = "0 0 3,8,16 * * *", zone = "GMT+7:00")
//    public void updateAppointmentStatus() {
//        var offlineAppointments =
//                appointmentRepository.findAllMissingByActualTimestampAndStatus(
//                        AppointmentStatus.WAITING, System.currentTimeMillis());
//        log.info("missing offline appointment size {}", offlineAppointments.size());
//        for (var appointment : offlineAppointments) {
//            appExecutor.submitIO(
//                    () -> {
//                        log.info("Update appointment status to missing, appointmentId={}", appointment.getId());
//                        appointment.setStatus(AppointmentStatus.MISSING);
//                        appointmentRepository.saveAndFlush(appointment);
//                    });
//        }
//
//        var onlineAppointments =
//                onlineAppointmentRepository.findAllMissingByTimestampAndStatus(
//                        System.currentTimeMillis(), AppointmentStatus.WAITING);
//        for (var appointment : onlineAppointments) {
//            appExecutor.submitIO(
//                    () -> {
//                        log.info(
//                                "Update online appointment status to missing, appointmentId={}",
//                                appointment.getId());
//                        appointment.setStatus(AppointmentStatus.MISSING);
//                        onlineAppointmentRepository.saveAndFlush(appointment);
//                    });
//        }
//    }
//}
