/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import servidor.Impressao;
import servidor.ImpressoraConfig.CorImpressao;
import servidor.ImpressoraConfig.QualidadeImpressao;
import servidor.ImpressoraConfig.NomeImpressoras;

/**
 *
 * @author lucas
 */
public class Cliente {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int escolhaImpressora, numCopias;
        String freteVerso, quanlidImpressao, corImpressao, texto, desistirImpressao, novaImpressao;

        Scanner teclado = new Scanner(System.in);

        Socket soquete = new Socket("localhost", 5555);

        ObjectOutputStream saida = new ObjectOutputStream(soquete.getOutputStream());
        ObjectInputStream entrada = new ObjectInputStream(soquete.getInputStream());

        do {
            Impressao im = new Impressao();
            Impressao mensagem = (Impressao) entrada.readObject();
            System.out.println("");
            System.out.println("- - Escolher parametros para impressão - -");
            System.out.println("Escolher impressora -> [1]-BROTHER [2]-HP2050  [3]-NIKON");

            escolhaImpressora = teclado.nextInt();

            //escolhendo a impressora
            if (escolhaImpressora == 1) {
                mensagem.nome = NomeImpressoras.BROTHER;
            } else if (escolhaImpressora == 2) {
                mensagem.nome = NomeImpressoras.HP2050;
            } else if (escolhaImpressora == 3) {
                mensagem.nome = NomeImpressoras.NIKON;
            } else {
                System.out.println("Escolha invalida de impressora");
            }

            System.out.println("---------------------------------------");
            //escolhendo numero de copias
            System.out.print("Numeros de cópias: ");
            numCopias = teclado.nextInt();
            mensagem.numCopias = numCopias;
            //escolhendo frete ou verso
            System.out.print("Frente e verso a impressão SIM ou NAO: ");
            freteVerso = teclado.next();
            if ("SIM".equals(freteVerso) || "sim".equals(freteVerso)) {
                mensagem.frentVerso = true;
            } else {
                mensagem.frentVerso = false;
            }
            //escolhendo qualidade de impressão
            System.out.print("Qualidade de impressão BAIXA, NORMAL ou ALTA: ");
            quanlidImpressao = teclado.next();
            if ("BAIXA".equals(quanlidImpressao) || "baixa".equals(quanlidImpressao)) {
                mensagem.qualidade = QualidadeImpressao.BAIXA;
            } else if ("NORMAL".equals(quanlidImpressao) || "normal".equals(quanlidImpressao)) {
                mensagem.qualidade = QualidadeImpressao.NORMAL;
            } else {
                mensagem.qualidade = QualidadeImpressao.ALTA;
            }
            //escolhendo cor de impressão
            System.out.print("Cor da impressão PRETO, COLORIDO: ");
            corImpressao = teclado.next();
            if ("PRETO".equals(corImpressao) || "preto".equals(corImpressao)) {
                mensagem.cor = CorImpressao.PRETO;
            } else {
                mensagem.cor = CorImpressao.COLORIDO;
            }
            //texto a ser impresso
            System.out.print("Digite texto a ser impresso: ");
            mensagem.texto = teclado.next();
            //Enviando parametros de impressão e o texto

            System.out.print("Deseja desistir da impressão SIM ou NÃO:");
            mensagem.desistirImpressao(teclado.next());
            //Saida para definir se a impressão continua
            saida.writeObject(mensagem);
            if ("SIM".equals(mensagem.desistir) || "sim".equals(mensagem.desistir)) {
                System.out.println("---------------------------------");
                System.out.println("Impressão cancelada");
                System.out.println("---------------------------------");

                //entrada.close();
                //saida.close();
                //soquete.close();
            } else {
                //mensagem de confirmação de impressão
                String confImpressao = (String) entrada.readObject();
                System.out.println("---------------------------------");
                System.out.println("-> " + confImpressao);
                System.out.println("---------------------------------");
            }
            System.out.print("Deseja realizar nova impressão: ");
            novaImpressao = teclado.next();
            saida.writeObject(novaImpressao);
        } while ("SIM".equals(novaImpressao) || "sim".equals(novaImpressao));
        System.out.println("Fim da lista de impressões");
        entrada.close();
        saida.close();
        soquete.close();
    }
}
