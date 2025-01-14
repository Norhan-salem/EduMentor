package com.asu.EduMentor.logging;

import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;

public class LoggingThread implements Runnable {
    @SneakyThrows
    @Override
    public void run() {
        BlockingQueue<Command> queue = CommandQueue.getInstance();
        while(true) {
            Command command = queue.take();
            command.execute();
        }
    }
}
