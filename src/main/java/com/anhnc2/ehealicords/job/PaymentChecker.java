package com.anhnc2.ehealicords.job;

//import com.anhnc2.ehealicords.constant.PaymentStatus;
//import com.anhnc2.ehealicords.repository.ZalopayLogRepository;
//import com.anhnc2.ehealicords.service.lowlevel.AppExecutor;
//import com.anhnc2.ehealicords.service.payment.ZaloPayService;
//import lombok.AllArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//
//@Component
//@AllArgsConstructor
//public class PaymentChecker {
//    private final ZalopayLogRepository zalopayLogRepository;
//    private final AppExecutor appExecutor;
//    private final ZaloPayService zaloPayService;
//
//    @Scheduled(cron = "0 0 3,15 * * *", zone = "GMT+7:00")
//    public void checkPaymentStatus() {
//        var ts = System.currentTimeMillis();
//        var twoDate = Duration.ofDays(2);
//        var millis = twoDate.toMillis();
//        var logs =
//                zalopayLogRepository.findAllByStatusAndTimestamp(PaymentStatus.WAITING, ts - millis, ts);
//        for (var log : logs) {
//            appExecutor.submitIO(
//                    () -> zaloPayService.validateTopUpTransId(log.getUserId(), log.getTransId()));
//        }
//    }
//}