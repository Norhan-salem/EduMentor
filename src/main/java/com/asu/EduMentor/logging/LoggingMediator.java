package com.asu.EduMentor.logging;

import lombok.SneakyThrows;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingMediator {
    private static LoggingMediator instance;

    private LoggingMediator() {
    }

    public static synchronized LoggingMediator getInstance() {
        if (instance == null) {
            instance = new LoggingMediator();
        }
        return instance;
    }

    @SneakyThrows
    public void log(Loggable message) {
        Command command = new Command() {
            @Override
            public void execute() {
                try (FileWriter writer = new FileWriter("app.log", true)) {
                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    writer.write(String.format("[%s] %s%n", timestamp, message.toLog()));
                } catch (IOException e) {
                    System.err.println("Error writing to log file: " + e.getMessage());
                }
            }
        };
        CommandQueue.getInstance().put(command);
    }
}
