/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kalsym.hasansajjadswati.countdownlatch;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author hasan
 */
public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = null;
        CountDownLatch startSignal = new CountDownLatch(1);
        try {
            server = new ServerSocket(8081);
            server.setReuseAddress(true);
            while (true) {
                System.out.println("Server Started!");
                Socket client = server.accept();
                System.out.println("New Client Connected!");

                new Thread(new ClientHandler(client, startSignal)).start();

            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }

    }
}
