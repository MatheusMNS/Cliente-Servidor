/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author mathe
 */
public class AnimalEncontrado extends JFrame {

    public void animalEncontrado(JFrame janela) {
        janela.setSize(150, 150);
        janela.setLocationRelativeTo(null);
        janela.add(new JLabel("ANIMAL NA PISTA"), BorderLayout.CENTER);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                janela.setVisible(true);
            }
        });
    }
}