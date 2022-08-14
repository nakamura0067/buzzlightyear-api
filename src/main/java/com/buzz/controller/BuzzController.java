package com.buzz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buzz.model.Buzz;
import com.buzz.service.BuzzService;

@RestController
public class BuzzController {

  @Autowired
  BuzzService service;

  @CrossOrigin
  @GetMapping("/buzz")
  public List<Buzz> findAll() {
    return service.findAll();
  }
}
