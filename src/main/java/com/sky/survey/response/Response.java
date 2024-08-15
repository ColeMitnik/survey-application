package com.sky.survey.response;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.sky.survey.users.User;
import com.sky.survey.survey.Survey;

@Entity
@Table(name = "responses", schema = "sky_survey")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResponseType responseType;

    @Column(nullable = false)
    private String sessionId;

    @Column(nullable = false)
    private int questionsAnswered;

    @Column(nullable = false)
    private boolean isComplete;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InProgressStatus inProgress;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    private LocalDateTime dateModified;

    // Getters
    public Long getResponseId() {
        return responseId;
    }

    public User getUser() {
        return user;
    }

    public Survey getSurvey() {
        return survey;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public InProgressStatus getInProgress() {
        return inProgress;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    // Setters
    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setQuestionsAnswered(int questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setInProgress(InProgressStatus inProgress) {
        this.inProgress = inProgress;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    @PrePersist
    protected void onCreate() {
        dateCreated = dateModified = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateModified = LocalDateTime.now();
    }

    public void updateProgress() {
        this.isComplete = (this.questionsAnswered == this.survey.getTotalQuestions());
        this.inProgress = isComplete ? InProgressStatus.COMPLETED : InProgressStatus.PENDING;
    }

    // Constructors
    public Response() {
    }

    public Response(User user, Survey survey, ResponseType responseType, String sessionId,
                    int questionsAnswered, boolean isComplete, InProgressStatus inProgress) {
        this.user = user;
        this.survey = survey;
        this.responseType = responseType;
        this.sessionId = sessionId;
        this.questionsAnswered = questionsAnswered;
        this.isComplete = isComplete;
        this.inProgress = inProgress;
    }

    // Enum definitions
    public enum ResponseType {
        TEXT, MULTIPLE_CHOICE, RATING
    }

    public enum InProgressStatus {
        COMPLETED, ABANDONED, PENDING
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Response{" +
                "responseId=" + responseId +
                ", user=" + user +
                ", survey=" + survey +
                ", responseType=" + responseType +
                ", sessionId='" + sessionId + '\'' +
                ", questionsAnswered=" + questionsAnswered +
                ", isComplete=" + isComplete +
                ", inProgress=" + inProgress +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                '}';
    }
}
