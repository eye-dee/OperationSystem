package com.operation.system.tasks;

import com.operation.system.model.MainEnrty;

public class PrintingEntry extends MainEnrty {
    public PrintingEntry(String taskName, int taskId){
        super(taskName,taskId);
    }

    @Override
    public void run() {
        logger.info("Start " + taskName);

        logger.info("PrintEnrty.run()");

        logger.info("End " + taskName);
        taskQueue.terminateTask();
    }
}
