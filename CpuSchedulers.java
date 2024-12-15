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
                new Process("0", 0, 17, 4,4),
                new Process("1", 3, 6, 9,3),
                new Process("2", 4, 10, 3,5),
                new Process("3", 29, 4, 8,2)
        };

        SchedulingAlgorithms scheduler = new SchedulingAlgorithms(processes);

        scheduler.FCAIScheduling(processes);

    }
}