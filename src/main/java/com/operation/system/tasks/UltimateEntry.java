package com.operation.system.tasks;

import com.operation.system.model.MainEnrty;
import com.operation.system.model.Resource;
import com.operation.system.model.Task;
import com.operation.system.service.ResourceQueue;

import java.util.List;

/**
 * Created by Игорь on 01.03.2017.
 */
public class UltimateEntry extends MainEnrty {
    private ResourceQueue resourceQueue;
    private List<Task> needActivateTasks;
    private List<Resource> needGetResources;

    public UltimateEntry(String taskName, int taskId){
        super(taskName,taskId);
    }

    public void setResourceQueue(ResourceQueue resourceQueue) {
        this.resourceQueue = resourceQueue;
    }

    public void setNeedActivateTasks(List<Task> needActivateTasks) {
        this.needActivateTasks = needActivateTasks;
    }

    public void setNeedGetResources(List<Resource> needGetResources) {
        this.needGetResources = needGetResources;
    }

    @Override
    public void run() {
        logger.info("Start " + taskName);

        if (needActivateTasks.size() > 0) {
            for (Task needActivateTask : needActivateTasks) {
                //// TODO: 27.02.2017 getTaskPriorityFromId
                taskQueue.activateTask(needActivateTask.getEntry(), needActivateTask.getPriority(), needActivateTask.getName());
            }
        }

        if (needGetResources.size() > 0) {
            for (Resource needGetResource : needGetResources) {
                resourceQueue.getResource(needGetResource.getPriority(), needGetResource.getName());
                resourceQueue.releaseResource(needGetResource.getPriority(), needGetResource.getName());
            }
        }

        logger.info("End " + taskName);
        taskQueue.terminateTask();
    }
}
