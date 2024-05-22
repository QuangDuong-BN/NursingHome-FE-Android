package com.example.nursinghome_android.entityDTO;

public class VisitRecord {
    private int id;
    private User user;
    private String timeOfDay;
    private String visitDate;

    public VisitRecord(int id, User user, String timeOfDay, String visitDate) {
        this.id = id;
        this.user = user;
        this.timeOfDay = timeOfDay;
        this.visitDate = visitDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }
}
