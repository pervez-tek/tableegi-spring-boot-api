package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.JamathFeedBackDto;
import com.tpty.tableegi.jamath.entity.BroadCastMessageEntity;
import com.tpty.tableegi.jamath.entity.JamathiFeedBackEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.repo.BroadCastRepo;
import com.tpty.tableegi.jamath.repo.JamathiFeedBackRepo;
import com.tpty.tableegi.jamath.utils.BroadCastMsgDtoConverterToEntity;
import com.tpty.tableegi.jamath.utils.JamathiFeedBackDtoConverterToEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FeedBackServiceImpl implements FeedBackService {

    private JamathiFeedBackRepo jamathiFeedBackRepo;
    private PasswordEncoder passwordEncoder;

    public FeedBackServiceImpl(JamathiFeedBackRepo jamathiFeedBackRepo, PasswordEncoder passwordEncoder) {
        this.jamathiFeedBackRepo = jamathiFeedBackRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JamathiFeedBackEntity sendFeedBack(JamathFeedBackDto feedBackDto) throws InvalidDataException {
        if (feedBackDto.getFeedBack() == null || feedBackDto.getEmail() == null || feedBackDto.getName() == null || feedBackDto.getRating() <= 0) {
            throw new InvalidDataException("Data is missing.");
        }
        if (feedBackDto.getFeedBack().trim().equalsIgnoreCase("") ||
                feedBackDto.getEmail().equalsIgnoreCase("") ||
                feedBackDto.getName().equalsIgnoreCase("")) {
            throw new InvalidDataException("Please provide valid Data.");
        }
        JamathiFeedBackEntity entity = JamathiFeedBackDtoConverterToEntity.toEntity(feedBackDto);

        return jamathiFeedBackRepo.save(entity);
    }
}
