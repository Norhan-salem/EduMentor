package com.asu.EduMentor.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogCommand implements Command {
    String message;

    public LogCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        try (FileWriter writer = new FileWriter("app.log", true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write(String.format("[%s] %s%n", timestamp, message));
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
