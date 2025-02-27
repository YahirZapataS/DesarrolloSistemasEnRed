package Actividad__04;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CarreraProgressBar extends JFrame {
    private JProgressBar bar1, bar2, bar3;
    private JButton startButton;
    private final int MAX_VALUE = 1000;

    public CarreraProgressBar() {
        setTitle("Carrera de ProgressBars");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        // Inicializar las barras de progreso
        bar1 = new JProgressBar(0, MAX_VALUE);
        bar2 = new JProgressBar(0, MAX_VALUE);
        bar3 = new JProgressBar(0, MAX_VALUE);

        bar1.setStringPainted(true);
        bar2.setStringPainted(true);
        bar3.setStringPainted(true);

        startButton = new JButton("Iniciar Carrera");

        add(bar1);
        add(bar2);
        add(bar3);
        add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarCarrera();
            }
        });
    }

    private void iniciarCarrera() {
        startButton.setEnabled(false);
        bar1.setValue(0);
        bar2.setValue(0);
        bar3.setValue(0);

        List<String> podio = Collections.synchronizedList(new ArrayList<>());

        new Thread(() -> correrBar(bar1, "Barra 1", podio)).start();
        new Thread(() -> correrBar(bar2, "Barra 2", podio)).start();
        new Thread(() -> correrBar(bar3, "Barra 3", podio)).start();
    }

    private void correrBar(JProgressBar bar, String nombre, List<String> podio) {
        int progress = 0;
        Random random = new Random();
        while (progress < MAX_VALUE) { 
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int incremento = random.nextInt(16) + 10;
            progress += incremento;
            if (progress > MAX_VALUE) {
                progress = MAX_VALUE;
            }

            final int currentProgress = progress;
            SwingUtilities.invokeLater(() -> bar.setValue(currentProgress));
        }

        podio.add(nombre);
        synchronized (podio) {
            if (podio.size() == 3) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(CarreraProgressBar.this, "Resultados de la carrera:\n 1° " + podio.get(0) + "\n 2° " + podio.get(1) + "\n 3° " + podio.get(2));
                    startButton.setEnabled(true);
                });
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarreraProgressBar().setVisible(true));
    }
}