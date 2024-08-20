package com.sky.survey.question;

import com.sky.survey.survey.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findBySurvey(Survey survey, Pageable pageable);
    Page<Question> findBySurvey_SurveyId(Long surveyId, Pageable pageable);
}