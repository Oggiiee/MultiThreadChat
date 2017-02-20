package com.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Oskar on 2017-01-12.
 */

public class Client implements Runnable {

    private static Socket clientSocket;
    private static PrintStream out;
    private static DataInputStream in;
    private static BufferedReader inputLine;
    private static boolean closed = false;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Server Ip:");
        String ServerIp = sc.nextLine();
        System.out.println("Server Port:");
        int ServerPort = sc.nextInt();

        try{
            clientSocket = new Socket(ServerIp,ServerPort);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(clientSocket != null && out != null & in != null){
            try{
                new Thread(new Client()).start();
                while(!closed){
                    out.println(inputLine.readLine());
                }
                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    public void run(){

        String received;
        try{
            while ((received = in.readLine()) != null){
                System.out.println(received);
            }
            closed = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
