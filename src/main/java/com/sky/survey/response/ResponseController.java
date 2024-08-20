package com.sky.survey.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/surveys/{surveyId}/responses")
public class ResponseController {

    private static final Logger LOGGER = Logger.getLogger(ResponseController.class.getName());

    @Autowired
    private ResponseService responseService;

    @PostMapping
    public ResponseEntity<?> createResponse(@PathVariable Long surveyId, @RequestBody Response response) {
        try {
            if (!response.getSurvey().getSurveyId().equals(surveyId)) {
                return new ResponseEntity<>("Survey ID in path does not match the survey in the response", HttpStatus.BAD_REQUEST);
            }
            Response createdResponse = responseService.createResponse(response);
            return new ResponseEntity<>(createdResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating response", e);
            return new ResponseEntity<>("Error creating response: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getResponses(
            @PathVariable Long surveyId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            Page<Response> responsePage;
            if (email != null && !email.isEmpty()) {
                responsePage = responseService.getResponsesBySurveyIdAndUserEmail(surveyId, email, PageRequest.of(page, size));
            } else if (startDate != null && endDate != null) {
                responsePage = responseService.getResponsesBySurveyIdAndDateRange(surveyId, startDate, endDate, PageRequest.of(page, size));
            } else {
                responsePage = responseService.getResponsesBySurveyId(surveyId, PageRequest.of(page, size));
            }

            Map<String, Object> response = Map.of(
                    "responses", responsePage.getContent(),
                    "currentPage", responsePage.getNumber(),
                    "totalItems", responsePage.getTotalElements(),
                    "totalPages", responsePage.getTotalPages()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting responses", e);
            return new ResponseEntity<>("Error getting responses: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResponseById(@PathVariable Long surveyId, @PathVariable Long id) {
        try {
            Response response = responseService.getResponseById(id);
            if (!response.getSurvey().getSurveyId().equals(surveyId)) {
                return new ResponseEntity<>("Response does not belong to the specified survey", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Response not found", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting response", e);
            return new ResponseEntity<>("Error getting response: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateResponse(@PathVariable Long surveyId, @PathVariable Long id, @RequestBody Response responseDetails) {
        try {
            Response existingResponse = responseService.getResponseById(id);
            if (!existingResponse.getSurvey().getSurveyId().equals(surveyId)) {
                return new ResponseEntity<>("Response does not belong to the specified survey", HttpStatus.BAD_REQUEST);
            }
            Response updatedResponse = responseService.updateResponse(id, responseDetails);
            return new ResponseEntity<>(updatedResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Response not found", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating response", e);
            return new ResponseEntity<>("Error updating response: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResponse(@PathVariable Long surveyId, @PathVariable Long id) {
        try {
            Response response = responseService.getResponseById(id);
            if (!response.getSurvey().getSurveyId().equals(surveyId)) {
                return new ResponseEntity<>("Response does not belong to the specified survey", HttpStatus.BAD_REQUEST);
            }
            responseService.deleteResponse(id);
            return new ResponseEntity<>("Response deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Response not found", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting response", e);
            return new ResponseEntity<>("Error deleting response: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}