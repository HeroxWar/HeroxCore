package com.test.utils;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.command.ConsoleCommandSenderMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import java.util.ArrayList;
import java.util.List;

public class PrintMessage {

    private ServerMock serverMock;

    public PrintMessage(ServerMock serverMock) {
        this.serverMock = serverMock;
    }

    public List<String> getAllMessages(ConsoleCommandSenderMock consoleCommandSenderMock) {
        List<String> messages = new ArrayList<>();
        String message = getMessage(consoleCommandSenderMock);
        while (message != null) {
            messages.add(message);
            message = getMessage(consoleCommandSenderMock);
        }
        return messages;
    }

    public String getMessage(ConsoleCommandSenderMock consoleCommandSenderMock) {
        String message = consoleCommandSenderMock.nextMessage();
        int count = 0;
        while (message == null) {
            serverMock.getScheduler().performTicks(20L);
            message = consoleCommandSenderMock.nextMessage();
            if(message == null) {
                count++;
                if(count == 100) {
                    break;
                }
            }
        }
        return message;
    }

    public List<String> getAllMessages(PlayerMock playerMock) {
        List<String> messages = new ArrayList<>();
        String message = getMessage(playerMock);
        while (message != null) {
            messages.add(message);
            message = getMessage(playerMock);
        }
        return messages;
    }

    public String getMessage(PlayerMock playerMock) {
        String message = playerMock.nextMessage();
        int count = 0;
        while (message == null) {
            serverMock.getScheduler().performTicks(20L);
            message = playerMock.nextMessage();
            if(message == null) {
                count++;
                if(count == 100) {
                    break;
                }
            }
        }
        return message;
    }

}
