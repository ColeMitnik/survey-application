package com.sky.survey.question;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Entity
@Table( name = "questions", schema = "sky_survey")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long question_id;

    @Column(name = "survey_id")
    private Long survey_id;

    @Column(name = "question_text")
    private String question_text;

    @Column(name = "question_type")
    private String question_type;

    @Column(name = "is_required")
    private boolean is_required;

    @Column(name = "date_created")
    private LocalDateTime date_created;

    @Column(name = "date_modified")
    private LocalDateTime date_modified;

    // Default constructor
    public Question() {
    }

    // Getters and Setters
    public Long getQuestionId() {
        return question_id;
    }

    public void setQuestionId(Long question_id) {
        this.question_id = question_id;
    }

    public Long getSurveyId() {
        return survey_id;
    }

    public void setSurveyId(Long survey_id) {
        this.survey_id = survey_id;
    }

    public String getQuestionText() {
        return question_text;
    }

    public void setQuestionText(String question_text) {
        this.question_text = question_text;
    }

    public String getQuestionType() {
        return question_type;
    }

    public void setQuestionType(String question_type) {
        this.question_type = question_type;
    }

    public boolean isRequired() {
        return is_required;
    }

    public void setRequired(boolean required) {
        is_required = required;
    }

    public LocalDateTime getDateCreated() {
        return date_created;
    }

    public void setDateCreated(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    public LocalDateTime getDateModified() {
        return date_modified;
    }

    public void setDateModified(LocalDateTime date_modified) {
        this.date_modified = date_modified;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Question{" +
                "id=" + question_id +
                ", surveyId=" + survey_id +
                ", questionText='" + question_text + '\'' +
                ", questionType='" + question_type + '\'' +
                ", isRequired=" + is_required +
                ", dateCreated=" + date_created +
                ", dateModified=" + date_modified +
                '}';
    }
}