package com.fastcampus.projectboardadmin.service;

import com.fastcampus.projectboardadmin.dto.ArticleDto;
import com.fastcampus.projectboardadmin.dto.properties.ProjectProperties;
import com.fastcampus.projectboardadmin.dto.response.ArticleClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleManagementService {

    private final RestTemplate restTemplate;
    private final ProjectProperties projectProperties;

    public List<ArticleDto> getArticles(){
        URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.board().url()+"/api/articles")
                .queryParam("size",10000)
                .build()
                .toUri();
        ArticleClientResponse response = restTemplate.getForObject(uri, ArticleClientResponse.class);

        return Optional.ofNullable(response).orElseGet(ArticleClientResponse::empty).articles();
    }

    public ArticleDto getArticle(Long articleId){
        URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.board().url()+"/api/articles/"+articleId)
                .build()
                .toUri();
        ArticleDto response = restTemplate.getForObject(uri,ArticleDto.class);
        return Optional.ofNullable(response).orElseThrow(()-> new NoSuchElementException("그런 게시물은 존재하지 않습니다"));
    }

    public void deleteArticle(Long articleId){
        URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.board().url()+"/api/articles/"+articleId)
                .build()
                .toUri();
        restTemplate.delete(uri);
    }





}
