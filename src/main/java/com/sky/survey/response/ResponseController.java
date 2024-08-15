package com.sky.survey.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responses")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @GetMapping
    public ResponseEntity<List<Response>> getAllResponses() {
        List<Response> responses = responseService.getAllResponses();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResponseById(@PathVariable Long id) {
        try {
            Response response = responseService.getResponseById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<Response>> getResponsesBySurveyId(@PathVariable Long surveyId) {
        List<Response> responses = responseService.getResponsesBySurveyId(surveyId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Response>> getResponsesByUserId(@PathVariable Long userId) {
        List<Response> responses = responseService.getResponsesByUserId(userId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<Response>> getResponsesBySessionId(@PathVariable String sessionId) {
        List<Response> responses = responseService.getResponsesBySessionId(sessionId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createResponse(@RequestBody Response response) {
        try {
            Response createdResponse = responseService.createResponse(response);
            return new ResponseEntity<>(createdResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateResponse(@PathVariable Long id, @RequestBody Response responseDetails) {
        try {
            Response updatedResponse = responseService.updateResponse(id, responseDetails);
            return new ResponseEntity<>(updatedResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResponse(@PathVariable Long id) {
        try {
            responseService.deleteResponse(id);
            return new ResponseEntity<>("Response deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}