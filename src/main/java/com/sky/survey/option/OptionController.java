package com.sky.survey.option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/options")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @GetMapping
    public ResponseEntity<List<Option>> getAllOptions() {
        return ResponseEntity.ok(optionService.getAllOptions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Option> getOptionById(@PathVariable Long id) {
        return ResponseEntity.ok(optionService.getOptionById(id));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Option>> getOptionsByQuestionId(@PathVariable Long questionId) {
        return ResponseEntity.ok(optionService.getOptionsByQuestionId(questionId));
    }

    @PostMapping
    public ResponseEntity<Option> createOption(@Valid @RequestBody Option option) {
        return ResponseEntity.status(HttpStatus.CREATED).body(optionService.createOption(option));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Option> updateOption(@PathVariable Long id, @Valid @RequestBody Option optionDetails) {
        return ResponseEntity.ok(optionService.updateOption(id, optionDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long id) {
        optionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }
}