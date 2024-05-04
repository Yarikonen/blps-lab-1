package com.itmo.simaland.scheduler;

import com.itmo.simaland.model.entity.DailySalesReport;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.model.entity.OrderItem;
import com.itmo.simaland.repository.DailySalesReportRepository;
import com.itmo.simaland.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class PeriodicTask {
    private static final Logger logger = LoggerFactory.getLogger(PeriodicTask.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DailySalesReportRepository dailySalesReportRepository;

    @Transactional
    @Scheduled(cron = "0 0 8 * * ?") // Run every day at 8:00 AM
//    @Scheduled(cron = "0 0/1 * * * ?") // Run every minute for debug reasons
    public void everyFiveMinutes() {
        logger.info("Executing PeriodicTask...");

        LocalDate currentDate = LocalDate.now();
        List<Order> orders = orderRepository.findByOrderDateWithItems(currentDate);

        int totalItemsSold = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                totalItemsSold += orderItem.getQuantity();
                totalAmount = totalAmount.add(orderItem.getTotalPrice());
            }
        }

        DailySalesReport dailySalesReport = new DailySalesReport();
        dailySalesReport.setReportDate(currentDate);
        dailySalesReport.setTotalItemsSold(totalItemsSold);
        dailySalesReport.setTotalAmount(totalAmount);

        dailySalesReportRepository.save(dailySalesReport);

        logger.info("PeriodicTask executed successfully.");
    }
}
