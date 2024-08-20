package com.sky.survey.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findBySurvey_SurveyId(Long surveyId);
    List<Response> findByUser_UserId(Long userId);
    List<Response> findBySessionId(String sessionId);

    Page<Response> findBySurvey_SurveyId(Long surveyId, Pageable pageable);
    Page<Response> findBySurvey_SurveyIdAndUser_Email(Long surveyId, String email, Pageable pageable);

    // Replace completionDate with dateModified or dateCreated
    Page<Response> findBySurvey_SurveyIdAndDateModifiedBetween(Long surveyId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Additional methods that might be useful
    Page<Response> findBySurvey_SurveyIdAndIsComplete(Long surveyId, boolean isComplete, Pageable pageable);
    Page<Response> findBySurvey_SurveyIdAndInProgress(Long surveyId, Response.InProgressStatus inProgress, Pageable pageable);
}