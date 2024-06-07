package com.itmo.simaland.delegate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetUserIdDelegate implements JavaDelegate {

    private final IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Started execution in GetUserIdDelegate");

        String userId = identityService.getCurrentAuthentication().getUserId();
        if (userId == null) {
            log.error("No authenticated user found");
            throw new IllegalStateException("No authenticated user");
        }

        log.info("Authenticated user ID: {}", userId);

        execution.setVariable("userId", userId);
        log.info("Set process variable 'userId' to {}", userId);
    }
}
