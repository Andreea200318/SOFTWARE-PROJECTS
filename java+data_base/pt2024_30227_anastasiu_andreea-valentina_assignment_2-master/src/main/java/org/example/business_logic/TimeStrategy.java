package org.example.business_logic;

import org.example.business_logic.Strategy;
import org.example.model.Server;
import org.example.model.Task;

import java.util.List;

public class TimeStrategy implements Strategy {



    @Override
    public void adaugaStrategie(List<Server> a, Task b) {
        int minim = Integer.MAX_VALUE;
        int poz = -1;
        for (int i = 0; i < a.size(); i++) {
            Server s = a.get(i);
            if (s.getWaitingPeriod().get() < minim) {
                poz = i;
                minim = s.getWaitingPeriod().get();
            }
        }
        if (poz != -1) {
            a.get(poz).addTask(b);
        } else {
            System.out.println("nu exista server valid pt task.");
        }

    }
}
