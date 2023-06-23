package Servidor;

import Dados.Carta;
import Dados.Tabuleiro;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Servidor extends Thread{
    
    private static List<Cliente> clientes;
    private Cliente cliente;
    private Socket conexao;
    private String nomeCliente;
    private  int id_cliente = 0;
    private String[] vetor = null;
    private String texto = null;
    private static boolean jogoIniciado = false;
    private static int quantJogadores = 0;
    private Tabuleiro tabuleiro;
    private Carta carta;
    private int vitoria = 0;
    private int derrota = 0;
    
    int i = 1;

    public Servidor(Cliente c, Tabuleiro t) {
        cliente = c;
        tabuleiro = t;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getSocket().getInputStream()));
            PrintStream saida = new PrintStream(cliente.getSocket().getOutputStream());
            cliente.setSaida(saida);
            boolean JogoPrestesAIniciar = true;
            int permite = 0;

            do {
                permite = 1;
                nomeCliente = entrada.readLine().trim();
                for (Cliente c : clientes) {
                    if (c.getNome() != null && nomeCliente.toLowerCase().equals(c.getNome().toLowerCase())) {
                        sendTo(saida, "Esse jogador já está na partida", ".Por favor informe outro nome", cliente.getId());
                        permite = 0;
                    }
                }
            } while (permite == 0 || nomeCliente.trim().equals(""));
            
            System.out.println("NOME CLIENTE: " + nomeCliente);

            if (nomeCliente == null) {
                return;
            }

            cliente.setNome(nomeCliente);
            
            if (cliente.getHost()) {
                totalParticipantes();
            }
            
            sendTo(saida, cliente.getNome() + ", aguarde pouco esperando mais um jogador", ", o jogo já vai começar!", cliente.getId());

            for (Cliente c : clientes) {
                if (c.getNome() == null) {
                    JogoPrestesAIniciar = false;
                }
            }
            
            if (clientes.size() != 0 && clientes.size() == quantJogadores && JogoPrestesAIniciar) {
                jogoIniciado = true;
                historico();
                ranking(clientes);
                for (Cliente c : clientes) {
                    c.setCartaJogador(tabuleiro.cartasJogador(tabuleiro.getTabuleiro()));
                    tabuleiro.mostraTab(c.getCartaJogador(), c);
                    c.setPersonagemJog(tabuleiro.personagemJog(c.getCartaJogador(), c));
                }
            }
            
            if (jogoIniciado) {
                sendToAll(clientes.get(0).getNome() + ", está jogando");
                PrintStream saida2 = new PrintStream(clientes.get(0).getSocket().getOutputStream());
                clientes.get(0).setSaida(saida2);
                clientes.get(0).setVezJogar(true);
                
                clientes.get(0).setVezJogar(Boolean.TRUE);
                
                String aux = opcao(clientes.get(0));
                Cliente c = clientes.get(0);
                int jogada = 0, aux2 = 0;
                c.setContJogada(jogada);
                while (!aux.equals("0")) {
                    switch (aux) {
                        case "0":
                            conexao.close();
                            sendToAll("Jogo encerrado");
                            break;
                        case "1":
                            jogada++;
                            c.setContJogada(jogada);
                            jogada(c, tabuleiro, carta, jogada);
                            break;
                        case "2":
                            adivinharJogada(c, tabuleiro, carta);
                            sendToAll("Jogo encerrado");
                            break;
                        default:
                            break;
                    }
                }
            }

            sendToAll(saida, " saiu da conexao", " do chat");
            clientes.remove(saida);

            if (this.conexao != null) {
                this.conexao.close();
            }
            
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
    
    public void sendToAll(String msg){
        try{
         Iterator<Cliente> iter = clientes.iterator();
            while (iter.hasNext()) {
                Cliente outroJogador = iter.next();
                PrintStream chat = (PrintStream) outroJogador.getSaida();
                chat.println(msg);
            }   
        }
        catch(Exception e) {
            System.out.println("IOException: " + e); 
        }
    }
    
        public String opcao(Cliente cliente) {
        try {
            if (cliente.getVezJogar()) {
                cliente.getSaida().println("****** MENU OPCOES *********"
                        +                  "\n* 0 - Sair                 *"
                        +                  "\n* 1 - Realizar jogada      *"
                        +                  "\n* 2 - Adivinhar personagem *"
                        +                  "\n****************************"
                        +                  "\n Sua escolha: ");
                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getSocket().getInputStream()));
                String aux = entrada.readLine();
                return aux;
            } else if (!cliente.getVezJogar()) {
                cliente.getSaida().println("Nao é sua vez de jogar, aguarde!");
            }

        } catch (Exception e) {
            System.out.println("IOException: " + e);
        }
        return null;
    }

    
    
    public void sendToAll(PrintStream saida, String texto1, String texto2) {
        //System.out.println("entrou n o sendtoall ");
        try {
            //System.out.println("texto 2: "+texto2);
            Iterator<Cliente> iter = clientes.iterator();
            while (iter.hasNext()) {
                Cliente outroCliente = iter.next();
                PrintStream chat = (PrintStream) outroCliente.getSaida();
                if (chat != saida) {
                    chat.println(cliente.getNome()
                            + " com IP: "
                            + cliente.getSocket().
                                    getRemoteSocketAddress()
                            + texto1 + texto2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTo(PrintStream saida, String texto1, String texto2, int id_cliente) {
        try {
            Iterator<Cliente> iter = clientes.iterator();
            while (iter.hasNext()) {
                System.out.println("id cliente: "+ id_cliente);
                Cliente outroCliente = iter.next();
                if (outroCliente.getId() == id_cliente) {
                    PrintStream chat = (PrintStream) outroCliente.getSaida();
                    if (chat != saida) {
                        chat.println(cliente.getNome()
                            + " com IP: "
                            + cliente.getSocket().
                                    getRemoteSocketAddress()
                            + texto1 + texto2);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int contJogador() {
        int cont = 0;
        for (Cliente c : clientes) {
            if (c.getIp().equals(cliente.getIp())) {
                break;
            }
            cont++;
        }
        return cont;
    }
    
    public void totalParticipantes() throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getSocket().getInputStream()));
        PrintStream saida = new PrintStream(cliente.getSocket().getOutputStream());
        sendTo(saida, ", " + cliente.getNome() + ", é o primeiro a entrar,", "então escolha a quantos irão jogar(apenas 2 jogadores): ", cliente.getId());
        do {
            String quant = entrada.readLine();
            if (!(quant.matches("[+-]?\\d*(\\.\\d+)?")) || quant.equals("")) {
                sendTo(saida, "Informe um número valido!", "(Informe o valor 2).", cliente.getId());
            } else {
                quantJogadores = Integer.parseInt(quant);
            }

            if (quantJogadores < 2 || quantJogadores > 2) {
                sendTo(saida, "A quantidade de jogadores deve ser apenas 2.", "Informe a quantidade correta!", cliente.getId());
            } else if (quantJogadores < clientes.size()) {
                quantJogadores = 0;
                sendTo(saida, "Já existem " + clientes.size() + " jogadores conectados,", " por favor informe um número maior ou igual a esse.", cliente.getId());
            }
        } while (quantJogadores < 2 || quantJogadores > 2);
    }
    
    public void ranking(List<Cliente> clientes) {
        
        Collections.sort(clientes, new Comparator<Cliente>() {
            public int compare(Cliente c1, Cliente c2) {
                return Integer.compare(c2.getVitoria(), c1.getVitoria());
            }
        });
        
        sendToAll("Ranking: ");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            sendToAll((i + 1) + "° Lugar: " + cliente.getNome());
        }
  
    }
    
    
    public void historico() {
        
        sendToAll("Historico dos jogos: ");
        for(Cliente c: clientes) {
            sendToAll("Cliente: " + c.getNome() + ", " + c.getVitoria() + "V/" + c.getDerrota() + "D");
        }
    }
    
    public void jogada(Cliente cliente, Tabuleiro tabuleiro, Carta carta, int jogada) {
        try {
            int aux = 0;
            for (Cliente c : clientes) {
                c.getSaida().println("*************************** MENU JOGADAS *************************"
                        + "\n* 1 - Seu personagem é homem?                                    *"
                        + "\n* 2 - Seu personagem tem óculos?                                 *"
                        + "\n* 3 - Seu personagem tem cabelo?                                 *"
                        + "\n* 4 - Seu personagem tem cabelo escuro (Preto/Castanho)?         *"
                        + "\n* 5 - Seu personagem tem bigode?                                 *"
                        + "\n* 6 - Seu personagem tem pele clara?                             *"
                        + "\n* 7 - Seu personagem tem olhos castanhos?                        *"
                        + "\n* 8 - Seu personagem tem chapéu?                                 *"
                        + "\n******************************************************************"
                        + "\nSua escolha: ");

                BufferedReader entrada = new BufferedReader(new InputStreamReader(c.getSocket().getInputStream()));
                String escolha = entrada.readLine();
                
                if(escolha.equals("1")) {
                    if(jogada == 1) {
                        c.getSaida().println("Não é possivel realizar a pergunta do sexo na primeira rodada");
                        c.setContJogada(1);
                        jogada(c, tabuleiro, carta, c.getContJogada());
                    }
                }
                
                Cliente outroCliente = obterOutroCliente(c);
                outroCliente.getSaida().println("Sua pergunta: " + getDescricaoPergunta(escolha, jogada));

                // Aguardar resposta do outro jogador
                BufferedReader entradaOutroJogador = new BufferedReader(new InputStreamReader(outroCliente.getSocket().getInputStream()));
                String respostaOutroJogador = entradaOutroJogador.readLine();

                c.getSaida().println("Resposta do outro jogador: " + respostaOutroJogador);
                removerCartas(c, escolha, tabuleiro, carta, respostaOutroJogador);
                aux = 1;

                if (aux > 0) {
                    c.setVezJogar(Boolean.FALSE);
                    Iterator<Cliente> iter = clientes.iterator();
                    int cont = 0;
                    boolean encontrouCliente = false;

                    while (iter.hasNext()) {
                        Cliente j = iter.next();
                        if (j.getIp().equals(c.getIp())) {
                            encontrouCliente = true;
                            break;
                        }
                        cont++;
                    }

                    if (encontrouCliente) {
                        if (encontrouCliente) {
                            if (cont < clientes.size() - 1) {
                                clientes.get(cont + 1).setVezJogar(Boolean.TRUE);
                                String aux2 = opcao(clientes.get(cont + 1));
                            } else {
                                clientes.get(0).setVezJogar(Boolean.TRUE);
                                String aux2 = opcao(clientes.get(0));
                            }
                        }
                    }

                    aux--;
                }
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        } catch (NumberFormatException e) {
            System.out.println("Escolha inválida. Por favor, insira um número válido.");
        }
    }
    
    private String getDescricaoPergunta(String escolha, int jogada) {
            
            switch (escolha) {
                case "1":
                    return "Seu personagem é homem?";
                case "2":
                    return "Seu personagem tem oculos?";
                case "3":
                    return "Seu personagem tem cabelo?";
                case "4":
                    return "Seu personagem tem cabelo escuro (Preto/Castanho)?";
                case "5":
                    return "Seu personagem tem bigode?";
                case "6":
                    return "Seu personagem tem pele clara?";
                case "7":
                    return "Seu personagem tem olhos castanhos?";
                case "8":
                    return "Seu personagem tem chapeu?";
                default:
                    return "Pergunta nao encontrada";
            }
    }
    
    public void removerCartas (Cliente cliente, String escolha, Tabuleiro tabuleiro, Carta carta, String respostaOutroJogador) {
        
        for(Cliente c: clientes) {
            switch (escolha) {
                case "1":
                    if (respostaOutroJogador.equals("Sim")) {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getSexo().equals("Feminino")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    } else {
                        if(c.getVezJogar() == Boolean.TRUE) {
                           ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getSexo().equals("Masculino")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    }   
                    break;
                case "2":
                    if (respostaOutroJogador.equals("Sim")) {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getOculos().equals("Nao oculos")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    } else {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getOculos().equals("Usa oculos")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    }
                    break;
                case "3":
                    if (respostaOutroJogador.equals("Sim")) {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getCabelo().equals("Nao cabelo")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    } else {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getCabelo().equals("Tem cabelo")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    }
                    break;
                case "4":
                    if (respostaOutroJogador.equals("Sim")) {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getCor_cabelo().equals("Cabelo Claro")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    } else {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getCor_cabelo().equals("Cabelo Escuro")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    }
                    break;
                case "5":
                    if (respostaOutroJogador.equals("Sim")) {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getBigode().equals("Nao bigode")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    } else {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getBigode().equals("Tem bigode")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    }
                    break;
                case "6":
                    if (respostaOutroJogador.equals("Sim")) {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getCor_pele().equals("Pele Escura")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    } else {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getCor_pele().equals("Pele Clara")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    }
                    break;
                case "7":
                    if (respostaOutroJogador.equals("Sim")) {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getCor_olhos().equals("Azul")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    } else {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getCor_olhos().equals("Castanho")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    }
                    break;
                case "8":
                    if (respostaOutroJogador.equals("Sim")) {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getChapeu().equals("Nao chapeu")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    } else {
                        if(c.getVezJogar() == Boolean.TRUE) {
                            ArrayList<Carta> cartasRemover = new ArrayList<>();
                            for (Carta card : c.getCartaJogador()) {
                                if (card.getChapeu().equals("Tem chapeu")) {
                                    cartasRemover.add(card);
                                }
                            }
                            c.getCartaJogador().removeAll(cartasRemover);
                            tabuleiro.mostraTab(c.getCartaJogador(), c);
                            for(Carta card: c.getPersonagemJog()) {
                                c.getSaida().println("Seu personagem: " + card.getNome() + ", " + card.getCor_pele() + ", " + card.getCor_olhos()
                                + ", " + card.getOculos() + ", " + card.getCabelo() + ", " + card.getCor_cabelo() + ", " + card.getChapeu() + ", " + card.getBigode() + ", " + card.getSexo());
                            }
                        }
                    }
                    break;
                default:
                    c.getSaida().println("Jogada não encontrada");
                    break;
                }        
        }
    }
        
    public Cliente obterOutroCliente(Cliente cliente) {
        
        if (clientes.size() > 1) {
            if (clientes.get(0) == cliente) {
                return clientes.get(1);
            } else {
                return clientes.get(0);
            }
        }

        return null;
    }
    
    public void adivinharJogada(Cliente cliente, Tabuleiro tabuleiro, Carta carta) {
        try {
            for (Cliente c : clientes) {
                if(c.getVezJogar() == Boolean.TRUE) {
                    c.getSaida().println("Digite o nome do personagem que acha que é: ");

                    BufferedReader entrada = new BufferedReader(new InputStreamReader(c.getSocket().getInputStream()));
                    String adivinhar = entrada.readLine();

                    // Enviar escolha para o outro cliente
                    Cliente outroCliente = obterOutroCliente(c);
                    outroCliente.getSaida().println("Seu personagem é: " + adivinhar + " (SIM/NAO): ");

                    BufferedReader entradaOutroJogador = new BufferedReader(new InputStreamReader(outroCliente.getSocket().getInputStream()));
                    String respostaOutroJogador = entradaOutroJogador.readLine();

                    if(respostaOutroJogador.toUpperCase().equals("SIM")) {
                        sendToAll("Parabéns o jogador: " + c.getNome() + " ganhou o jogo");
                        vitoria++;
                        c.setVitoria(vitoria);
                        outroCliente.setDerrota(derrota + 1);
                    } 
                    else {
                        sendToAll("Parabéns o jogador: " + c.getNome() + " ganhou o jogo");
                        vitoria++;
                        outroCliente.setVitoria(vitoria);
                        c.setDerrota(derrota + 1);
                    }
                }
            }
            ranking(clientes);
            historico();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        clientes = new ArrayList<Cliente>();
        Tabuleiro tabuleiro = new Tabuleiro();
        try {
            ServerSocket s = new ServerSocket(2223);
            while (true) {
                System.out.println("Esperando alguem se conectar ");
                Socket conexao = s.accept();

                Cliente cliente = new Cliente();

                cliente.setId(clientes.size());
                cliente.setIp(conexao.getRemoteSocketAddress().toString());
                cliente.setSocket(conexao);
                
                if (jogoIniciado || clientes.size() == 3) {
                    cliente.getSocket().close();
                    continue;
                }

                if (clientes.isEmpty()) {
                    cliente.setHost(true);
                }

                clientes.add(cliente);

                System.out.println("Conectou: " + cliente.getIp());
                System.out.println("ID do cliente: " + cliente.getId());

                Thread t = new Servidor(cliente, tabuleiro);
                
                tabuleiro.criacaoTabuleiro(tabuleiro.getTabuleiro());
                
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}