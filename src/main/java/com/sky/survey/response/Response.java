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
    @Column(name = "response_id")
    private Long responseId;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "is_complete", nullable = false)
    private boolean isComplete;

    @Enumerated(EnumType.STRING)
    @Column(name = "in_progress", nullable = false)
    private InProgressStatus inProgress;

    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_modified", nullable = false)
    private LocalDateTime dateModified;

    // Enum definition
    public enum InProgressStatus {
        COMPLETED, ABANDONED, PENDING
    }

    // Getters and setters
    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public InProgressStatus getInProgress() {
        return inProgress;
    }

    public void setInProgress(InProgressStatus inProgress) {
        this.inProgress = inProgress;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    // Constructors, other methods...

    @PrePersist
    protected void onCreate() {
        dateCreated = dateModified = LocalDateTime.now();
        if (inProgress == null) {
            inProgress = InProgressStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        dateModified = LocalDateTime.now();
    }

    // toString method
    @Override
    public String toString() {
        return "Response{" +
                "responseId=" + responseId +
                ", survey=" + survey +
                ", user=" + user +
                ", sessionId='" + sessionId + '\'' +
                ", isComplete=" + isComplete +
                ", inProgress=" + inProgress +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                '}';
    }
}