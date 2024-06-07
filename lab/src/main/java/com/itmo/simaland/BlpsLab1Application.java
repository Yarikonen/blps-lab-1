package com.itmo.simaland;

import com.itmo.simaland.config.QuartzConfig;
import com.itmo.simaland.delegate.ItemCreationDelegate;
import com.itmo.simaland.model.entity.User;
import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.model.enums.RoleEnum;
import com.itmo.simaland.model.enums.Status;
import com.itmo.simaland.service.UserService;
import com.itmo.simaland.service.WarehouseService;
import jakarta.persistence.EntityNotFoundException;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.camunda.bpm.engine.ProcessEngine;
//import org.camunda.bpm.engine.ProcessEngineConfiguration;
//import com.itmo.simaland.delegate.ItemCreationDelegate;

@SpringBootApplication
@EnableScheduling
@EnableProcessApplication
@Import(QuartzConfig.class)
public class BlpsLab1Application {
    @Autowired
    private Scheduler scheduler;


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BlpsLab1Application.class, args);
        BlpsLab1Application application = context.getBean(BlpsLab1Application.class);
        application.runScheduler();
    }

    public void runScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Bean
    CommandLineRunner init(UserService userService, WarehouseService warehouseService) {
        return args -> {
            try {
                userService.loadUserByUsername("admin");
                warehouseService.getById(1L);
            } catch (UsernameNotFoundException e) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword("admin");
                adminUser.setRoleEnum(RoleEnum.ADMIN);
                adminUser.setStatus(Status.ACTIVE);
                userService.createUser(adminUser);
            } catch (EntityNotFoundException e) {
                Warehouse warehouse = new Warehouse();
                warehouse.setAddress("street");
                warehouse.setId(1L);
                warehouseService.save(warehouse);
            }
        };
    }
}
