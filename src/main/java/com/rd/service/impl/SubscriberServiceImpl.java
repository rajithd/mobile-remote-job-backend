package com.rd.service.impl;

import com.rd.dto.GithubResponse;
import com.rd.dto.JobInfoDto;
import com.rd.dto.RemoteOkResponse;
import com.rd.dto.SubscriberDto;
import com.rd.service.Subscriber;
import com.rd.service.SubscriberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.rd.service.Constants.GITHUB;
import static com.rd.service.Constants.REMOTE_OK;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final RestTemplate restTemplate;
    private final String gitHubApiUrl;
    private final String remoteOkApiUrl;

    public SubscriberServiceImpl(RestTemplate restTemplate, @Value("${subscriber.github.api.url}") String gitHubApiUrl,
                                 @Value("${subscriber.remoteok.api.url}") String remoteOkApiUrl) {
        this.restTemplate = restTemplate;
        this.gitHubApiUrl = gitHubApiUrl;
        this.remoteOkApiUrl = remoteOkApiUrl;
    }

    @Override
    public List<SubscriberDto> getAllSubscribers() {
        List<SubscriberDto> subscriberDtos = new ArrayList<>();
        for (Subscriber subscriber : Subscriber.values()) {
            subscriberDtos.add(new SubscriberDto(subscriber.getId(), subscriber.getName()));
        }
        return subscriberDtos;
    }

    @Override
    public List<JobInfoDto> getJobInfoById(int id) {
        Optional<Subscriber> subscriber = Subscriber.getById(id);
        if (!subscriber.isPresent()) {
            throw new UnsupportedOperationException("");
        }
        switch (subscriber.get().getName()) {
            case GITHUB:
                return handleGithubResponse();
            case REMOTE_OK:
                return handleRemoteOkResponse();
            default:
                throw new UnsupportedOperationException("Unsupported service");
        }
    }

    private List<JobInfoDto> handleGithubResponse() {
        ResponseEntity<GithubResponse[]> entity = restTemplate.getForEntity(gitHubApiUrl, GithubResponse[].class);
        if (Objects.requireNonNull(entity.getBody()).length == 0) {
            return Collections.emptyList();
        }
        List<GithubResponse> githubResponses = Arrays.asList(entity.getBody());
        if (CollectionUtils.isEmpty(githubResponses)) {
            return Collections.emptyList();
        }
        List<JobInfoDto> jobInfoDtos = new ArrayList<>();
        for (GithubResponse githubResponse : githubResponses) {
            JobInfoDto jobInfoDto = JobInfoDto.builder().id(githubResponse.getId()).company(githubResponse.getCompany())
                    .description(githubResponse.getDescription()).position(githubResponse.getTitle())
                    .companyLogo(githubResponse.getCompanyIconUrl()).url(githubResponse.getUrl()).build();
            jobInfoDtos.add(jobInfoDto);
        }
        return jobInfoDtos;
    }

    private List<JobInfoDto> handleRemoteOkResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
                " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<RemoteOkResponse[]> responseEntity = restTemplate.exchange(remoteOkApiUrl, HttpMethod.GET, entity, RemoteOkResponse[].class);
        if (Objects.requireNonNull(responseEntity.getBody()).length == 0) {
            return Collections.emptyList();
        }
        List<RemoteOkResponse> remoteOkResponses = Arrays.asList(responseEntity.getBody());
        if (CollectionUtils.isEmpty(remoteOkResponses)) {
            return Collections.emptyList();
        }
        List<JobInfoDto> jobInfoDtos = new ArrayList<>();

        for (RemoteOkResponse remoteOkResponse : remoteOkResponses) {
            if (StringUtils.isEmpty(remoteOkResponse.getId())) {
                continue;
            }
            JobInfoDto jobInfoDto = JobInfoDto.builder().id(remoteOkResponse.getId()).company(remoteOkResponse.getCompany())
                    .description(remoteOkResponse.getDescription()).position(remoteOkResponse.getPosition())
                    .companyLogo(remoteOkResponse.getCompanyLogo()).url(remoteOkResponse.getUrl()).build();
            jobInfoDtos.add(jobInfoDto);
        }
        return jobInfoDtos;
    }
}
