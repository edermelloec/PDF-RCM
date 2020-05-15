package com.meu.seguranca;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Éder Mello
 */
public class ControlaAcessoGUI {

    private static int diaAtual, mesAtual, anoAtual, autSenha;
    private static JLabel tempoLabel = new JLabel();
    private static final int DIA = 18;
    private static final int MES = 05;
    private static final int ANO = 2020;

    public static boolean autenticador() {
        boolean logado = false;
        String pass = "";
        String usuario = "";

        taskTerminar();

        JPasswordField password = new JPasswordField(10);
        password.setEchoChar('*');

        JPanel entUsuario = new JPanel();
        JPanel panelSenha = new JPanel();
        JPanel panelUser = new JPanel();
        JPanel panelBotaoTempo = new JPanel();

        JLabel rotulo = new JLabel("DIGITE A SENHA: ");
        JLabel rotuloUser = new JLabel("DIGITE O NOME DO USUÁRIO: ");
        JLabel lembrete = new JLabel("Aguardando há: ");

        JTextField user = new JTextField("Master");

        panelUser.setLayout(new GridLayout(2, 1, 0, 5));
        panelUser.add(rotuloUser);
        panelUser.add(user);

        panelSenha.setLayout(new GridLayout(2, 1, 0, 5));
        panelSenha.add(rotulo);
        panelSenha.add(password);

        panelBotaoTempo.setLayout(new GridLayout(2, 1, 0, 5));
        panelBotaoTempo.add(lembrete);
        panelBotaoTempo.add(tempoLabel);

        entUsuario.setLayout(new GridLayout(3, 1, 0, 20));
        entUsuario.add(panelUser);
        entUsuario.add(panelSenha);
        entUsuario.add(panelBotaoTempo);

        JOptionPane.showMessageDialog(null, entUsuario,
                "ACESSO RESTRITO", JOptionPane.QUESTION_MESSAGE);

        usuario = user.getText();
        pass = password.getText();

        pegaDataDeHoje();

        if (usuario.equalsIgnoreCase("master") && pass.equalsIgnoreCase(Integer.toString(autSenha))) {
            logado = true;
        }

        return logado;
    }

    private static void pegaDataDeHoje() {
        diaAtual = (Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        mesAtual = (Calendar.getInstance().get(Calendar.MONTH) + 1);
        anoAtual = (Calendar.getInstance().get(Calendar.YEAR));

        autSenha = anoAtual - mesAtual - diaAtual;
        // diaAtual hard-coded afim de comparar o diaAtual da feitura do arquivo
        // evita funcionamento do  mesmo sem esta validação !!!! 
    }

    private static void taskTerminar() {

        Timer timer = new Timer(1000, new ActionListener() {
            int value = 0;
            int min = 0, hor = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                value++;

                if (value < 60) {
                    tempoLabel.setText(Integer.toString(min)
                            + " minutos e " + Integer.toString(value)
                            + " Segundos");
                } else {
                    min++;
                    value = 0;
                    tempoLabel.setText(Integer.toString(min)
                            + " minutos e "
                            + Integer.toString(value) + " Segundos");
                }

                //System.out.println(value);
            }
        });

        timer.start();
    }

    public static boolean isValidaTempoLicenca() {
        diaAtual = (Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        mesAtual = (Calendar.getInstance().get(Calendar.MONTH) + 1);
        anoAtual = (Calendar.getInstance().get(Calendar.YEAR));

        if (ANO >= anoAtual) {
            if (mesAtual <= MES) {
                if (diaAtual <= DIA) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

}
