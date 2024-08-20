package com.sky.survey.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private static final Logger logger = Logger.getLogger(QuestionController.class.getName());

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<Page<Question>> getQuestions(@RequestParam(required = false) Long surveyId, Pageable pageable) {
        try {
            Page<Question> questions = questionService.getQuestions(surveyId, pageable);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.severe("Error getting questions: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        try {
            Question createdQuestion = questionService.createQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
        } catch (Exception e) {
            logger.severe("Error creating question: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable Long id) {
        try {
            Question question = questionService.getQuestion(id);
            return ResponseEntity.ok(question);
        } catch (RuntimeException e) {
            logger.warning("Question not found with ID: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.severe("Error getting question: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question questionDetails) {
        try {
            Question updatedQuestion = questionService.updateQuestion(id, questionDetails);
            return ResponseEntity.ok(updatedQuestion);
        } catch (RuntimeException e) {
            logger.warning("Question not found with ID: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.severe("Error updating question: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.severe("Error deleting question: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<Page<Question>> getQuestionsBySurvey(@PathVariable Long surveyId, Pageable pageable) {
        try {
            Page<Question> questions = questionService.getQuestionsBySurvey(surveyId, pageable);
            return ResponseEntity.ok(questions);
        } catch (RuntimeException e) {
            logger.warning("Survey not found with ID: " + surveyId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.severe("Error getting questions by survey: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/survey/{surveyId}")
    public ResponseEntity<Question> createQuestionForSurvey(@PathVariable Long surveyId, @RequestBody Question question) {
        try {
            Question createdQuestion = questionService.createQuestionForSurvey(surveyId, question);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
        } catch (RuntimeException e) {
            logger.warning("Survey not found with ID: " + surveyId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.severe("Error creating question for survey: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}