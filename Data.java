import java.util.ArrayList;

public class Data implements Comparable{
        //OVERVIEW: la classe Data rappresenta i post che andremo ad inserire nella lista di contenuti
    
    /*
     Invariante Data
     Gli utenti devono avere un nome con una lunghezza minima d 1 carattere
     Per andare a modificare i dati precedentemente le funzioni devono essere validate dalla password
     
     Funzione di astrazione Data
     Autore .length() > 0
     Autore != null && categoria != null
     */

       private int codiceIdentificativo; //definisce il codice ID del post
       private String autore; //definisce l'autore del post
       private String text; //definisce la descrizione del post
       private int numLike; //definisce il numero di like del post
       private String categoria; //definisce la categoria del post
       private ArrayList<String> whoLiked; //crea una lista di persone che hanno messo like al post

       public Data(String testo, String aut, String cate, int ci){ //inizializziamo i dati
        this.autore = aut;
        this.text = testo;
        this.numLike = 0;
        this.categoria = cate;
        this.codiceIdentificativo = ci;
        this.whoLiked = new ArrayList<String>();
       }
       

    //effects: chiama la funzione addLike() e aggiunge una persona alla lista delle persone che mettono lile
    public void addPeopleWhoLiked(String name){ 
        this.addLike();
        whoLiked.add(new String(name));

    }

    //effects: stamopa chi ha messo like al post
    public void wl(){
        for(int x = 0; x < whoLiked.size(); x++){
            System.out.println(whoLiked.get(x));
        }
    }
    //effects: aggiunge 1 like al post
    public void addLike(){
        this.numLike++;
    }
    //effects: ritorna il codice id
    //return: codiceIdentificativo
    public int getCi(){
        return codiceIdentificativo;
    }

    //effects: ritorna la categoria
    //return: categoria
    public String getCate(){
        return categoria;
    }

    //effects: chiama il metodo toString ridefinito per stampare tutti i contenuti
    public String display(){
        return this.toString();
    }

    //effects: analoga a wl
    public void stampaWhoLiked(){
        for(int sp = 0; sp < whoLiked.size(); sp++){
            System.out.println(whoLiked.get(sp));
        }
    }

    //effects: definisce il metodo compare per comparare i vari dati tra di loro
    // nel nostro caso in base ai like in ordine crescente
    @Override
    public int compareTo(Object o){

        if(o!=null){
            Data myObject = (Data)o;
            if(numLike > myObject.numLike){
                return -1;
            }else{
                if(numLike == myObject.numLike){
                    return 0;
                }
            }
        }

        return 1;
    }

    //effects: ridefinisce tooString per stampare completamente il dato
    @Override
    public String toString(){
        return "Autore: "+autore+"Testo: "+text+" numero like: "+numLike+" categoria: "+categoria;
    }

}
