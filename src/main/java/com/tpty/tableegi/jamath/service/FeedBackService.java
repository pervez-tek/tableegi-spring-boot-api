package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.JamathFeedBackDto;
import com.tpty.tableegi.jamath.entity.JamathiFeedBackEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;

public interface FeedBackService {

    public JamathiFeedBackEntity sendFeedBack(JamathFeedBackDto feedBackDto) throws InvalidDataException;
}
