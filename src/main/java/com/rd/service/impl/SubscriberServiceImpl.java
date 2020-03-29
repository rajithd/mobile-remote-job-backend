package com.rd.service.impl;

import com.rd.dto.SubscriberDto;
import com.rd.service.Subscriber;
import com.rd.service.SubscriberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    @Override
    public List<SubscriberDto> getAllSubscribers() {
        List<SubscriberDto> subscriberDtos = new ArrayList<>();
        for (Subscriber subscriber : Subscriber.values()) {
            subscriberDtos.add(new SubscriberDto(subscriber.getId(), subscriber.getName()));
        }
        return subscriberDtos;
    }
}
