/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String newImpressao = "";
        /*
            A primeira classe importante é a ServerSocket e ela é responsável 
            por esperar a conexão do cliente. Esta classe possui um construtor 
            onde passamos a porta que desejamos usar para escutar as conexões.
         */
        ServerSocket servidor = new ServerSocket(5555);
        System.out.println("Servidor iniciado na porta 5555");
        /*
            O método accept() escuta uma conexão e aceita se alguma for encontrada. 
            O accept() bloqueia todo o restante até que uma conexão seja feita, ele 
            fica em espera aguardando que alguém conecte. 
         */
        System.out.println("Buscando conexão");
        Socket soquete = servidor.accept();

        System.out.println("Conexão aceita pelo cliente: " + soquete.getPort());

        ObjectOutputStream saida = new ObjectOutputStream(soquete.getOutputStream());
        ObjectInputStream entrada = new ObjectInputStream(soquete.getInputStream());
        do {
            Impressao impressora = new Impressao();

            saida.writeObject(impressora);
            saida.flush();

            //Recebe do cliente a class para definir se a impressão vai ser feita
            Impressao impressaoCliente = (Impressao) entrada.readObject();
            // int novaImpressao = 0;
            if ("SIM".equals(impressaoCliente.desistir) || "sim".equals(impressaoCliente.desistir)) {
                System.out.println("--------------------------------------");
                System.out.println("Impressão cancelada");
                System.out.println("--------------------------------------");
                //  entrada.close();
                // saida.close();
                // soquete.close();
            } else {
                System.out.println("--------------------------------------");
                System.out.println("PARAMENTRO DE CONFIGURADOS DE IMPRESSÃO ESCOLIDA PELO CLIENTE");
                System.out.println("--------------------------------------");
                System.out.println("Impressora: " + impressaoCliente.nome);
                System.out.println("Numero de copias: " + impressaoCliente.numCopias);
                System.out.println("Frente e verso: " + impressaoCliente.frentVerso);
                System.out.println("Qualidade de impressão: " + impressaoCliente.qualidade);
                System.out.println("Cor de impressão: " + impressaoCliente.cor);
                System.out.println("---------------------------------------");
                for (int i = 1; i <= impressaoCliente.numCopias; i++) {
                    System.out.println("Texto a ser impresso - " + i + " : " + impressaoCliente.texto);
                }
                System.out.println("---------------------------------------");
                String confImpressao = "Impressão feita com SUCESSO";
                saida.writeObject(confImpressao);
            }
            String novaImpressao = (String) entrada.readObject();
            newImpressao = novaImpressao;
        } while ("sim".equals(newImpressao) || "SIM".equals(newImpressao));
        System.out.println("Fim da lista de impressões");
        entrada.close();
        saida.close();
        soquete.close();
    }
}
