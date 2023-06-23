package Dados;

import Servidor.Cliente;
import java.util.ArrayList;
import java.util.Collections;

public class Tabuleiro extends Carta{
    
    public ArrayList<Carta> Tabuleiro = new ArrayList<Carta>();
    
    public Tabuleiro() {
        criacaoTabuleiro(Tabuleiro);
    }

    public ArrayList<Carta> getTabuleiro() {
        return Tabuleiro;
    }
    
    public void criacaoTabuleiro(ArrayList<Carta> Tabuleiro) {
        criacaoCartasFemininas(Tabuleiro);
        criacaoCartasMasculinas(Tabuleiro);
        embaralharCartasTab(Tabuleiro);
    }
    
    public void criacaoCartasFemininas(ArrayList<Carta> Tabuleiro) {
        String nome = "", sexo = "Feminino",  cor_cabelo = "", chapeu = "", cor_pele = "", oculos = "",
                cor_olhos = "", cabelo = "", bigode = "";
        int i;
        
        for(i = 1; i <= 5; i++) {
            for(int j = 0; j < 2; j++) {
                Carta carta = new Carta(); 
                if(i == 1) {
                    nome = "Ana";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Escura";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 2) {
                    nome = "Clara";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Tem chapeu";
                    cor_pele = "Pele Escura";
                    oculos = "Usa oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 3) {
                    nome = "Sonia";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 4) {
                    nome = "Maria";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 5) {
                    nome = "Lúcia";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Azul";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }

                carta.setNome(nome);
                carta.setSexo(sexo);
                carta.setCor_pele(cor_pele);
                carta.setCor_cabelo(cor_cabelo);
                carta.setChapeu(chapeu);
                carta.setOculos(oculos);
                carta.setCor_olhos(cor_olhos);
                carta.setCabelo(cabelo);
                carta.setBigode(bigode);
                Tabuleiro.add(carta);
            }
        }
        
    }
    
    public void criacaoCartasMasculinas(ArrayList<Carta> Tabuleiro) {
        String nome = "", sexo = "Masculino",  cor_cabelo = "", chapeu = "", cor_pele = "", oculos = "",
                cor_olhos = "", cabelo = "", bigode = "";
        int i;
        
        for(i = 1; i <= 19; i++) {
            for(int j = 0; j < 2; j++) {
                Carta carta = new Carta(); 
                if(i == 1) {
                    nome = "Jorge";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Tem chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 2) {
                    nome = "Beto";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Tem chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 3) {
                    nome = "Pedro";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Azul";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 4) {
                    nome = "Paulo";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Usa oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 5) {
                    nome = "Daniel";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 6) {
                    nome = "Helio";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Escura";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Nao cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 7) {
                    nome = "Ricardo";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Nao cabelo";
                    bigode = "Tem bigode";
                }
                if(i == 8) {
                    nome = "Roberto";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Azul";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 9) {
                    nome = "Zeca";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Usa oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 10) {
                    nome = "Luis";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Escura";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Tem bigode";
                }
                if(i == 11) {
                    nome = "Henrique";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Tem chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 12) {
                    nome = "Chico";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Escura";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 13) {
                    nome = "João";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Usa oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Nao cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 14) {
                    nome = "Marcos";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Tem bigode";
                }
                if(i == 15) {
                    nome = "Carlos";
                    cor_cabelo = "Cabelo Claro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Tem bigode";
                }
                if(i == 16) {
                    nome = "Tony";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Usa oculos";
                    cor_olhos = "Azul";
                    cabelo = "Nao cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 17) {
                    nome = "Fernando";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Escura";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Tem cabelo";
                    bigode = "Nao bigode";
                }
                if(i == 18) {
                    nome = "Alfredo";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Escura";
                    oculos = "Nao oculos";
                    cor_olhos = "Azul";
                    cabelo = "Tem cabelo";
                    bigode = "Tem bigode";
                }
                if(i == 19) {
                    nome = "Edu";
                    cor_cabelo = "Cabelo Escuro";
                    chapeu = "Nao chapeu";
                    cor_pele = "Pele Clara";
                    oculos = "Nao oculos";
                    cor_olhos = "Castanho";
                    cabelo = "Nao cabelo";
                    bigode = "Nao bigode";
                }

                carta.setNome(nome);
                carta.setSexo(sexo);
                carta.setCor_pele(cor_pele);
                carta.setCor_cabelo(cor_cabelo);
                carta.setChapeu(chapeu);
                carta.setOculos(oculos);
                carta.setCor_olhos(cor_olhos);
                carta.setCabelo(cabelo);
                carta.setBigode(bigode);
                Tabuleiro.add(carta);
            }
        }
    }
    
    public void embaralharCartasTab(ArrayList<Carta> Tabuleiro) {
        Collections.shuffle(Tabuleiro);
    }
    
    public ArrayList<Carta> cartasJogador(ArrayList<Carta> Tabuleiro) {
        ArrayList<Carta> CartasJogador = new ArrayList<Carta>();
        ArrayList<Carta> CartasDuplicadas = new ArrayList<Carta>();

        for (int i = 0; i < Tabuleiro.size(); i++) {
            Carta cAtual = Tabuleiro.get(i);
            boolean duplicata = false;

            for (int j = i + 1; j < Tabuleiro.size(); j++) {
                if (cAtual.getNome().equals(Tabuleiro.get(j).getNome())) {
                    duplicata = true;
                    break;
                }
            }

            if (duplicata) {
                CartasDuplicadas.add(cAtual);
            } else {
                CartasJogador.add(cAtual);
            }
        }

        Tabuleiro.removeAll(CartasDuplicadas);

        return CartasJogador;
    }
    
    public ArrayList<Carta> personagemJog(ArrayList<Carta> Tabuleiro, Cliente j) {
        ArrayList<Carta> personagemJog = new ArrayList<Carta>();
        int i;
        
        embaralharCartasTab(Tabuleiro);

        for (i = 0; i < 1; i++) {
            personagemJog.add(Tabuleiro.get(i));
        }
        
        for (Carta c : personagemJog) {
            j.getSaida().println("Personagem do Jogador: " + c.getNome() + ", " + c.getCor_pele() + ", " + c.getCor_olhos()
            + ", " + c.getOculos() + ", " + c.getCabelo() + ", " + c.getCor_cabelo() + ", " + c.getChapeu() + ", " + c.getBigode() + ", " + c.getSexo());
        }
        
        return personagemJog;
    }
    
    public void mostraTab(ArrayList<Carta> Tabuleiro, Cliente j) {
        int i = 0;
        for (Carta c : Tabuleiro) {
            j.getSaida().println("Tabuleiro: " + c.getNome() + ", " + c.getSexo() + ", " + c.getCor_pele() + ", " + 
                    c.getCor_olhos() + ", " + c.getCabelo() + ", " + c.getCor_cabelo() + ", " + c.getChapeu() + ", " + c.getBigode() + ", " + c.getOculos());
            i++;
        }
    }
}