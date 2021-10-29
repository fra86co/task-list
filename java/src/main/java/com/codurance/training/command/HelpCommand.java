package com.codurance.training.command;

import java.io.PrintWriter;

public class HelpCommand {

    private final PrintWriter writer;

    public HelpCommand(PrintWriter out) {
        writer = out;
    }

    public void invoke() {
        writer.println("Commands:");
        writer.println("  show");
        writer.println("  add project <project name>");
        writer.println("  add task <project name> <task description>");
        writer.println("  check <task ID>");
        writer.println("  uncheck <task ID>");
        writer.println();
    }
}
