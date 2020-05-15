package com.meu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Classe: FBConexao Programador: Gilcimar Leme Afonso <hidrestudo@gmail.com>
 * Graduando em TADS pela FACOM/UFMS 2017... Arquivo criado em 17/05/2018 às
 * 22:22:36
 */
public final class FBConexao {

    private static String localUrl;
    private static String remoteUrl;
    private static String localHost;
    private static String porta;
    private static String caminhoBasedados;
    private static final String CHARSET = "?lc_ctype=WIN1252";
    private static final String DRIVER = "jdbc:firebirdsql";

    public static Connection conectar(Banco siaBanco) {
        localHost = siaBanco.getIpHost();
        porta = siaBanco.getPorta();
        caminhoBasedados = siaBanco.getPathDatabase();
        localUrl = DRIVER + "://" + localHost + ":" + porta + "/" + caminhoBasedados + CHARSET;

        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            return DriverManager.getConnection(localUrl, siaBanco.getUser(), siaBanco.getSenha());
        }
        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "ERRO! VERIFIQUE PORTA, LOCAL E NOME DO ARQUIVO BANCO!\n" + e + e.getMessage());
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO! VERIFIQUE PORTA, LOCAL E NOME DO ARQUIVO BANCO!\n" + ex + ex.getMessage());
        }

        return null;
    }

    public static void desconectar(Connection con, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null) {rs.close();}
            if (stm != null) {stm.close();}
            if (con != null) {con.close();}
        }
        catch (IllegalStateException iEx) {
            JOptionPane.showMessageDialog(null, "ERRO! \n" + iEx + iEx.getMessage());
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO AO ENCERRAR! \n" + ex + ex.getMessage());
        }

    }
}
