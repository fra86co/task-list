package com.codurance.training.command;

import com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class CheckCommand {
    private final PrintWriter out;
    private final Map<String, List<Task>> tasks;

    public CheckCommand(PrintWriter writer, Map<String, List<Task>> tasks) {
        this.out = writer;
        this.tasks = tasks;
    }

    public void invoke(String taskId, boolean done) {
        int id = Integer.parseInt(taskId);
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }
}
