/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ConexaoBanco;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author mathe
 */
public class ConexaoMySQL {

    private String _idEquipamento;
    private String _senhaEquipamento;
    private BufferedImage _img;

    public ConexaoMySQL(String idEquipamento, String senhaEquipamento, BufferedImage img) {
        _idEquipamento = idEquipamento;
        _senhaEquipamento = senhaEquipamento;
        _img = img;
    }
    
    public boolean buscaSenha() throws IOException{
        Connection connection = null;
        
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);

            String serverName = "localhost";
            String mydatabase = "bd_ads";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
            String username = "root";
            String password = "";

            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                String sql = "SELECT senha FROM equipamentos WHERE senha = ?";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1, _senhaEquipamento);
                ResultSet rs = pstm.executeQuery();
                while(rs.next()){
                    System.out.println("Retorno do banco: "+rs.getString(1));
                    if (insereDados(connection)) {
                        return true;
                    } else {
                        return false;
                    }                    
                }
            } else {
                System.out.println("Conexão com o banco falhou :(");
                return false;
            }

        } catch (ClassNotFoundException e) {  //Driver não encontrado
            System.out.println("O driver expecificado nao foi encontrado.");
            return false;
        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
            return false;
        }
        
        return true;
    }

    public boolean conexaoMySQL() throws IOException {

        Connection connection = null;

        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);

            String serverName = "localhost";
            String mydatabase = "bd_relatorios";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
            String username = "root";
            String password = "";

            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                if (insereDados(connection)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                System.out.println("Conexão com o banco falhou :(");
                return false;
            }

        } catch (ClassNotFoundException e) {  //Driver não encontrado
            System.out.println("O driver expecificado nao foi encontrado.");
            return false;
        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
            return false;
        }
    }

    public boolean insereDados(Connection connection) throws IOException {
        try {
            System.out.println("Inserindo dados no banco...\n");
            System.out.println("SENHA DO EQUIP: " + _idEquipamento);
            byte[] imageInByte = bfToByte();
            String sql = "INSERT INTO alertas (id_equipamento, imagem) VALUES (?, ?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, _idEquipamento);
            pstm.setBytes(2, imageInByte);
            pstm.executeUpdate();
            //Statement stmt = connection.createStatement();
            //stmt.executeUpdate("insert into alertas (id_equipamento) values ('" + _idEquipamento + "')");
            if(fechaConexao(connection)){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir os dados no banco :(");
            e.printStackTrace();
            fechaConexao(connection);
            return false;
        }
    }
    
    public boolean fechaConexao(Connection connection){
        try {
            connection.close();
            System.out.println("Conexão com o banco fechada!\n");
            return true;
        } catch (SQLException ex) {
            System.out.println("Impossível fechar a conexão com o banco!\n");
            return false;
        }
    }
    
    public byte[] bfToByte() throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(_img, "png", baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }
}
