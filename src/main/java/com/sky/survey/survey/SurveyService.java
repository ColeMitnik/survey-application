package com.sky.survey.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + id));
    }

    public Survey createSurvey(Survey survey) {
        // Add any business logic here
        return surveyRepository.save(survey);
    }

    public Survey updateSurvey(Long id, Survey surveyDetails) {
        Survey survey = getSurveyById(id);
        // Update all fields
        survey.setTitle(surveyDetails.getTitle());
        survey.setDescription(surveyDetails.getDescription());
        survey.setUser(surveyDetails.getUser());
        survey.setTotalQuestions(surveyDetails.getTotalQuestions());
        survey.setStartDate(surveyDetails.getStartDate());
        survey.setEndDate(surveyDetails.getEndDate());
        survey.setActive(surveyDetails.isActive());
        survey.setWelcomeMessage(surveyDetails.getWelcomeMessage());
        survey.setCompletionMessage(surveyDetails.getCompletionMessage());
        survey.setQuestions(surveyDetails.getQuestions());
        return surveyRepository.save(survey);
    }

    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }
}