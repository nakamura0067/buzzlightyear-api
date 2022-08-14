package com.buzz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buzz.model.Buzz;
import com.buzz.repository.BuzzRepository;

@Service
public class BuzzService {

  @Autowired
  BuzzRepository repository;

  public List<Buzz> findAll(){
    return repository.findAll().stream().map(buzz -> {
      return Buzz.builder()
          .rank(buzz.getRank())
          .title(buzz.getTitle())
          .description(buzz.getDescription())
          .createdDate(buzz.getCreatedDate())
          .updatedDate(buzz.getUpdatedDate())
          .build();
    }).collect(Collectors.toList());
  }
}
