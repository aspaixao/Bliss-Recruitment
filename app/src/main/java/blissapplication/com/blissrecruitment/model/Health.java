package blissapplication.com.blissrecruitment.model;

// Created by Alexandre Paixao on 3/17/2018.

public class Health {

    private String status;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Health() {}
    public Health(String status) {
        this.status = status;
    }
}
