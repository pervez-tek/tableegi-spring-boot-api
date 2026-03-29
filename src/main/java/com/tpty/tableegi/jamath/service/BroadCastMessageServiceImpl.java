package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.BroadCastMessageDto;
import com.tpty.tableegi.jamath.dto.NotificationDeliveryResult;
import com.tpty.tableegi.jamath.dto.NotificationEvent;
import com.tpty.tableegi.jamath.entity.BroadCastMessageEntity;
import com.tpty.tableegi.jamath.entity.JamathMasterSurveyEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.repo.AdminLoginRepo;
import com.tpty.tableegi.jamath.repo.BroadCastRepo;
import com.tpty.tableegi.jamath.repo.JamathMasterSurveyRepo;
import com.tpty.tableegi.jamath.utils.BroadCastMsgDtoConverterToEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class BroadCastMessageServiceImpl implements BroadCastMessageService {

    private final BroadCastRepo broadCastRepo;
    private final NotificationPublisher notificationPublisher;
    private final JamathMasterSurveyRepo jamathMasterSurveyRepo;

    public BroadCastMessageServiceImpl(BroadCastRepo broadCastRepo, NotificationPublisher notificationPublisher, JamathMasterSurveyRepo jamathMasterSurveyRepo) {
        this.broadCastRepo = broadCastRepo;
        this.notificationPublisher = notificationPublisher;
        this.jamathMasterSurveyRepo = jamathMasterSurveyRepo;
    }

    @Override
    public BroadCastMessageEntity publishMessage(BroadCastMessageDto messageDto) throws InvalidDataException {
        if (messageDto.getMessage() == null || messageDto.getSubject() == null || messageDto.getUsrAdminId() == null) {
            throw new InvalidDataException("Data is missing.");
        }
        if (messageDto.getMessage().trim().equalsIgnoreCase("") ||
                messageDto.getSubject().equalsIgnoreCase("") ||
                messageDto.getUsrAdminId().equalsIgnoreCase("")) {
            throw new InvalidDataException("Please provide valid Data.");
        }
        BroadCastMessageEntity entity = BroadCastMsgDtoConverterToEntity.toEntity(messageDto);

        if (Objects.nonNull(entity)) {
            List<JamathMasterSurveyEntity> jamthiMasterList = jamathMasterSurveyRepo.findAll();
            jamthiMasterList.forEach(data -> {
                        CompletableFuture<List<NotificationDeliveryResult>> futureResults = notificationPublisher.publishBroadCastAsync(
                                NotificationEvent.builder().
                                        name(data.getName()).
                                        email(data.getEmail()).
                                        phone(data.getPhone()).
                                        subject(messageDto.getSubject()).
                                        message(messageDto.getMessage()).
                                        build());

                        futureResults.thenAccept(System.out::println);
                        futureResults.whenComplete((results, ex) -> {
                            if (ex != null) {
                                System.out.printf("Async publish failed for {%s}: {%s} : {%s}", data.getEmail(), ex.getMessage(), ex);
                                // record failure result if needed
                            } else {
                                System.out.printf("Async publish results: {%s}", results);
                            }
                        });
                    }
            );
        }

        return broadCastRepo.save(entity);
    }
}
