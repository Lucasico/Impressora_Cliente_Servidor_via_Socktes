/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.Serializable;
import servidor.ImpressoraConfig.CorImpressao;
import servidor.ImpressoraConfig.NomeImpressoras;
import servidor.ImpressoraConfig.QualidadeImpressao;

/**
 *
 * @author lucas
 */
public class Impressao implements Serializable{
    
    public String texto;
    public int numCopias;
    public boolean frentVerso;
    public CorImpressao cor;
    public QualidadeImpressao qualidade;
    public NomeImpressoras nome;
    public String desistir;
   
    
    public void desistirImpressao(String desistir){
        this.desistir = desistir;
    }
    
  
    
}
