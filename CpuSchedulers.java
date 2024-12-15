package com.mycompany.os_ass_3.net;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import java.util.Arrays;
/**
 *
 * @author Mohamed
 */
public class CpuSchedulers {

    public static void main(String[] args)
    {
        Process[] processes = new Process[] {
                new Process("p1", 2, 6, 4,4),
                new Process("p2", 5, 2, 9,3),
                new Process("p3", 1, 8, 3,5),
                new Process("p4",20,3,8,2),
                new Process("p5",4,4,7,4),
        };

        SchedulingAlgorithms scheduler = new SchedulingAlgorithms(processes);

        scheduler.Shortest_Remaining_Time(processes,5,0);

    }
}