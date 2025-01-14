package com.asu.EduMentor.logging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandQueue {
    private static final BlockingQueue<Command> instance = new LinkedBlockingQueue<>();

    private CommandQueue() {}

    public static BlockingQueue<Command> getInstance() {
        return instance;
    }
}

