package org.example.business_logic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.List;

public interface Strategy {
    void adaugaStrategie(List<Server> a, Task b);
}
