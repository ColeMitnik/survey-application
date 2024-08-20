package com.sky.survey.survey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    // Find all active surveys
    List<Survey> findByIsActiveTrue();

    // Find surveys by user id (corrected)
    List<Survey> findByUser_UserId(Long userId);

    // Find surveys between start and end dates
    List<Survey> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);

    // Find surveys by title containing a specific string (case-insensitive)
    List<Survey> findByTitleContainingIgnoreCase(String titlePart);

    // Custom query to find surveys with a minimum number of questions
    @Query("SELECT s FROM Survey s WHERE s.totalQuestions >= :minQuestions")
    List<Survey> findSurveysWithMinQuestions(@Param("minQuestions") int minQuestions);

    // Custom query to find surveys created by a specific user and are currently active
    @Query("SELECT s FROM Survey s WHERE s.user.userId = :userId AND s.isActive = true")
    List<Survey> findActiveUserSurveys(@Param("userId") Long userId);
}