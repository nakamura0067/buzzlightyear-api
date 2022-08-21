package com.buzz.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
          .id(buzz.getId())
          .ranking(buzz.getRanking())
          .title(buzz.getTitle())
          .description(buzz.getDescription())
          .createdDate(buzz.getCreatedDate())
          .updatedDate(buzz.getUpdatedDate())
          .build();
    }).collect(Collectors.toList());
  }

  public Integer register(Buzz buzz) {
    BuzzEntity entity = convertEntity(buzz);
    entity.setCreatedDate(nowDate);
    entity.setUpdatedDate(nowDate);
    Integer id = repository.saveAndFlush(entity).getId();

    // 登録されたランキング以降のランキングを振りなおす
    List<Buzz> buzzList = this.findAll();
    buzzList.stream()
      .filter(element -> Integer.valueOf(element.getRanking()) >= Integer.valueOf(buzz.getRanking()))
      .sorted((e1, e2) -> Integer.valueOf(e1.getRanking()) - Integer.valueOf(e2.getRanking()))
      .forEach(element -> {
        element.setRanking(String.valueOf(Integer.valueOf(element.getRanking())+1));
        this.update(element);
      });
    return id;
  }

  public void update(Buzz buzz) {
    BuzzEntity entity = convertEntity(buzz);
    entity.setUpdatedDate(nowDate);
    repository.saveAndFlush(entity);
  }

  public void updateAll(List<Buzz> buzzes) {
    List<BuzzEntity> updateEntities = buzzes.stream().map(buzz -> {
      BuzzEntity entity = convertEntity(buzz);
      entity.setUpdatedDate(nowDate);
      return entity;
    }).collect(Collectors.toList());
    repository.saveAllAndFlush(updateEntities);
  }

  public void deleteById(Integer id) {
    String ranking = repository.findById(id).get().getRanking();
    repository.deleteById(id);

    // 削除されたランキング以降のランキングを振りなおす
    List<Buzz> buzzList = this.findAll();
    // ランキングを振りなおす
    buzzList.stream()
      .filter(element -> Integer.valueOf(element.getRanking()) >= Integer.valueOf(ranking))
      .sorted((e1, e2) -> Integer.valueOf(e1.getRanking()) - Integer.valueOf(e2.getRanking()))
      .forEach(element -> {
        element.setRanking(String.valueOf(buzzList.indexOf(element)-1));
        this.update(element);
      });
  }

  public void deleteAllById(List<Integer> ids) {
    repository.deleteAllById(ids);

    // 削除後のランキングを更新する
    List<Buzz> buzzList = this.findAll();
    List<Buzz> updateBuzzList = new ArrayList<>();

    buzzList.stream().sorted((e1, e2) -> Integer.valueOf(e1.getRanking()) - Integer.valueOf(e2.getRanking()))
      .forEach(element -> {
        element.setRanking(String.valueOf(updateBuzzList.size()+1));
        updateBuzzList.add(element);
      });
    this.updateAll(updateBuzzList);
  }

  private BuzzEntity convertEntity(Buzz buzz) {
    BuzzEntity entity = new BuzzEntity();
    entity.setId(buzz.getId());
    entity.setRanking(buzz.getRanking());
    entity.setTitle(buzz.getTitle());
    entity.setDescription(buzz.getDescription());
    return entity;
  }
}
