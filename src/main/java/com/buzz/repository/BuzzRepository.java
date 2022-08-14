package com.buzz.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import com.buzz.entity.BuzzEntity;

@Mapper
public interface BuzzRepository extends JpaRepository<BuzzEntity, String> {

  List<BuzzEntity> findAll();

}
