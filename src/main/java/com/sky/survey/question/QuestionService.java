package com.sky.survey.question;

import com.sky.survey.survey.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class QuestionService {

    private static final Logger logger = Logger.getLogger(QuestionService.class.getName());

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    public Page<Question> getQuestions(Long surveyId, Pageable pageable) {
        Page<Question> questions;
        if (surveyId != null) {
            logger.info("Fetching questions for survey ID: " + surveyId);
            questions = questionRepository.findBySurvey_SurveyId(surveyId, pageable);
        } else {
            logger.info("Fetching all questions");
            questions = questionRepository.findAll(pageable);
        }
        logger.info("Retrieved " + questions.getTotalElements() + " questions");
        return questions;
    }

    public Question createQuestion(Question question) {
        logger.info("Creating new question: " + question);
        Question savedQuestion = questionRepository.save(question);
        logger.info("Created question with ID: " + savedQuestion.getQuestionId());
        return savedQuestion;
    }

    public Question getQuestion(Long id) {
        logger.info("Fetching question with ID: " + id);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warning("Question not found with ID: " + id);
                    return new RuntimeException("Question not found");
                });
        logger.info("Retrieved question: " + question);
        return question;
    }

    public Question updateQuestion(Long id, Question questionDetails) {
        logger.info("Updating question with ID: " + id);
        Question question = getQuestion(id);
        question.setQuestionText(questionDetails.getQuestionText());
        question.setQuestionType(questionDetails.getQuestionType());
        question.setRequired(questionDetails.isRequired());
        Question updatedQuestion = questionRepository.save(question);
        logger.info("Updated question: " + updatedQuestion);
        return updatedQuestion;
    }

    public void deleteQuestion(Long id) {
        logger.info("Deleting question with ID: " + id);
        questionRepository.deleteById(id);
        logger.info("Deleted question with ID: " + id);
    }

    public Page<Question> getQuestionsBySurvey(Long surveyId, Pageable pageable) {
        logger.info("Fetching questions for survey ID: " + surveyId);
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + surveyId));
        Page<Question> questions = questionRepository.findBySurvey(survey, pageable);
        logger.info("Retrieved " + questions.getTotalElements() + " questions for survey ID: " + surveyId);
        return questions;
    }

    public Question createQuestionForSurvey(Long surveyId, Question question) {
        logger.info("Creating new question for survey ID: " + surveyId);
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + surveyId));
        question.setSurvey(survey);
        Question savedQuestion = questionRepository.save(question);
        logger.info("Created question with ID: " + savedQuestion.getQuestionId() + " for survey ID: " + surveyId);
        return savedQuestion;
    }
}