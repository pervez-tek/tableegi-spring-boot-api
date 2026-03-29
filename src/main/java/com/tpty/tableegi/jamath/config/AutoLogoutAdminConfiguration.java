package com.tpty.tableegi.jamath.config;

import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.entity.AdminUsersLoginTrackingEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.AdminLoginService;
import com.tpty.tableegi.jamath.service.AdminLoginTrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
public class AutoLogoutAdminConfiguration implements SchedulingConfigurer {

    @Autowired
    private AdminLoginTrackingService adminLoginTrackingService;

    @Autowired
    private AdminLoginService adminLoginService;

    @Value("${spring.auto.expire.session.id.token.valid.mins}")
    private String SPRING_AUTO_EXPIRE_JOB_SESSION_TOKEN_VALID_MINS;

    @Value("${spring.auto.expire.session.id.cron.expr}")
    private String SPRING_AUTO_EXPIRE_SESSION_ID_CRON_EXPR;

    //@Scheduled(fixedRate = 5 * 60 * 1000) //every 5 minutes
    @Scheduled(fixedRateString = "#{${spring.auto.expire.session.id.cron.expr} * 60000}")
    public void autoLogoutAdminUsers() throws InvalidDataException {
        LocalDateTime expiry = LocalDateTime.now().minusMinutes(Integer.parseInt(SPRING_AUTO_EXPIRE_JOB_SESSION_TOKEN_VALID_MINS));
        List<AdminUsersLoginTrackingEntity> expiredSessions = adminLoginTrackingService.findExpiredSessions(expiry);


        expiredSessions.forEach(track -> {
            log.debug("Scheduler for validating token session expired");
            AdminLoginRequestDto adminDto = AdminLoginRequestDto.builder()
                    .usrAdminId(track.getUsrAdminId().toString())
                    .id(track.getId().toString())
                    .username(adminLoginService.findById(track.getUsrAdminId()).getUsername())
                    .build();
            try {
                adminLoginTrackingService.signOut(adminDto);
            } catch (InvalidDataException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        registrar.setScheduler(Executors.newScheduledThreadPool(5));
    }
}
