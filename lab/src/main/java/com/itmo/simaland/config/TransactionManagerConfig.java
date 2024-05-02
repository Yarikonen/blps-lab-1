package com.itmo.simaland.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
public class TransactionManagerConfig {

    @Primary
    @Bean
    public JtaTransactionManager transactionManager() {
        JtaTransactionManager transactionManager = new JtaTransactionManager();
        transactionManager.setAllowCustomIsolationLevels(true);
        return transactionManager;
    }
}

