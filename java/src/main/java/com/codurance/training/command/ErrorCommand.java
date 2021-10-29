package com.codurance.training.command;

import java.io.PrintWriter;

public class ErrorCommand {
    private final PrintWriter out;

    public ErrorCommand(PrintWriter out) {
        this.out = out;
    }

    public void invoke(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }
}
