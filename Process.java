package com.mycompany.os_ass_3.net;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import static java.lang.Math.ceil;

/**
 *
 * @author Mohamed
 */
public class Process
{  String ProcessName;
    int arrivalTime, burstTime, priority, remainingBurstTime;
    public int quantum=0;
    double fcaiFactor;
   public  boolean isCompleted = false;
    int waitingTime = 0, turnaroundTime = 0;

    public Process(String name, int arrivalTime, int burstTime, int priority, int quantum) {
        this.ProcessName = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.priority = priority;
        this.quantum = quantum;
    }

    public Process() {
    }



    public String getProcessName()
    {
        return ProcessName;
    }

    public void setProcessName(String ProcessName)
    {
        this.ProcessName = ProcessName;
    }

    public int getBurstTime()
    {
        return burstTime;
    }

    public void setBurstTime(int BurstTime)
    {
        this.burstTime = BurstTime;
    }

    public int getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(int ArrivalTime)
    {
        this.arrivalTime = ArrivalTime;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int Priority)
    {
        this.priority = Priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int WaitingTime) {
        this.waitingTime = WaitingTime;
    }

    public int getTurnArroundTime() {
        return turnaroundTime;
    }

    public void setTurnArroundTime(int TurnArroundTime)
    {
        this.turnaroundTime = TurnArroundTime;
    }

    void calculateFCAIFactor(double v1, double v2) {
        this.fcaiFactor = ceil((10 - this.priority) + (this.arrivalTime / v1) + (this.remainingBurstTime / v2));
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }
}
