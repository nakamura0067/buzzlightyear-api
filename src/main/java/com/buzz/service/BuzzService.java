package com.buzz.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buzz.entity.BuzzEntity;
import com.buzz.model.Buzz;
import com.buzz.repository.BuzzRepository;

@Service
public class BuzzService {

  @Autowired
  BuzzRepository repository;

  Date nowDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

  public List<Buzz> findAll(){
    return repository.findAll().stream().map(buzz -> {
      return Buzz.builder()
          .ranking(buzz.getRanking())
          .title(buzz.getTitle())
          .description(buzz.getDescription())
          .createdDate(buzz.getCreatedDate())
          .updatedDate(buzz.getUpdatedDate())
          .build();
    }).collect(Collectors.toList());
  }

  public void register(Buzz buzz) {
    BuzzEntity entity = convertEntity(buzz);
    entity.setCreatedDate(nowDate);
    entity.setUpdatedDate(nowDate);

    List<Buzz> buzzList = findAll();
    // キー重複を避けるため、降順で更新する
    buzzList.stream().filter(element -> entity.getRanking() <= element.getRanking())
        .sorted(Comparator.comparing(Buzz::getRanking).reversed()).forEach(element -> {
          element.setRanking(element.getRanking()+1);
          this.update(element);
        });
    repository.saveAndFlush(entity);
  }

  public void update(Buzz buzz) {
    BuzzEntity entity = convertEntity(buzz);
    entity.setUpdatedDate(nowDate);
    repository.saveAndFlush(entity);
  }

  public void deleteAllById(List<String> rankings) {
    repository.deleteAllById(rankings);

    // 削除後のランキングを更新する
    List<Buzz> buzzList = this.findAll();
    // キー重複を避けるため、昇順で更新する
    buzzList.stream().sorted().forEach(buzz->{
      buzz.setRanking(buzzList.indexOf(buzz)+1);
      this.update(buzz);
    });
  }

  private BuzzEntity convertEntity(Buzz buzz) {
    BuzzEntity entity = new BuzzEntity();
    entity.setRanking(Integer.valueOf(buzz.getRanking()));
    entity.setTitle(buzz.getTitle());
    entity.setDescription(buzz.getDescription());
    return entity;
  }
}
