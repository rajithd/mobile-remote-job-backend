package com.rd.service;

import com.rd.dto.JobInfoDto;
import com.rd.dto.SubscriberDto;

import java.util.List;

public interface SubscriberService {

    List<SubscriberDto> getAllSubscribers();

    List<JobInfoDto> getJobInfoById(int id);

}
