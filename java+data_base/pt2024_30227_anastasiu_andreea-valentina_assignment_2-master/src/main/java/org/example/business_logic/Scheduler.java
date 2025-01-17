package org.example.business_logic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Scheduler {
    List<Server> servers = new ArrayList<>();
    private int maxNoServers;
    private int maxTasksPerServer;
    Strategy strategy;
    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        for (int i = 0; i < maxNoServers; i++) {
            Server s = new Server(maxTasksPerServer);
            Thread t = new Thread(s);
            this.servers.add(s);
            t.start();
        }
    }


    public boolean ServersEmpty() {
        for (Server server : servers) {
            if (!server.getTask().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void changeStrategy(SelectionPolicy policy)
    {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        } else if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new TimeStrategy();
        } else {
            throw new IllegalArgumentException("Unknown selection policy: " + policy);
        }

    }
    public List<Task> getQueue(int queueNumber) {

        if (queueNumber < 0 || queueNumber >= servers.size()) {
            throw new IndexOutOfBoundsException("Queue number " + queueNumber + " exceeds number of servers.");
        }
        return new ArrayList<>(servers.get(queueNumber).getTask());
    }


    public void dispatchTak(Task t)
    {
        if (strategy == null) {
            throw new IllegalStateException("Strategy has not been initialized");
        }
        strategy.adaugaStrategie(servers, t);
    }


    public List<Server> getServers(){
        return servers;
    }
}
