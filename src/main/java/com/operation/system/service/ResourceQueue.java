package com.operation.system.service;

import com.operation.system.Constants;
import com.operation.system.model.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static com.operation.system.Constants.INSERT_TO_HEAD;
import static com.operation.system.Constants.MAX_RESOURCE;

public class ResourceQueue {
    private List<Resource> resources = new ArrayList<>(Constants.MAX_RESOURCE);
    private TaskQueue taskQueue;
    private Logger logger;
    private AtomicInteger freeResource;
    private AtomicInteger runningTask;

    public ResourceQueue(AtomicInteger freeResource, AtomicInteger runningTask){
        this.freeResource = freeResource;
        this.runningTask = runningTask;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setTaskQueue(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void getResource(int priority, String name){
        logger.info("getResource " + name);

        int freeOccupy = freeResource.get();

        freeResource.set(resources.get(freeResource.get()).getPriority());

        resources.get(freeOccupy).setPriority(priority);
        resources.get(freeOccupy).setTask(runningTask.get());
        resources.get(freeOccupy).setName(name);

        if (taskQueue.getRunningTaskCeilingPriority() < priority){
            taskQueue.setRunningTaskCeilingPriority(priority);
        }
    }

    public void releaseResource(int priority, String name){
        logger.info("releaseResource " + name);

        int i,resourceIndex = -1;
        if (taskQueue.getRunningTaskCeilingPriority() == priority) {
            int resPriority, taskPriority;
            int ourTask = runningTask.get();

            taskPriority = taskQueue.getRunningTaskPriority();

            for (i = 0; i < MAX_RESOURCE; ++i) {
                if (resources.get(i).getTask() != runningTask.get()) {
                    continue;
                }

                resPriority = resources.get(i).getPriority();

                if (resPriority == priority && name.equals(resources.get(i).getName())) {
                    resourceIndex = i;
                } else {
                    if (resPriority > taskPriority) {
                        taskPriority = resPriority;
                    }
                }
            }

            taskQueue.setRunningTaskCeilingPriority(taskPriority);
            runningTask.set(taskQueue.getRunningTaskRef());

            taskQueue.schedule(ourTask, INSERT_TO_HEAD);

            if (resourceIndex == -1) {
                throw new IllegalArgumentException("resourceIndex == -1 => can't find runningTask in resources");
            }

            resources.get(resourceIndex).setPriority(priority);
            resources.get(resourceIndex).setTask(-1);
            freeResource.set(resourceIndex);

            if (ourTask != runningTask.get()) {
                taskQueue.dispatch(ourTask);
            }
        } else {
            resourceIndex = 0;

            while (resources.get(resourceIndex).getTask() != runningTask.get() ||
                    resources.get(resourceIndex).getPriority() != priority ||
                    !name.equals(resources.get(resourceIndex).getName())){
                resourceIndex++;
            }

            resources.get(resourceIndex).setPriority(freeResource.get());
            resources.get(resourceIndex).setTask(-1);
            freeResource.set(resourceIndex);
        }
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
