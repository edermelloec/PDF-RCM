/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meu.model;

import com.meu.dao.Banco;
import com.meu.dao.FBConexao;
import com.meu.dao.QueryFactory;
import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

/**
 *
 * @author Éder Mello
 */
public class PdfModel {

    private static LerPDF lerPdf;
    private static Connection con;
    private static PreparedStatement pstm;
    private static ResultSet rs;

    private static File[] files;

    public static void inicializePdfModel() {
        lerPdf = new LerPDF();
    }

    public boolean verificaConexao(Banco banco) {
        try {
            con = FBConexao.conectar(banco);
            pstm = con.prepareStatement(QueryFactory.maxCodDoc());

            rs = pstm.executeQuery();
            rs.next();
            return true;
        } catch (SQLException ex) {
            
            return false;
        }
        
    }

    public ArrayList<Item> boxCodigo_cax_doc(Banco banco) {
        ArrayList<Item> itens = null;
        try {
            con = FBConexao.conectar(banco);
            pstm = con.prepareStatement(QueryFactory.boxCodigo_cax_doc());

            rs = pstm.executeQuery();

            itens = new ArrayList<>();

            while (rs.next()) {
                Item item = new Item();
                item.setCodigo(rs.getString("CODIGO_CAX"));
                item.setDescricao(rs.getString("NOME_CAX"));

                itens.add(item);
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PdfModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itens;
    }

    public ArrayList<Item> boxCodigo_tipo_doc(Banco banco) {
        ArrayList<Item> itens = null;
        try {
            con = FBConexao.conectar(banco);
            pstm = con.prepareStatement(QueryFactory.boxCodigo_tipo_doc());

            rs = pstm.executeQuery();

            itens = new ArrayList<>();

            while (rs.next()) {
                Item item = new Item();
                item.setCodigo(rs.getString("CODIGO_TPO"));
                item.setDescricao(rs.getString("NOME_TPO"));

                itens.add(item);
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PdfModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itens;
    }

    public ArrayList<Item> boxCodigo_cls_doc(Banco banco) {
        ArrayList<Item> itens = null;
        try {
            con = FBConexao.conectar(banco);
            pstm = con.prepareStatement(QueryFactory.boxCodigo_cls_doc());

            rs = pstm.executeQuery();

            itens = new ArrayList<>();

            while (rs.next()) {
                Item item = new Item();
                item.setCodigo(rs.getString("CODIGO_CLS"));
                item.setDescricao(rs.getString("ASSUNTO_CLS"));

                itens.add(item);
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PdfModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itens;
    }

    public void buscaUltimo(Banco banco) {

        try {
            con = FBConexao.conectar(banco);
            pstm = con.prepareStatement(QueryFactory.pdfSelectText());

            rs = pstm.executeQuery();
            rs.next();

            String ultimoTexto = rs.getString("TEXTO_OCR_IND");
            JOptionPane.showMessageDialog(null, ultimoTexto.substring(0, 300) + "...");

            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PdfModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvaPdfPastaSelecionada(Banco banco, Parametros doc) {

        files = lerPdf.arquivoPastaSelecionado();

        if (files != null) {
            insereTexto(banco, files, doc);
        }
    }

    public void salvaPdfSelecionados(Banco banco, Parametros param) {

        files = lerPdf.arquivoSelecionado();

        if (files != null) {
            insereTexto(banco, files, param);
        }
    }

    public void insereTexto(Banco banco, File[] files, Parametros param) {

        new Thread() {
            public void run() {
                try {
                    int porcentagem = 100 / files.length;

                    con = FBConexao.conectar(banco);
                    PDFTextStripperByArea stripper;
                    String texto;
                    String nomeArquivo;
                    File arquivo;
                    stripper = new PDFTextStripperByArea();
                    boolean copiado = false;

                    PDFTextStripper tStripper = new PDFTextStripper();

                    JProgressBar progresso = new JProgressBar();
                    progresso.setStringPainted(true);

                    JFrame jf = new JFrame();
                    jf.setTitle("Processando..");
                    jf.setSize(300, 100);
                    jf.add(progresso);
                    jf.setLocationRelativeTo(null);
                    jf.setVisible(true);

                    for (int i = 0; i < files.length; i++) {

                        progresso.setValue(porcentagem * i);
                        try (PDDocument document = PDDocument.load(files[i])) {

                            if (!document.isEncrypted()) {

                                stripper.setSortByPosition(true);

                                texto = tStripper.getText(document);

                                texto = deAccent(texto);
                                texto = texto.replaceAll("[^\\p{ASCII}]", "");

                                nomeArquivo = String.valueOf(System.currentTimeMillis()) + ".PDF";

                                arquivo = files[i];
                                File destino = new File(param.getCaminhoArquivo() + "\\" + nomeArquivo);

                                copiado = copia(arquivo, destino);
                                if (copiado) {

                                    pstm = con.prepareStatement(QueryFactory.maxCodDoc());

                                    rs = pstm.executeQuery();
                                    rs.next();

                                    int ultimoCod = rs.getInt("MAX");

                                    //gera  DIG_DOC
                                    pstm = con.prepareStatement(QueryFactory.pdfDigDoc());
                                    pstm.setInt(1, ultimoCod + 1);
                                    pstm.setString(2, param.getExercicio());
                                    pstm.setInt(3, param.getCodigo_cls_doc());
                                    pstm.setInt(4, param.getCodigo_tipo_doc());
                                    pstm.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                                    pstm.setString(6, texto.substring(0, 50));
                                    pstm.setInt(7, param.getCodigo_cax_doc());
                                    pstm.setInt(8, 1);
                                    pstm.setString(9, "S");
                                    pstm.execute();

                                    //insere informações do 
                                    pstm = con.prepareStatement(QueryFactory.pdf());
                                    pstm.setInt(1, param.getCodigo_tipo_doc());
                                    pstm.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                                    pstm.setString(3, String.valueOf(document.getNumberOfPages()));// total paginas
                                    pstm.setString(4, formataDouble(files[i].length()));//tamanho arquivo
                                    pstm.setString(5, "PDF");
                                    pstm.setString(6, nomeArquivo);
                                    pstm.setString(7, texto);
                                    pstm.setString(8, "FIORILLI");
//                                pstm.setString(9, "2"); cod_usr_ind

                                    pstm.execute();

                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(PdfModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(PdfModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (copiado) {
                        JOptionPane.showMessageDialog(null, "Informações Inseridas com sucesso!!! Nº de PDF(s): " + files.length);
                    }
                    con.close();
                    jf.dispose();

                } catch (IOException ex) {
                    Logger.getLogger(PdfModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(PdfModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

    }

    public static String formataDouble(long tamanho) {
        double bytes = tamanho;
        bytes = bytes / 1024;

        DecimalFormat formato = new DecimalFormat("#.##");
        return formato.format(bytes) + " Kb";
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static boolean copia(File fonte, File destino) {

        try {
            InputStream in;
            in = new FileInputStream(fonte);

            OutputStream out = new FileOutputStream(destino);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            return true;
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "ERRO! \n O 'Caminho dos arquivos:' pode estar errado!");
            return false;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "ERRO! \n O 'Caminho dos arquivos:' pode estar errado!");
            return false;
        }
    }

}
