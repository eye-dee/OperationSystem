package com.operation.system.model;


public class Resource {
    private int task;
    private int priority;
    private String name;

    public Resource(int priority, int task){
        this.priority = priority;
        this.task = task;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public int getPriority() {
        return priority;
    }

    public int getTask() {
        return task;
    }

    public String getName() {
        return name;
    }
}
