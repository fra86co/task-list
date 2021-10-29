package com.codurance.training.tasks;

import com.codurance.training.command.*;

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
                new ShowCommand(this.tasks, this.out).execute();
                break;
            case "add":
                new AddCommand(this.tasks, out).invoke(commandRest[1]);
                break;
            case "check":
                new CheckCommand(this.out, this.tasks).invoke(commandRest[1], true);
                break;
            case "uncheck":
                new CheckCommand(this.out, this.tasks).invoke(commandRest[1], false);
                break;
            case "help":
                new HelpCommand(out).invoke();
                break;
            default:
                error(command);
                break;
        }
    }

    private void error(String command) {
        new ErrorCommand(TaskList.this.out).invoke(command);
    }

}
