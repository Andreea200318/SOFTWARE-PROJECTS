package org.example.business_logic;

import org.example.gui.nuj;
import org.example.model.Server;
import org.example.model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class SimulationManager implements Runnable {
    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int maxArrivalTime;
    private int minArrivalTime;
    private int numberOfServers;
    private SelectionPolicy simulationPolicy;
    private int numberOfClients;
    private double averageWaitingTime;
    private double averageServiceTime;

    private Scheduler scheduler;
    private List<Task> generatedTasks;
    private nuj gui; // Referință către instanța Nuj

    private int totalWaitingTime = 0;
    private int totalServiceTime = 0;
    private int servedClients = 0;
    private int[] clientsPerTimeUnit;
    private int peakHour = 0;

    public SimulationManager(nuj gui, SelectionPolicy simulationPolicy, int numberOfClients, int simtime,int maxProcessingTime, int minProcessingTime, int numberOfServers, int maxArrivalTime, int minArrivalTime) {
        this.gui = gui;
        this.simulationPolicy = simulationPolicy;
        this.numberOfClients = numberOfClients;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.numberOfServers = numberOfServers;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;

        this.timeLimit = simtime;

        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        this.generatedTasks = generateRandomTasks(numberOfClients);
        this.scheduler.changeStrategy(simulationPolicy);

        this.clientsPerTimeUnit = new int[timeLimit + 1];
    }

    private List<Task> generateRandomTasks(int numberOfTasks) {
        List<Task> generatedTasks = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            Random random = new Random();
            Task task = new Task();
            task.setServiceTime(random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime);
            task.setArrivalTime(random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime);
            task.setID(i + 1);
            generatedTasks.add(task);
        }
        return generatedTasks;
    }

    private int totalTasksProcessed = 0;

    @Override
    public void run() {
        int currentTime = 0;
        while (!generatedTasks.isEmpty() || !scheduler.ServersEmpty() || currentTime < timeLimit) {
            logCurrentState(currentTime);
            dispatchTasks(currentTime);
            currentTime++;

            totalWaitingTime += generatedTasks.size();
            totalServiceTime += calculateTotalTasksProcessed();

            servedClients += calculateTotalTasksProcessed();

            SwingUtilities.invokeLater(() -> {
                gui.updateGUI(getSimulationState());
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        averageWaitingTime = (double) totalWaitingTime / servedClients;
        averageServiceTime = (double) totalServiceTime / servedClients;


        int maxClients = 0;
        for (int i = 0; i < clientsPerTimeUnit.length; i++) {
            if (clientsPerTimeUnit[i] > maxClients) {
                maxClients = clientsPerTimeUnit[i];
                peakHour = i;
            }
        }


        updateStats(averageWaitingTime, averageServiceTime, peakHour);
    }

    private int calculateTotalTasksProcessed() {
        int totalTasks = generatedTasks.size();
        for (Server server : scheduler.getServers()) {
            totalTasks += server.getTask().size();
        }
        return totalTasks;
    }

    private void dispatchTasks(int currentTime) {
        Iterator<Task> iterator = generatedTasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getArrivalTime().get() == currentTime) {
                clientsPerTimeUnit[currentTime]++;
                scheduler.dispatchTak(task);
                iterator.remove();
            }
        }

        List<Server> servers = scheduler.getServers();
        for (Server server : servers) {
            BlockingQueue<Task> tasks = server.getTask();
            Iterator<Task> taskIterator = tasks.iterator();
            while (taskIterator.hasNext()) {
                Task task = taskIterator.next();
                task.decrementServiceTime();
                if (task.getServiceTime().get() == 0) {
                    taskIterator.remove();
                }
            }
        }
    }



    private StringBuilder simulationState = new StringBuilder();

    private void logCurrentState(int currentTime) {
        simulationState.append("Time ").append(currentTime).append("\n");
        simulationState.append("Waiting clients: ").append(generatedTasks).append("\n");
        List<Server> servers = scheduler.getServers();
        for (int i = 0; i < servers.size(); i++) {
            simulationState.append("Queue ").append(i + 1).append(": ").append(servers.get(i).getTask()).append("\n");
            simulationState.append("Queue ").append(i + 1).append(" status: ").append(servers.get(i).getTask().isEmpty() ? "closed" : "open").append("\n");
        }
        simulationState.append("\n");
    }



    private void updateStats(double averageWaitingTime, double averageServiceTime, int peakHour) {
        SwingUtilities.invokeLater(() -> {


            String existingText = gui.getTextArea1().getText();


            String statsText = existingText +
                    "Average Waiting Time: " + averageWaitingTime + "\n" +
                    "Average Service Time: " + averageServiceTime + "\n" +
                    "Peak Hour: " + peakHour + "\n\n";


            gui.getTextArea1().setText(statsText);
        });
    }


    public String getSimulationState() {
        return simulationState.toString();
    }
    public static void main(String[] args) {
        nuj d=new nuj();
    }
}