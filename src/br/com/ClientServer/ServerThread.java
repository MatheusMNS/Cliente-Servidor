/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ClientServer;

import br.com.ClientServer.Exceptions.Excecao;
import br.com.ConexaoBanco.ConexaoMySQL;
import br.com.View.AnimalEncontrado;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author mathe
 */
public class ServerThread extends Thread {

    private Socket _socket = null;
    private String _clienteEnd = "";

    public ServerThread(Socket socket, String clienteEnd) {
        super("ServerThread");
        this._socket = socket;
        this._clienteEnd = clienteEnd;
    }

    @Override
    public void run() {

        try {
            System.out.println("Conexão estabelecida com "+_clienteEnd+"\n");
            
            PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            String usuarioMsg, servidorMsg;
            servidorMsg = "OK";
            //out.println(servidorMsg);

            //AnimalEncontrado janela = new AnimalEncontrado();
            //janela.animalEncontrado(janela);
            
            usuarioMsg = in.readLine();
            System.out.println("\nMensagem do usuário: "+usuarioMsg);
            out.println(servidorMsg);
            ConexaoMySQL con = new ConexaoMySQL(_clienteEnd, "gato");
            
            if(con.buscaSenha(usuarioMsg)){
                out.println("OK");
            }
            else{
                out.println("ERRO");
            }
            
                        
            // Este while recebe vários dados do cliente enquanto estiverem conectados
            /* 
            while (!(usuarioMsg = in.readLine()).equals("0")) {
                out.println("Recebido: " + usuarioMsg);
            }
            */
            
            _socket.close();
            System.out.println("\nConexão fechada.");
        } catch (IOException e) {
            System.out.println("Erro ao invocar o método 'run()'");
        }
    }
}
