package com.mycompany.os_ass_3.net;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Mohamed
 */
import com.mycompany.os_ass_3.net.Process;
import java.util.*;
import java.util.function.DoubleToIntFunction;
import static java.lang.Math.ceil;

public class SchedulingAlgorithms {
    int waitingtime = 0;
    int turnaroundtime;
    int starttime = 0;
    int endtime;
    int totalwaitingtime = 0;
    int totalturnaroundtime = 0;
    private Process[] procesess;

    public SchedulingAlgorithms(Process[] procesess) {
        this.procesess = procesess;
    }
    public void PriorityScheduling(int context_switching) {
        Arrays.sort(procesess, Comparator.comparingInt(Process::getArrivalTime));

        int starttime = 0;
        int ContextSwitching_counter = 0;
        int totalwaitingtime = 0;
        int totalturnaroundtime = 0;

        PriorityQueue<Process> queue = new PriorityQueue<>((p1, p2) -> {
            if (p1.getPriority() == p2.getPriority()) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
            return Integer.compare(p1.getPriority(), p2.getPriority());
        });

        System.out.println("Priority Scheduling Execution Details:");
        System.out.println("");

        int index = 0;
        while (index < procesess.length || !queue.isEmpty()) {
            while (index < procesess.length && procesess[index].getArrivalTime() <= starttime) {
                queue.offer(procesess[index]);
                index++;
            }

            if (queue.isEmpty()) {
                starttime = procesess[index].getArrivalTime();
                continue;
            }

            Process currentProcess = queue.poll();

            int waitingtime = (starttime - currentProcess.getArrivalTime()) + context_switching;
            currentProcess.setWaitingTime(waitingtime);

            int endtime = starttime + currentProcess.getBurstTime();
            ContextSwitching_counter++;
            endtime += context_switching;
            starttime = endtime;

            int turnaroundtime = endtime - currentProcess.getArrivalTime();
            currentProcess.setTurnArroundTime(turnaroundtime);

            totalwaitingtime += waitingtime;
            totalturnaroundtime += turnaroundtime;


            System.out.println("Executing Process: " + currentProcess.getProcessName());
            System.out.println("    Waiting Time: " + currentProcess.getWaitingTime());
            System.out.println("    Turnaround Time: " + currentProcess.getTurnArroundTime());
            System.out.println("----------------------------------------");
        }


        double avg_wait_time = (double) totalwaitingtime / procesess.length;
        double avg_turn_time = (double) totalturnaroundtime / procesess.length;


        System.out.println(" Priority Scheduling:");
        System.out.println("");
        System.out.println("Total Context Switches: " + ContextSwitching_counter);
        System.out.println("Average Waiting Time: " + ceil(avg_wait_time));
        System.out.println("Average Turnaround Time: " + ceil(avg_turn_time));
        System.out.println("");
    }


    public void Shortest_job_First() {
        Arrays.sort(procesess, (p1, p2) -> Integer.compare(p1.getBurstTime(), p2.getBurstTime()));

        System.out.println("Order of process execution:");
        for (Process p : procesess) {
            if (starttime < p.getArrivalTime()) {
                starttime = p.getArrivalTime();
            }

            waitingtime = starttime - p.getArrivalTime();
            p.setWaitingTime(waitingtime);

            endtime = starttime + p.getBurstTime();
            turnaroundtime = endtime - p.getArrivalTime();
            p.setTurnArroundTime(turnaroundtime);

            starttime = endtime;
            System.out.print(p.getProcessName() + " ");
        }

        for (Process p : procesess) {
            totalwaitingtime += p.getWaitingTime();
            totalturnaroundtime += p.getTurnArroundTime();
        }

        double avg_wait_time = (double) totalwaitingtime / procesess.length;
        double avg_turn_time = (double) totalturnaroundtime / procesess.length;

        System.out.println("");
        System.out.println("Shortest Job First Scheduling Results:");
        for (Process p : procesess) {
            System.out.println("/////////////////////////////////////");
            System.out.println("process waiting time of" + " " + p.getProcessName() + " = " + p.getWaitingTime());
            System.out.println("process turn around time of" + " " + p.getProcessName() + " = " + p.getTurnArroundTime());

        }
        System.out.println("/////////////////////////////////////////");
        System.out.println("Average Waiting Time: " + ceil(avg_wait_time));
        System.out.println("Average Turnaround Time: " + ceil(avg_turn_time));
    }

    ////////////////////////////////////////////////////////////////////////////////////
    public void Shortest_Remaining_Time(Process[] processes, int n, int contextSwitchTime) {

        Arrays.sort(processes, (p1, p2) -> Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()));
        Arrays.sort(processes, (p1, p2) -> Integer.compare(p1.getBurstTime(), p2.getBurstTime()));

        int[] originalBurstTimes = new int[n];
        for (int i = 0; i < n; i++) {
            originalBurstTimes[i] = processes[i].getBurstTime();
        }

        int currentTime = 0;
        int completedProcesses = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        Process lastProcess = null;

        while (completedProcesses < n) {
            int shortestRemainingTime = Integer.MAX_VALUE;
            Process nextProcess = null;


            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime
                        && process.getBurstTime() > 0
                        && process.getBurstTime() < shortestRemainingTime) {
                    shortestRemainingTime = process.getBurstTime();
                    nextProcess = process;
                }
            }

            if (nextProcess != null) {

                if (lastProcess != nextProcess && lastProcess != null) {
                    currentTime += contextSwitchTime;
                }


                nextProcess.setBurstTime(nextProcess.getBurstTime() - 1);
                currentTime++;

                lastProcess = nextProcess;


                if (nextProcess.getBurstTime() == 0) {
                    completedProcesses++;
                    int turnaroundTime = currentTime - nextProcess.getArrivalTime() + contextSwitchTime;
                    nextProcess.setTurnArroundTime(turnaroundTime);


                    int index = Arrays.asList(processes).indexOf(nextProcess);
                    nextProcess.setWaitingTime(turnaroundTime - originalBurstTimes[index]);
                }
            } else {

                currentTime++;
            }
        }


        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnArroundTime();
        }

        double avgWaitTime = (double) totalWaitingTime / n;
        double avgTurnTime = (double) totalTurnaroundTime / n;

        System.out.println("Shortest Remaining Time First Scheduling Results:");
        for (Process process : processes) {
            System.out.println("/////////////////////////////////////");
            System.out.println("Process waiting time of " + process.getProcessName() + " = " + process.getWaitingTime());
            System.out.println("Process turn around time of " + process.getProcessName() + " = " + process.getTurnArroundTime());
        }

        System.out.println("/////////////////////////////////////");
        System.out.println("Average Waiting Time: " + ceil(avgWaitTime));
        System.out.println("Average Turnaround Time: " + ceil(avgTurnTime));
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void FCAIScheduling(Process[] processes) {

        double v1 = calculateV1(processes);
        double v2 = calculateV2(processes);

        Queue<Process> readyQueue = new LinkedList<>();
        int currentTime = 0;

        while (!allProcessesCompleted(processes)) {

            for (Process p : processes) {
                if (p.getArrivalTime() <= currentTime && !p.isCompleted && !readyQueue.contains(p)) {
                    readyQueue.add(p);
                }
            }


            readyQueue.removeIf(p -> p.isCompleted);

            // Recalculate FCAI factors
            for (Process p : readyQueue) {
                p.calculateFCAIFactor(v1, v2);
            }


            Process currentProcess = selectProcessWithLowestFCAI(readyQueue);

            if (currentProcess != null) {
                int startTime = currentTime;
                System.out.println("Executing process: " + currentProcess.getProcessName() + " (Start time: " + startTime + ")");

                int executionTime = Math.min(currentProcess.quantum, currentProcess.getRemainingBurstTime());
                int fortyPercentTime = (int) Math.ceil(currentProcess.quantum * 0.4);

                if (executionTime > fortyPercentTime) {
                    currentTime += fortyPercentTime;
                    currentProcess.setRemainingBurstTime(currentProcess.getRemainingBurstTime() - fortyPercentTime);
                } else {
                    currentTime += executionTime;
                    currentProcess.setRemainingBurstTime(0);
                }

                if (currentProcess.getRemainingBurstTime() > 0) {
                    currentProcess.quantum += 2;
                } else {
                    currentProcess.isCompleted = true;
                    currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    System.out.println("Process " + currentProcess.getProcessName() + " completed. (Finish time: " + currentTime + ")");
                }
            } else {
                currentTime++;
            }
        }

        printStatistics(processes);
    }


    static double calculateV1(Process[] processes) {
        int lastArrivalTime = Arrays.stream(processes).mapToInt(p -> p.arrivalTime).max().orElse(0);
        return lastArrivalTime / 10.0;
    }

    static double calculateV2(Process[] processes) {
        int maxBurstTime = Arrays.stream(processes).mapToInt(p -> p.burstTime).max().orElse(0);
        return maxBurstTime / 10.0;
    }

    static boolean allProcessesCompleted(Process[] processes) {
        return Arrays.stream(processes).allMatch(p -> p.isCompleted);
    }

    static Process selectProcessWithLowestFCAI(Queue<Process> readyQueue) {
        return readyQueue.stream().min(Comparator.comparingDouble(p -> p.fcaiFactor)).orElse(null);
    }

    static void printStatistics(Process[] processes) {
        System.out.println("\nProcess Execution Statistics:");
        double totalWaitingTime = 0, totalTurnaroundTime = 0;

        for (Process p : processes) {
            System.out.println("Process " + p.getProcessName() + ":");
            System.out.println("Waiting Time: " + p.waitingTime);
            System.out.println("Turnaround Time: " + p.turnaroundTime);
            totalWaitingTime += p.waitingTime;
            totalTurnaroundTime += p.turnaroundTime;
        }

        System.out.println("Average Waiting Time: " + ceil((totalWaitingTime / processes.length)));
        System.out.println("Average Turnaround Time: " + ceil((totalTurnaroundTime / processes.length)));
    }
}