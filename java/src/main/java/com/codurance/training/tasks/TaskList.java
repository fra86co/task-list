package com.codurance.training.tasks;

import com.codurance.training.command.AddCommand;
import com.codurance.training.command.CheckCommand;
import com.codurance.training.command.HelpCommand;
import com.codurance.training.command.ShowCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private final BufferedReader in;
    private final PrintWriter out;

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            execute(command);
        }
    }

    private void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                show();
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                check(commandRest[1]);
                break;
            case "uncheck":
                uncheck(commandRest[1]);
                break;
            case "help":
                help();
                break;
            default:
                error(command);
                break;
        }
    }

    private void show() {
        new ShowCommand(this.tasks, TaskList.this.out).execute();
    }

    private void add(String commandLine) {
        new AddCommand(TaskList.this.tasks, out).invoke(commandLine);
    }

    private void check(String taskId) {
        new CheckCommand(TaskList.this.out, TaskList.this.tasks).invoke(taskId, true);
    }

    private void uncheck(String taskId) {
        new CheckCommand(TaskList.this.out, TaskList.this.tasks).invoke(taskId, false);
    }

    private void help() {
        new HelpCommand(out).invoke();
    }

    private void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

}
