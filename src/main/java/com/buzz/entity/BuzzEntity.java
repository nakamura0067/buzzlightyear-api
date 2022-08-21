package com.buzz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_buzz")
public class BuzzEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String ranking;
  private String title;
  private String description;

  @Column(name="created_date")
  private Date createdDate;

  @Column(name="updated_date")
  private Date updatedDate;
}
