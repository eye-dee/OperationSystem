package com.operation.system.service;

import com.operation.system.Constants;
import com.operation.system.model.Entry;
import com.operation.system.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static com.operation.system.Constants.INSERT_TO_TAIL;

public class TaskQueue {
    private List<Task> tasks;
    private List<Task> buffer = new ArrayList<>(Constants.MAX_TASK);
    private Logger logger;
    private AtomicInteger runningTask;
    private AtomicInteger freeTask;

    public TaskQueue(AtomicInteger runningTask, AtomicInteger freeTask){
        this.runningTask = runningTask;
        this.freeTask = freeTask;

        for (int i = 0; i < Constants.MAX_TASK - 1; ++i){
            buffer.add(new Task(i + 1));
        }
        buffer.add(new Task(-1));
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
   }

    public Task getMainTask(){
        return tasks.get(0);
    }

    public void activateTask(Entry entry, int priority, String name){
        logger.info("ActivateTask " + name);

        int task = runningTask.get();

        int occupy = freeTask.get();
        freeTask.set(buffer.get(occupy).getRef());

        buffer.get(occupy).setPriority(priority);
        buffer.get(occupy).setCeilingPriority(priority);
        buffer.get(occupy).setName(name);
        buffer.get(occupy).setEntry(entry);

        schedule(occupy,INSERT_TO_TAIL);

        if (task != runningTask.get()){
            dispatch(task);
        }

        logger.info("End of activateTask " + name);
    }

    public void terminateTask(){
        logger.info("terminateTask " + buffer.get(runningTask.get()).getName());

        int task = runningTask.get();
        runningTask.set(buffer.get(task).getRef());

        buffer.get(task).setRef(freeTask.get());
        freeTask.set(task);

        logger.info("end of terminateTask" + buffer.get(task).getName());
    }

    public void schedule(int task, int mode){
        logger.info("Schedule " + buffer.get(task).getName());

        int cur = runningTask.get();
        int prev = -1;

        int priority = buffer.get(task).getCeilingPriority();

        while (cur != -1 && buffer.get(cur).getCeilingPriority() > priority){
            prev = cur;
            cur = buffer.get(cur).getRef();
        }

        if (mode == INSERT_TO_TAIL){
            while (cur != -1 && buffer.get(cur).getCeilingPriority() == priority){
                prev = cur;
                cur = buffer.get(cur).getRef();
            }
        }

        buffer.get(task).setRef(cur);

        if (prev==-1){
            runningTask.set(task);
        } else {
            buffer.get(prev).setRef(task);
        }

        logger.info("End fo Schedule" + buffer.get(task).getName());
    }

    public void dispatch(int task){
        logger.info("dispatch");

        do {
            buffer.get(runningTask.get()).entry();
        } while (runningTask.get() != task);

        logger.info("end of dispatch");
    }

    public int getRunningTaskCeilingPriority(){
        return buffer.get(runningTask.get()).getCeilingPriority();
    }
    public void setRunningTaskCeilingPriority(int priority){
        buffer.get(runningTask.get()).setCeilingPriority(priority);
    }

    public int getRunningTaskRef(){
        return buffer.get(runningTask.get()).getRef();
    }

    public int getRunningTaskPriority(){
        return buffer.get(runningTask.get()).getPriority();
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
