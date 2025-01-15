package com.asu.EduMentor.logging;

import lombok.SneakyThrows;

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
        Command command = new LogCommand(message.toLog());
        CommandQueue.getInstance().put(command);
    }
}
