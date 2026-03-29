package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.BroadCastMessageDto;
import com.tpty.tableegi.jamath.dto.JamathFeedBackDto;
import com.tpty.tableegi.jamath.entity.BroadCastMessageEntity;
import com.tpty.tableegi.jamath.entity.JamathiFeedBackEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;

public interface BroadCastMessageService {

    public BroadCastMessageEntity publishMessage(BroadCastMessageDto messageDto) throws InvalidDataException;
}
