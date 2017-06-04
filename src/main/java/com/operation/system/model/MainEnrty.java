package com.operation.system.model;

import com.operation.system.service.TaskQueue;

import java.util.logging.Logger;

abstract public class MainEnrty implements Entry {
    protected TaskQueue taskQueue;
    protected String taskName;
    protected int taskId;
    protected Logger logger;

    public MainEnrty(String taskName, int taskId){
        logger = Logger.getLogger("MainEnrty");
        this.taskName = taskName;
        this.taskId = taskId;
    }

    public void setTaskQueue(TaskQueue taskQueue){
        this.taskQueue = taskQueue;
    }
}
