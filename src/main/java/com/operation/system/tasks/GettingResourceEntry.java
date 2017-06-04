package com.operation.system.tasks;

import com.operation.system.model.MainEnrty;
import com.operation.system.model.Resource;
import com.operation.system.service.ResourceQueue;

public class GettingResourceEntry extends MainEnrty{
    private Resource resource;
    private ResourceQueue resourceQueue;
    public GettingResourceEntry(String taskName, int taskId, Resource resource){
        super(taskName,taskId);
        this.resource = resource;
    }

    public void setResourceQueue(ResourceQueue resourceQueue) {
        this.resourceQueue = resourceQueue;
    }

    @Override
    public void run() {
        logger.info("Start " + taskName);

        resourceQueue.getResource(resource.getPriority(),resource.getName());
        resourceQueue.releaseResource(resource.getPriority(),resource.getName());

        logger.info("End " + taskName);
        taskQueue.terminateTask();
    }
}
