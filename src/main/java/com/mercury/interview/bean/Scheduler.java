package com.mercury.interview.bean;

public class Scheduler {
    private String name;
    private int number;
    private  double rate;

    public Scheduler() {
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Scheduler(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public Scheduler(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", rate=" + rate +
                '}';
    }
}
