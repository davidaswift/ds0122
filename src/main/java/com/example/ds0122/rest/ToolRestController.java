package com.example.ds0122.rest;

import com.example.ds0122.model.Tool;
import com.example.ds0122.repository.ToolRepository;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tools")
public class ToolRestController {

  @Autowired
  private ToolRepository toolRepository;

  @GetMapping
  public List<Tool> getAllTools() {
    return toolRepository.findAll();
  }

  @GetMapping("/{id}")
  public Tool getTool(@PathVariable String id) {
    return toolRepository.findById(Long.parseLong(id)).orElseThrow(EntityNotFoundException::new);
  }
}
