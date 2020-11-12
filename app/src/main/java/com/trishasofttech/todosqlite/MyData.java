package com.trishasofttech.todosqlite;

public class MyData {
    String name,task,taskdesc;

    public MyData()
    {

    }
    public MyData(String name, String task, String taskdesc) {
        this.name = name;
        this.task = task;
        this.taskdesc = taskdesc;
    }

    public String getName() {
        return name;
    }

    public String getTask() {
        return task;
    }

    public String getTaskdesc() {
        return taskdesc;
    }
}
