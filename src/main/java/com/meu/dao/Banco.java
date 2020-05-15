/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meu.dao;

/**
 *
 * @author Éder Mello
 */
public class Banco {
    protected String pathDatabase;
    protected String ipHost;
    protected String porta;
    protected String user;
    protected String senha;

    public String getPathDatabase() {
        return pathDatabase;
    }

    public void setPathDatabase(String pathDatabase) {
        this.pathDatabase = pathDatabase;
    }

    public String getIpHost() {
        return ipHost;
    }

    public void setIpHost(String ipHost) {
        this.ipHost = ipHost;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Banco(String pathDatabase, String ipHost, String porta, String user, String senha) {
        this.pathDatabase = pathDatabase;
        this.ipHost = ipHost;
        this.porta = porta;
        this.user = user;
        this.senha = senha;
    }

    public Banco() {
    }
}
