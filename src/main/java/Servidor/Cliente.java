package Servidor;

import Dados.Carta;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Cliente {
    
    private int id;
    private String ip;
    private String nome;
    private PrintStream saida;
    private Socket socket;
    private boolean vezJogar = false;
    private ArrayList<Carta> CartaJogador;
    private ArrayList<Carta> personagemJog;
    private boolean host = false;
    private int contJogada;
    private int vitoria;
    private int derrota;
    
    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getNome() {
        return nome;
    }

    public PrintStream getSaida() {
        return saida;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSaida(PrintStream saida) {
        this.saida = saida;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean getVezJogar() {
        return vezJogar;
    }

    public void setVezJogar(boolean vezJogar) {
        this.vezJogar = vezJogar;
    }
    
    public ArrayList<Carta> getCartaJogador() {
        return CartaJogador;
    }

    public void setCartaJogador(ArrayList<Carta> CartaJogador) {
        this.CartaJogador = CartaJogador;
    }

    public ArrayList<Carta> getPersonagemJog() {
        return personagemJog;
    }

    public void setPersonagemJog(ArrayList<Carta> personagemJog) {
        this.personagemJog = personagemJog;
    }

    public boolean getHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }

    public int getContJogada() {
        return contJogada;
    }

    public void setContJogada(int contJogada) {
        this.contJogada = contJogada;
    }

    public int getVitoria() {
        return vitoria;
    }

    public void setVitoria(int vitoria) {
        this.vitoria = vitoria;
    }

    public int getDerrota() {
        return derrota;
    }

    public void setDerrota(int derrota) {
        this.derrota = derrota;
    }
}
