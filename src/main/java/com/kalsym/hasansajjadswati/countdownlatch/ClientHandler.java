/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kalsym.hasansajjadswati.countdownlatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author hasan
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final CountDownLatch startSignal;

    public ClientHandler(Socket socket, CountDownLatch startSignal) {
        this.clientSocket = socket;
        this.startSignal = startSignal;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            boolean exitStatus = false;

            while (!exitStatus) {
                try {

                    if (in.readLine().equalsIgnoreCase("exit")) {
                        startSignal.await(5, TimeUnit.SECONDS);
                        out.println("Goodbye!");
                        exitStatus = false;
                    } else {
                        startSignal.await(5, TimeUnit.SECONDS);
                        out.println("Response Received!");
                        System.out.println("Respose is sent after 5 seconds!");
                    }

                } catch (InterruptedException ex) {
                    System.err.println(ex);
                }
            }

        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
