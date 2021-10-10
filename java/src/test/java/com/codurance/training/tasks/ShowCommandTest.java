package com.codurance.training.tasks;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.lineSeparator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ShowCommandTest {

    private final PipedInputStream outStream = new PipedInputStream();
    private final BufferedReader outReader = new BufferedReader(new InputStreamReader(outStream));

    @Test
    public void show_task_list() throws IOException {
        Map<String, List<Task>> tasks = new LinkedHashMap<>();
        tasks.put("project 1", Arrays.asList(
                new Task(1, "task 1", false),
                new Task(2, "task 2", false)
        ));
        tasks.put("project 2", Arrays.asList(
                new Task(3, "task 3", false),
                new Task(4, "task 4", false)
        ));
        ShowCommand command = new ShowCommand(tasks, new PrintWriter(new PipedOutputStream(outStream), true));

        command.execute();

        readLines(
                "project 1",
                "    [ ] 1: task 1",
                "    [ ] 2: task 2",
                ""
        );

        readLines(
                "project 2",
                "    [ ] 3: task 3",
                "    [ ] 4: task 4",
                ""
        );
    }

    private void readLines(String... expectedOutput) throws IOException {
        for (String line : expectedOutput) {
            read(line + lineSeparator());
        }
    }

    private void read(String expectedOutput) throws IOException {
        int length = expectedOutput.length();
        char[] buffer = new char[length];
        outReader.read(buffer, 0, length);
        assertThat(String.valueOf(buffer), is(expectedOutput));
    }


}