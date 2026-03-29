package com.tpty.tableegi.jamath.controller;

import com.tpty.tableegi.jamath.dto.BroadCastMessageDto;
import com.tpty.tableegi.jamath.dto.JamathFeedBackDto;
import com.tpty.tableegi.jamath.entity.JamathiFeedBackEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.AdminLoginService;
import com.tpty.tableegi.jamath.service.FeedBackService;
import com.tpty.tableegi.jamath.service.ProfileImageService;
import com.tpty.tableegi.jamath.utils.JamathiFeedBackDtoConverterToEntity;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tableegi")
@CrossOrigin(origins = "${front.end.react.api.url}") // allow React dev server
@Slf4j
public class JamathiFeedBackController {

    private final FeedBackService feedBackService;
    private final ProfileImageService profileImageService;

    private JamathiFeedBackController(FeedBackService feedBackService, ProfileImageService profileImageService) {
        this.feedBackService = feedBackService;
        this.profileImageService = profileImageService;
    }

    @PostMapping("/sendFeedBack")
    public ResponseEntity<Object> sendFeedBack(@Valid @RequestBody JamathFeedBackDto feedBackDto) throws InvalidDataException {

        JamathiFeedBackEntity jamathiFeedBackEntity = feedBackService.sendFeedBack(feedBackDto);
        if (jamathiFeedBackEntity != null) {
            log.info("SendFeedback message Completed");
        }
        return ResponseEntity.ok(JamathiFeedBackDtoConverterToEntity.toDTO(jamathiFeedBackEntity));
    }
}
