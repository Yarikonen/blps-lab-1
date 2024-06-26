package com.itmo.simaland;

import com.itmo.simaland.config.QuartzConfig;
import com.itmo.simaland.model.entity.User;
import com.itmo.simaland.model.enums.RoleEnum;
import com.itmo.simaland.model.enums.Status;
import com.itmo.simaland.service.UserService;
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

@SpringBootApplication
@EnableScheduling
@Import(QuartzConfig.class)
public class BlpsLab1Application extends SpringBootServletInitializer {
    @Autowired
    private Scheduler scheduler;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlpsLab1Application.class);
    }

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
    CommandLineRunner init(UserService userService) {
        return args -> {
            try {
                userService.loadUserByUsername("admin");
            } catch (UsernameNotFoundException e) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword("admin");
                adminUser.setRoleEnum(RoleEnum.ADMIN);
                adminUser.setStatus(Status.ACTIVE);
                userService.createUser(adminUser);
            }
        };
    }
}
