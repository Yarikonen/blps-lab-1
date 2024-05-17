package com.itmo.simaland.scheduler;

import com.itmo.simaland.model.entity.DailySalesReport;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.model.entity.OrderItem;
import com.itmo.simaland.repository.DailySalesReportRepository;
import com.itmo.simaland.repository.OrderRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class ReportJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(ReportJob.class);

    private static final int MAX_RETRIES = 3;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DailySalesReportRepository dailySalesReportRepository;

    @Override
    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Executing ReportJob...");

        JobKey jobKey = context.getJobDetail().getKey();
        int retryCount = context.getJobDetail().getJobDataMap().getIntValue("retryCount");

        try {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            List<Order> orders = orderRepository.findByOrderDateWithItems(yesterday);

            int totalItemsSold = 0;
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (Order order : orders) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    totalItemsSold += orderItem.getQuantity();
                    totalAmount = totalAmount.add(orderItem.getTotalPrice());
                }
            }

            DailySalesReport dailySalesReport = new DailySalesReport();
            dailySalesReport.setReportDate(yesterday);
            dailySalesReport.setTotalItemsSold(totalItemsSold);
            dailySalesReport.setTotalAmount(totalAmount);

            dailySalesReportRepository.save(dailySalesReport);

            logger.info("ReportJob executed successfully.");
        } catch (Exception e) {
            logger.error("Error executing ReportJob", e);
            retryCount++;
            if (retryCount <= MAX_RETRIES) {
                context.getJobDetail().getJobDataMap().put("retryCount", retryCount);
                throw new JobExecutionException(e, true);
            } else {
                throw new JobExecutionException(e, false);
            }
        }
    }
}
