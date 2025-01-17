package org.example.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    public BlockingQueue<Task> tasks;

    public AtomicInteger waitingPeriod;

    public Server(int max_task) {
        tasks = new ArrayBlockingQueue<Task>(max_task);
        waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask)
    {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime().get());

    }


    @Override
    public void run() {
        while (true) {
            if (!tasks.isEmpty()) {
                Task t = tasks.peek();
                if (t != null) {
                    try {
                        Thread.sleep(1000L * t.getServiceTime().get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waitingPeriod.addAndGet(-t.getServiceTime().get());

                }
            }
        }
    }

    public boolean isFull() {
        return tasks.size() == tasks.remainingCapacity();
    }

    public BlockingQueue<Task> getTask(){
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
    public int getQueueLength() {
        return tasks.size();
    }

}
