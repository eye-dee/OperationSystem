package com.operation.system.model;

import com.operation.system.service.TaskQueue;

public interface Entry {
    void run();

    void setTaskQueue(TaskQueue taskQueue);
}
