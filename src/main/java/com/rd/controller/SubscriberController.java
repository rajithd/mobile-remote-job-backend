package com.rd.controller;

import com.rd.dto.JobInfoDto;
import com.rd.dto.SubscriberDto;
import com.rd.service.SubscriberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubscriberController {

    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping("/subscribers")
    public List<SubscriberDto> getSubscribers() {
        return subscriberService.getAllSubscribers();
    }

    @GetMapping("/subscribers/{id}/jobs")
    public List<JobInfoDto> getJobsById(@PathVariable int id) {
        return subscriberService.getJobInfoById(id);
    }

}
