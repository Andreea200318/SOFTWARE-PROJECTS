package org.example.business_logic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void adaugaStrategie(List<Server> servers, Task task) {
        int minQueueLength = Integer.MAX_VALUE;
        Server selectedServer = null;


        for (Server server : servers) {
            if (server.getQueueLength() < minQueueLength) {
                minQueueLength = server.getQueueLength();
                selectedServer = server;
            }
        }


        if (selectedServer != null) {
            selectedServer.addTask(task);
        } else {

            throw new IllegalStateException("nu e server disp.");
        }
    }
}
