package com.operation.system.model;

public class Task {
    private int ref;
    private int priority;
    private int ceilingPriority;
    private String name;
    private Entry entry;

    public Task(){}

    public Task(int ref){
        this.ref = ref;
    }

    public void entry(){
        entry.run();
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Entry getEntry() {
        return entry;
    }

    public int getCeilingPriority() {
        return ceilingPriority;
    }

    public int getRef() {
        return ref;
    }

    public void setCeilingPriority(int ceilingPriority) {
        this.ceilingPriority = ceilingPriority;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }
}
