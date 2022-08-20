package com.buzz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.buzz.model.Buzz;
import com.buzz.service.BuzzService;

@CrossOrigin
@RestController
public class BuzzController {

  @Autowired
  BuzzService service;

  @GetMapping("/buzz")
  public List<Buzz> findAll() {
    return service.findAll();
  }

  @PostMapping("/buzz")
  public void register(@RequestBody Buzz buzz) {
    service.register(buzz);
  }

  @PutMapping("/buzz")
  public void update(@RequestBody Buzz buzz) {
    service.update(buzz);
  }

  @DeleteMapping("/buzz")
  public void deleteAllById(@RequestBody List<String> rankings) {
    service.deleteAllById(rankings);
  }
}
