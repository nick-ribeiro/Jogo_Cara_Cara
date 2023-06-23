package Servidor;

import java.io.*;
import java.net.*;

public class ClienteSocket extends Thread{
    
    private static boolean done = false;
    private Socket conexao;

    public ClienteSocket(Socket s) {
        conexao = s;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));

            String texto;

            while (true) {
                texto = entrada.readLine();

                if (texto == null) {
                    System.out.println("Conexao encerrada");
                    break;
                }
                System.out.println("\n" + texto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        done = true;
    }

    public static void main(String[] args) {
        try {
            Socket conexao = new Socket("127.0.0.1", 2223);

            PrintStream saida = new PrintStream(conexao.getOutputStream());

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Digite seu nome: ");

            String nome = teclado.readLine();
            saida.println(nome);

            Thread t = new ClienteSocket(conexao);
            t.start();

            String texto;

            while (true) {
                System.out.println("> ");
                texto = teclado.readLine();
                if (done) {
                    break;
                }
                saida.println(texto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
