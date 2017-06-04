package com.operation.system;

import com.operation.system.model.Entry;
import com.operation.system.model.Task;
import com.operation.system.service.ResourceQueue;
import com.operation.system.service.TaskQueue;

import java.util.logging.Logger;

public class OperationSystem {
    private TaskQueue taskQueue;
    private ResourceQueue resourceQueue;

    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
        taskQueue.setLogger(logger);
        resourceQueue.setLogger(logger);
    }

    public ResourceQueue getResourceQueue() {
        return resourceQueue;
    }

    public TaskQueue getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void setResourceQueue(ResourceQueue resourceQueue) {
        this.resourceQueue = resourceQueue;
    }

    public void start(){
        logger.info("Start OS");

        Task mainTask = taskQueue.getMainTask();

        activateTask(mainTask.getEntry(), mainTask.getPriority(),mainTask.getName());
    }
    public void stop(){
        logger.info("Shutdown OS");
    }
    public void activateTask(Entry entry, int priority, String name){
        taskQueue.activateTask(entry,priority,name);
    }
}
