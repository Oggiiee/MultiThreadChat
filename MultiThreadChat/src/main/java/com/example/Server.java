package com.example;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Oskar on 2017-01-12.
 */
public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Scanner sc;

    private static final int maxClients = 10;
    private static final ClientHandler[] threads = new ClientHandler[maxClients];

    public static void main(String[] args){
        System.out.println("Port for server to run on: ");
        sc = new Scanner(System.in);
        int portNbr = sc.nextInt();

        try{
            serverSocket = new ServerSocket(portNbr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try{
                clientSocket = serverSocket.accept();
                int i = 0;
                for(i = 0; i < maxClients; i++){
                    if (threads[i] == null){
                        (threads[i] = new ClientHandler(clientSocket, threads)).start();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

