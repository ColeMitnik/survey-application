package com.sky.survey.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResponseService {
    @Autowired
    private ResponseRepository responseRepository;

    public List<Response> getAllResponses() {
        return responseRepository.findAll();
    }

    public Response getResponseById(Long id) {
        return responseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Response not found with id: " + id));
    }

    public List<Response> getResponsesBySurveyId(Long surveyId) {
        return responseRepository.findBySurvey_SurveyId(surveyId);
    }

    public List<Response> getResponsesByUserId(Long userId) {
        return responseRepository.findByUser_UserId(userId);
    }

    public List<Response> getResponsesBySessionId(String sessionId) {
        return responseRepository.findBySessionId(sessionId);
    }

    public Page<Response> getResponsesBySurveyId(Long surveyId, Pageable pageable) {
        return responseRepository.findBySurvey_SurveyId(surveyId, pageable);
    }

    public Page<Response> getResponsesBySurveyIdAndUserEmail(Long surveyId, String email, Pageable pageable) {
        return responseRepository.findBySurvey_SurveyIdAndUser_Email(surveyId, email, pageable);
    }

    // Updated method to use dateModified instead of completionDate
    public Page<Response> getResponsesBySurveyIdAndDateRange(Long surveyId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return responseRepository.findBySurvey_SurveyIdAndDateModifiedBetween(surveyId, startDate, endDate, pageable);
    }

    // New methods to match the added repository methods
    public Page<Response> getResponsesBySurveyIdAndCompletionStatus(Long surveyId, boolean isComplete, Pageable pageable) {
        return responseRepository.findBySurvey_SurveyIdAndIsComplete(surveyId, isComplete, pageable);
    }

    public Page<Response> getResponsesBySurveyIdAndProgressStatus(Long surveyId, Response.InProgressStatus inProgress, Pageable pageable) {
        return responseRepository.findBySurvey_SurveyIdAndInProgress(surveyId, inProgress, pageable);
    }

    public Response createResponse(Response response) {
        return responseRepository.save(response);
    }

    public Response updateResponse(Long id, Response responseDetails) {
        Response response = getResponseById(id);
        response.setUser(responseDetails.getUser());
        response.setSurvey(responseDetails.getSurvey());
        response.setSessionId(responseDetails.getSessionId());
        response.setComplete(responseDetails.isComplete());
        response.setInProgress(responseDetails.getInProgress());

        return responseRepository.save(response);
    }

    public void deleteResponse(Long id) {
        responseRepository.deleteById(id);
    }
}