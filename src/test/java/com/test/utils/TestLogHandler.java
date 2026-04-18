package com.test.utils;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

// Test logger handler to capture console messages
public class TestLogHandler extends Handler {
    private final List<String> messages;

    public TestLogHandler(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public void publish(LogRecord record) {
        messages.add(record.getMessage());
    }

    @Override
    public void flush() {
        // Not needed for test
    }

    @Override
    public void close() throws SecurityException {
        // Not needed for test
    }
}
