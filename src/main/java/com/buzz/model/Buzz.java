package com.buzz.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Buzz {

  private Integer ranking;
  private String title;
  private String description;
  private Date createdDate;
  private Date updatedDate;

}
