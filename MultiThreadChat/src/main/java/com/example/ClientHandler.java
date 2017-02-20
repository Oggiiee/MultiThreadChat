package com.example;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Oskar on 2017-01-12.
 */
public class ClientHandler extends Thread {

    private String clientName = null;
    private DataInputStream in = null;
    private PrintStream out = null;
    private Socket clientSocket = null;
    private final ClientHandler[] threads;
    private int maxClientsCount;

    public ClientHandler(Socket clientSocket, ClientHandler[] threads){
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        ClientHandler[] threads = this.threads;


        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new PrintStream(clientSocket.getOutputStream());
            String name;
            while (true) {
                out.println("Enter your name");
                name = in.readLine().trim();
                if(name.indexOf('@') == -1){
                    break;
                } else {
                    out.println();
                }

            }


            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] == this) {
                        clientName =  name;
                        break;
                    }
                }
            }

            while (true) {
                String line = in.readLine();
                if(line.startsWith(("quit"))){
                    break;
                }

                synchronized (this) {
                    for (int i = 0; i < maxClientsCount; i++) {
                        if (threads[i] != null && threads[i].clientName != null) {
                            threads[i].out.println(name + ": " + line);
                        }
                    }
                }
            }
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {

        }
    }

}
