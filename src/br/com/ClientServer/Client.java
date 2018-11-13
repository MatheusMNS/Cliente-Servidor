/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ClientServer;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author mathe
 */
public class Client {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        /*
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
         

        
        String servidorNome = "localhost";
        int porta = 4444;
        */
        /*
        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }
        
        
        String servidorNome = args[0];
        int porta = Integer.parseInt(args[1]);
        */
        String servidorNome = "localhost";
        int porta = 4444;
        
        long start;
        long elapsed;
        byte[] msgCifrada;
        
        //for (int i = 0; i < 100; i++) {
            
        //    start = System.currentTimeMillis();
            try {
                Socket socket = new Socket(servidorNome, porta);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataInputStream din=new DataInputStream(socket.getInputStream());
                DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
                
                
                BufferedReader brEntrada = new BufferedReader(new InputStreamReader(System.in));
                String servidorMsg = "Conexão Estabelecida";
                String usuarioMsg = "10,123";
                //msgCifrada = hash(usuarioMsg);

                System.out.println("\nEnviando Mensagem: "+usuarioMsg.toString());
                //usuarioMsg = brEntrada.readLine();
                out.println(usuarioMsg);
                //usuarioMsg = "" + i;
                //if (usuarioMsg != null) {
                //    out.println(usuarioMsg);
                //}
                System.out.println("Enviando imagem...");
                out.flush();
                BufferedImage bufferedImage = ImageIO.read(new File("print.png"));
                ImageIO.write(bufferedImage, "png", dout);
                
                servidorMsg = in.readLine();
                System.out.println("Servidor: "+servidorMsg);
                //elapsed = System.currentTimeMillis() - start;

                /*
                try {
                    File arquivo = new File("tempo.txt"); // se já existir, será sobreescrito
                    FileWriter fw = new FileWriter(arquivo, true);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write("\n" + elapsed + " milissegundos");

                    bw.flush();
                    bw.close();
                    System.out.println("\nEscrita no arquivo: OK\n");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                */

                // O do while abaixo mantém uma conexão ativa com o servidor
                /*
                do {
                    System.out.println("Servidor: " + servidorMsg);

                    System.out.print("--> ");
                    usuarioMsg = brEntrada.readLine();
                    if (usuarioMsg != null) {
                        out.println(usuarioMsg);
                    }

                } while ((servidorMsg = in.readLine()) != null);
                 */
            } catch (IOException e) {
                System.out.println("Impossível conectar com o servidor!");
                e.printStackTrace();
                System.exit(1);
            }
    
        
        
    }
    
    public static byte[] hash(String password) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
        byte[] passBytes = password.getBytes();
        byte[] passHash = sha256.digest(passBytes);
        return passHash;
    }
}
    
