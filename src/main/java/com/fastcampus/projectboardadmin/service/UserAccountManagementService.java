package com.fastcampus.projectboardadmin.service;

import com.fastcampus.projectboardadmin.dto.UserAccountDto;
import com.fastcampus.projectboardadmin.dto.properties.ProjectProperties;
import com.fastcampus.projectboardadmin.dto.response.UserAccountClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserAccountManagementService {

    private final RestTemplate restTemplate;
    private final ProjectProperties projectProperties;

    public List<UserAccountDto> getUserAccounts(){
        URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.board().url()+"/api/userAccounts")
                .queryParam("size",10000)
                .build()
                .toUri();
        UserAccountClientResponse response = restTemplate.getForObject(uri, UserAccountClientResponse.class);

        return Optional.ofNullable(response).orElseGet(UserAccountClientResponse::empty).userAccounts();
    }
    public UserAccountDto getUserAccount(String userId){
        URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.board().url()+"/api/userAccounts/" + userId)
                .build()
                .toUri();
        UserAccountDto response = restTemplate.getForObject(uri, UserAccountDto.class);
        return Optional.ofNullable(response)
                .orElseThrow(()->new NoSuchElementException("회원이 없습니다"));
    }
    public void deleteUserAccount(String userId){
        URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.board().url()+"/api/userAccounts/" + userId)
                .build()
                .toUri();
        restTemplate.delete(uri);
    }


}
