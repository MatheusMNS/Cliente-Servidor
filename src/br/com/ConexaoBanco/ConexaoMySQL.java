/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ConexaoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author mathe
 */
public class ConexaoMySQL {

    private String _idEquipamento;
    private String _senhaEquipamento;

    public ConexaoMySQL(String idEquipamento, String senhaEquipamento) {
        _idEquipamento = idEquipamento;
        _senhaEquipamento = senhaEquipamento;
    }
    
    public boolean buscaSenha(){
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

    public boolean conexaoMySQL() {

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

    public boolean insereDados(Connection connection) {
        try {
            System.out.println("Inserindo dados no banco...\n");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("insert into alertas (id_equipamento) values ('" + _idEquipamento + "')");
            if(fechaConexao(connection)){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir os dados no banco :(");
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
}
