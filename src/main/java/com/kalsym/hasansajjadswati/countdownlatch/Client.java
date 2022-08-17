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
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author hasan
 */
public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 8081);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Send Request To Server:");
        Scanner scanner = new Scanner(System.in);
        String inputToServer = null;
        String outputFromServer = null;

        while (!"exit".equalsIgnoreCase(inputToServer)) {
            inputToServer = scanner.nextLine();
            out.println(inputToServer);

            CountDownLatch latch = new CountDownLatch(1);
            WaitingMessage message = new WaitingMessage(latch);
            Thread thread = new Thread(message);
            thread.start();
            outputFromServer = in.readLine();

            if (outputFromServer.equalsIgnoreCase("response received!")) {
                System.out.println("Response Received! Waited = " + message.getCounter() + " Seconds!");
                message.setExit(true);
                message.resetCounter();
                System.out.println("Send Request To Server:");
            }

            if (outputFromServer.equalsIgnoreCase("Goodbye!")) {
                System.out.println("Goodbye!");
                message.setExit(true);
            }
        }
        scanner.close();
    }

}
