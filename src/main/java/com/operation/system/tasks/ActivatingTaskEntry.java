package com.operation.system.tasks;

import com.operation.system.model.MainEnrty;
import com.operation.system.model.Task;

/**
 * Created by Игорь on 27.02.2017.
 */
public class ActivatingTaskEntry extends MainEnrty {
    Task childTask;
    public ActivatingTaskEntry(String taskName, int taskId){
        super(taskName,taskId);
    }

    public void setChildTask(Task childTask) {
        this.childTask = childTask;
    }

    @Override
    public void run() {
        logger.info("Start " + taskName);

        //// TODO: 27.02.2017 getTaskPriorityFromId 
        taskQueue.activateTask(childTask.getEntry(),childTask.getPriority(),childTask.getName());

        logger.info("End " + taskName);
        taskQueue.terminateTask();
    }
}
