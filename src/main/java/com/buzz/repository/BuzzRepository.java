package com.buzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buzz.entity.BuzzEntity;

@Repository
public interface BuzzRepository extends JpaRepository<BuzzEntity, Integer> {
}
