package Actividad_03;

import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Contadores extends JFrame {
    private JLabel labelContador1, labelContador2;
    private JButton botonContador1, botonContador2;
    private Contador contador1, contador2;

    public Contadores() {
        setTitle("Contadores de Hilos");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        labelContador1 = new JLabel("0", SwingConstants.CENTER);
        labelContador2 = new JLabel("0", SwingConstants.CENTER);

        botonContador1 = new JButton("Iniciar");
        botonContador2 = new JButton("Iniciar");

        contador1 = new Contador(labelContador1, botonContador1);
        contador2 = new Contador(labelContador2, botonContador2);

        botonContador1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contador1.toggle();
            }
        });

        botonContador2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contador2.toggle();
            }
        });

        add(labelContador1);
        add(botonContador1);
        add(labelContador2);
        add(botonContador2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Contadores().setVisible(true);
        });
    }
}

class Contador implements Runnable {
    private JLabel label;
    private JButton button;
    private int contador = 0;
    private volatile boolean corriendo = false;
    private Thread thread;

    public Contador(JLabel label, JButton button) {
        this.label = label;
        this.button = button;
    }

    public void toggle() {
        if (corriendo) {
            corriendo = false;
            button.setText("Continuar");
        } else {
            if (thread == null || thread.isAlive()) {
                thread = new Thread(this);
                thread.start();
            }

            corriendo = true;
            button.setText("Detener");
        }
    }

    public void run() {
        while (true) {
            if (corriendo) {
                contador++;
                SwingUtilities.invokeLater(() -> {
                    label.setText(String.valueOf(contador));
                });
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}