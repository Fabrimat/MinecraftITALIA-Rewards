package me.fabrimat.minecraftitaliarewards.interfaces;

import me.fabrimat.minecraftitaliarewards.interfaces.Manager;

public interface SchedulerManager extends Manager {

    void forceRun();
    void startRunner();
    void stopRunner();


}
