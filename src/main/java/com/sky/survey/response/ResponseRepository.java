package com.sky.survey.response;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findBySurvey_SurveyId(Long surveyId);
    List<Response> findByUser_UserId(Long userId);
    List<Response> findBySessionId(String sessionId);
}