/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kalsym.hasansajjadswati.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author hasan
 */
public class WaitingMessage implements Runnable {

    private static int counter = 0;
    private final CountDownLatch latch;
    private boolean exitStatus = false;

    public WaitingMessage(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {

        while (!exitStatus) {
            try {

                System.out.println("I am waiting for response from server!");
                counter++;
                latch.await(1, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                ex.getStackTrace();
            }
        }
    }

    public int getCounter() {
        return counter;
    }

    public void resetCounter() {
        counter = 0;
    }

    public void setExit(Boolean exitStatus) {
        this.exitStatus = exitStatus;
    }
}
