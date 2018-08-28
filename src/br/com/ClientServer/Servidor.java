/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ClientServer;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author mathe
 */
public class Servidor {

    public static void main(String[] args) throws IOException {
        /*
        if (args.length != 1) {
            System.err.println("Usage: java KKMultiServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;
         */
        if (args.length != 1) {
            System.err.println("Usage: java KKMultiServer <port number>");
            System.exit(1);
        }
        
        int porta = Integer.parseInt(args[0]);
        
        System.out.println("Iniciando servidor...\n");
        
        try {
            ServerSocket serverSocket = new ServerSocket(porta);
            System.out.println("Esperando conexões...");
            while (true) {
                new ServerThread(serverSocket.accept(), serverSocket.getInetAddress().getHostAddress()).start();
            }
        } catch (IOException e) {
            System.out.println("Impossível realizar a conexão!");
        }
    }
}
