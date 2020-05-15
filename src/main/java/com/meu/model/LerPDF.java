/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meu.model;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Éder Mello
 */
public class LerPDF {

    public File[] arquivoSelecionado() {

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf");

        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(true);
        int returnVal = fc.showOpenDialog(fc);
        File[] files = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (!fc.isMultiSelectionEnabled()) {
                fc.setMultiSelectionEnabled(true);
            }

            files = fc.getSelectedFiles();
        }

        return files;

    }

    public File[] arquivoPastaSelecionado() {

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        fc.setMultiSelectionEnabled(true);

        int returnVal = fc.showOpenDialog(fc);

        File[] files = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = new File(fc.getSelectedFile().getAbsolutePath());

            files = file.listFiles();
        }

        return files;

    }

}
