import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.Collections;
import java.util.Vector;
import java.util.HashMap;
import java.util.regex.*;

import java.util.*;

import jdk.jfr.ContentType;

public class Control{

   public Control(){}
    
    //effects: prende come parametro un testo e verifica che vada bene
    //throws: lancia un eccezione se la lunghezza del dato non é valida
    public static void dataControl(String text) throws InvalidData{
        if(text.length() == 0){
            throw new InvalidData("Errore: testo non valido");
        }
    }
    
    //effects: prende come parametro un testo e verifica che non sia nullo
    //throws: lancia un eccezione se il dato é nullo
    public static void nullControl(String text) throws InvalidData{
        if(text == null){
            throw new InvalidData("Errore: testo non valido");
        }
    }

   //effects: prende come paramtro un codice ed una lista di Data, controlla se esiste o no quel contenuto (tramite il codice)
   //return: true o false a seconda se esiste o no il dato
   public boolean findData(int codice, List<Data> lista){
        for(int x = 0; x < lista.size(); x++){
            if(codice == lista.get(x).getCi()){
                return true;
            }
        }
        return false;
   }

   //effects: prende come parametro un codice ed una lista di String e controlla se esiste una categoria già fatta con quel nome
   //return: true o false a seconda se esiste o no il dato
  public boolean findCategory(String nomeCat, List<String> lista){
    for(int x = 0; x < lista.size(); x++){
        if(nomeCat.equals(lista.get(x))){
            return true;
        }
    }
    return false;
  }

  //effects: prende come parametro un cod ed un hashmap e ricerca un elemento
  //return: true se trovato e false se no
  public boolean findData2(int codice, HashMap<Integer,Data> hm){
      
    Iterator<Map.Entry<Integer,Data>> it = hm.entrySet().iterator();
    while(it.hasNext()){
       Map.Entry<Integer, Data> e = it.next();
        if(e.getKey() == codice){
           return true;
        }
    }
    return false;
  }



}
