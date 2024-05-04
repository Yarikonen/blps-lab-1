package com.itmo.simaland.repository;

import com.itmo.simaland.model.entity.DailySalesReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailySalesReportRepository extends JpaRepository<DailySalesReport, Long> {
}
