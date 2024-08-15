package com.sky.survey.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Response createResponse(Response response) {
        response.updateProgress();
        return responseRepository.save(response);
    }

    public Response updateResponse(Long id, Response responseDetails) {
        Response response = getResponseById(id);
        response.setUser(responseDetails.getUser());
        response.setSurvey(responseDetails.getSurvey());
        response.setResponseType(responseDetails.getResponseType());
        response.setSessionId(responseDetails.getSessionId());
        response.setQuestionsAnswered(responseDetails.getQuestionsAnswered());
        response.updateProgress();
        return responseRepository.save(response);
    }

    public void deleteResponse(Long id) {
        responseRepository.deleteById(id);
    }
}