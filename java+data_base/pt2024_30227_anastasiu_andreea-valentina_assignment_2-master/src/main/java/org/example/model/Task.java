package org.example.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Comparable {
    public int ID;
    public AtomicInteger arrivalTime=new AtomicInteger();
    public AtomicInteger serviceTime=new AtomicInteger();



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public AtomicInteger getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime.set(arrivalTime);
    }

    public AtomicInteger getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime.set(serviceTime);
    }

   

    @Override
    public int compareTo(Object o) {
        Task t=(Task) o;
        if(arrivalTime.get()<t.getArrivalTime().get())
            return -1;
        else
            return 1;
    }


    public void decrementServiceTime() {
        serviceTime.decrementAndGet();
    }
}
