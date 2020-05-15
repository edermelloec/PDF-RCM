/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meu.model;

/**
 *
 * @author Éder Mello
 */
public class Parametros {

    String exercicio;
    int codigo_cls_doc;
    int codigo_tipo_doc;
    int codigo_cax_doc;
    String caminhoArquivo;

    public Parametros() {
    }

    public Parametros(int codigo_tipo_doc, String exercicio, int codigo_cls_doc,
            int codigo_cax_doc, String caminhoBanco) {
        this.codigo_tipo_doc = codigo_tipo_doc;
        this.exercicio = exercicio;
        this.codigo_cls_doc = codigo_cls_doc;
        this.codigo_cax_doc = codigo_cax_doc;
        this.caminhoArquivo = caminhoBanco;
    }

    public String getExercicio() {
        return exercicio;
    }

    public void setExercicio(String exercicio) {
        this.exercicio = exercicio;
    }

    public int getCodigo_cls_doc() {
        return codigo_cls_doc;
    }

    public void setCodigo_cls_doc(int codigo_cls_doc) {
        this.codigo_cls_doc = codigo_cls_doc;
    }

    public int getCodigo_tipo_doc() {
        return codigo_tipo_doc;
    }

    public void setCodigo_tipo_doc(int codigo_tipo_doc) {
        this.codigo_tipo_doc = codigo_tipo_doc;
    }

    public int getCodigo_cax_doc() {
        return codigo_cax_doc;
    }

    public void setCodigo_cax_doc(int codigo_cax_doc) {
        this.codigo_cax_doc = codigo_cax_doc;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoBanco) {
        this.caminhoArquivo = caminhoBanco;
    }

}
