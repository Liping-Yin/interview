package com.example.interview;

import java.time.Instant;

public class Container {
    //    Other properties are omitted for simplicity
    private String id;
    private Instant updatedDate;

    public Container(Instant updatedDate, String id) {
        this.id = id;
        this.updatedDate = updatedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Container{" +
                "id='" + id + '\'' +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
