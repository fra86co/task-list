package com.codurance.training.command;

import com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddCommand {

    private final PrintWriter writer;
    private static long lastId = 0;
    private final Map<String, List<Task>> tasks;

    public AddCommand(Map<String, List<Task>> tasks, PrintWriter out) {
        this.tasks = tasks;
        this.writer = out;
    }

    private static long nextId() {
        return ++lastId;
    }

    public void invoke(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(projectTask[0], projectTask[1]);
        }
    }

    private void addProject(String name) {
        tasks.put(name, new ArrayList<>());
    }

    private void addTask(String project, String description) {
        List<Task> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            writer.printf("Could not find a project with the name \"%s\".", project);
            writer.println();
            return;
        }
        projectTasks.add(new Task(nextId(), description, false));
    }
}
