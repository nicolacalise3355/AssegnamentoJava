import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.Collections;
import java.util.Vector;
import java.util.HashMap;

import java.util.*;

import jdk.jfr.ContentType;



public class NewAr2<E extends Data> implements DataBoard<Data>{   // la classe NewAr2 estende vector per prenderne i metodi 
    //OVERVIEW: la classe NewAr2 é un estenzione di Vector che permette di implementare dei nuovi metodi ma usare quelli 
    //principali di Vector, questa struttura é usata per rappresentare un "utente" con i dati principali e le cose che può fare
    
    /*
     Invariante NewAr2
     Utente e Password devono essere validi e non nulli
     I contenuti sono ordinati in base ai Like
     Ogni contenuto ha assegnato un codice identificativo
     Ogni contenuto ha la propria categoria
     I valori contenuti nelle liste degli amici e categorie devono essere validi
     */

    private final String utente;  //stringa per il nome dell'utente
    private final String password; //codice password per accedere ai dati e modificarli
    HashMap<Integer, Data> contents; //HashMap che rappresenta codice + contenuto
    Vector<Category> catAmici; //lista delle categorie con all'interno gli amici che vogliamo le possano visualizzare
    Control controllo; // serve per sfruttare i controlli

        public NewAr2(final String name, final String cod){  // viene inizializzata inserendo il nome dell'utente e la password
            //Inizializziamo tutti i dati principali
            this.utente = name;
            this.password = mapPassword(cod);
            this.contents = new HashMap<Integer, Data>();
            this.controllo = new Control();
            this.catAmici = new Vector<Category>();
        }

        //effects: prende in input un numero base come password ma tramite una funzione lo modifica per rendere piu sicura
        // la privacy 
        //return: mapped
        public String mapPassword(String base){
            String mapped = base+"aaabbbccc";
            return mapped;
        }

        //effects: ritorna la password
        //return: password
        public String getPassword(){
            return this.password;
        }

        //effects: controlla se la password é quella corretta
        //throw: se la password é sbagliata lancia un eccezione
        public void checkPassword(final String p) throws FailLogin{
            if(!p.equals(password)){
                System.out.println("Errore");
                throw new FailLogin("Password Sbagliata");
            }
        }

        //effects: manda a put i dati per aggiungere una categoria
        //throws: errore se la categoria é gia esistente oppure sbagliamo password, lanciamo un eccezione
        public void CreateCategory(String cat, String pssw) throws InternalError, FailLogin{
            String tmpPassword = mapPassword(pssw);
            this.checkPassword(tmpPassword);

            for(int c = 0; c < catAmici.size(); c++){
            if(cat.equals(catAmici.get(c).getName()) == true){
                throw new InternalError("Errore interno: categoria gia esistente");
            }
            }

            this.put(tmpPassword,new Vector<String>(),cat);

        }

        //effects: stampiamo le categorie inserite da noi
        public void getCategory(){
            for(int gc = 0; gc < catAmici.size(); gc++){
                System.out.println(catAmici.get(gc).getName());
            }
        }

        //effects: aggiunge una nuova categoria alle liste dedicate
        //throw: lancia un eccezione se la password é sbagliata
        //return: true se la categoria viene inserita
        public boolean put(final String passw,final List dato,final String cate) throws FailLogin{
            this.checkPassword(passw);
            this.catAmici.add(new Category(cate));
            return true;

        }
    
    //effects: rimuove una categoria dalla lista
    //throw: lancia un eccezione se sbagliamo il login o non troviamo la categoria
    public void removeCategory(String cat, String pssw) throws InternalError, FailLogin{
        String tmpPassword = mapPassword(pssw);
        this.checkPassword(tmpPassword);
        
        int count = 0;
        
        for(int c = 0; c < catAmici.size(); c++){
            if(cat.equals(catAmici.get(c).getName()) == false){
                count++;
            }
        }
        if(count == catAmici.size()){
            throw new InternalError("Errore interno: Catgeoria non trovata ");
        }
        
        final Iterator<Category> ic = catAmici.iterator();
        while(ic.hasNext()){
            final Category s2 = ic.next();
            if(cat.equals(s2.getName())){
                ic.remove();
            }
            
        }
    }
    
    //effects: aggiunge un amico ad una categoria che abbiamo scelto noi
    //throw: lancia un eccezione se si sbaglia il login, se la categoria non esiste
    public void addFriend(String category, String passw, String friend) throws FailLogin, InternalError{
        
        String tmpPassword = mapPassword(passw);
        this.checkPassword(tmpPassword);
        
        int count = 0;
        
        for(int c = 0; c < catAmici.size(); c++){
            if(category.equals(catAmici.get(c).getName()) == false){
                count++;
            }
        }
        if(count == catAmici.size()){
            throw new InternalError("Errore interno: Catgeoria non trovata ");
        }
        
        for(int af = 0; af < catAmici.size(); af++){
            if(category.equals(catAmici.get(af).getName())){
                catAmici.get(af).addF(friend);
            }
        }
    }
    
    //effects: stampa tutte le categorie con i relativi amici inseriti
    public void getCatAmici(){
        for(int gca = 0; gca < catAmici.size(); gca++){
            System.out.println(catAmici.get(gca).getName()+": ");
            catAmici.get(gca).stampaFriends();
        }
    }
    
    //effects: rimuove un amico dalla lista e da una categoria
    //throw: lancia un eccezione se la categoria non esiste o il login viene sbagliato
    public void removeFriend(String category, String passw, String friend) throws InternalError, FailLogin{
        String tmpPassword = mapPassword(passw);
        this.checkPassword(tmpPassword);
        
        int count = 0;
        
        for(int c = 0; c < catAmici.size(); c++){
            if(category.equals(catAmici.get(c).getName()) == false){
                count++;
            }
        }
        if(count == catAmici.size()){
            throw new InternalError("Errore interno: Catgeoria non trovata ");
        }
        
        for(int vc = 0; vc < catAmici.size(); vc++){
            if(category.equals(catAmici.get(vc).getName())){
                catAmici.get(vc).remFriend(friend);
            }
        }
    }
    
    //effects: fa da tramite per eliminare un contenuto
    //throw: lancia un eccezione se non si trova un dato
    public void removeContent(String passw, int cod) throws DataNotFound{
        
        if(controllo.findData2(cod, contents) == false){
            throw new DataNotFound("Errore: dato non trovato");
        }
        
        Data tmp = new Data("","","",cod);
        try{
        this.remove(passw,tmp);
        }catch(FailLogin e){
            System.out.println(e);
        }
    }

    //effects: rimuove un contenuto dall hashmap
    //throw: lancia un eccezione se sbagliamo il login o se il dato non viene trovato
    //DA AGGIUNGERE ECCEZIONE DATA NOT FOUND
    //return: hashmap aggiornato
    public Data remove(String passw, Data dato) throws FailLogin{
        String tmpPassword = mapPassword(passw);
        this.checkPassword(tmpPassword);
        
        int codice = dato.getCi();
        
        contents.remove(codice);
        return dato;
    }
    
    //effects: funzione intermedia per selezionare correttamente il dato
    //throw: lancia un eccezione se non viene trovato un dato
    public void like(final int codice, final String friend) throws DataNotFound{
        
        if(controllo.findData2(codice, contents) == false){
            throw new DataNotFound("Errore: dato non trovato");
        }
            Data tmp = new Data("","","",codice);
        this.insertLike(friend,tmp);
        
    }
    
    //effects: inserisce un like ad un contenuto
    public void insertLike(String friend, Data dato){
        
        Iterator<Map.Entry<Integer,Data>> it = contents.entrySet().iterator();
        while(it.hasNext()){
            
            Map.Entry<Integer, Data> e = it.next();
            if(e.getKey() == dato.getCi()){
                e.getValue().addPeopleWhoLiked(friend);
            }
        }
        
    }
    
    //effects: restituisce una lista di data di una certa categoria
    //throw: lancia un eccezione se la password é sbagliata
    //return: lista con i data di quella categoria che abbiamo scelto
    public List<Data> getDataCategory(String passw, String category) throws FailLogin{
        String tmpPassword = mapPassword(passw);
        this.checkPassword(tmpPassword);
        
        Vector<Data> dataCat = new Vector<Data>();
        
        Iterator<Map.Entry<Integer,Data>> it = contents.entrySet().iterator();
        while(it.hasNext()){
            
            Map.Entry<Integer, Data> e = it.next();
            if(category.equals(e.getValue().getCate()) == true){
                dataCat.add(e.getValue());
            }
        }
        return dataCat;
    }

    //effects: crea un iteratore che restituisce tutti i contenuti ordinati per like
    //throw: FailLogin se login sbagliato
    //return: restituisce l'iteratore non modificabile
    public Iterator<Data> getIterator(String passw) throws FailLogin{
        String tmpPassword = mapPassword(passw);
        this.checkPassword(tmpPassword);
        
        Vector<Data> tutto = new Vector<Data>();
        
        Iterator<Map.Entry<Integer,Data>> it = contents.entrySet().iterator();
        while(it.hasNext()){
            
            Map.Entry<Integer, Data> e = it.next();
            tutto.add(e.getValue());
        }
        
        Collections.sort(tutto);
        List<Data> finale = Collections.unmodifiableList(tutto);
        return finale.iterator();
        
    }

    //effects: crea un iteratore con i data visibili da un amico specifico
    //return: un iteratore avente i data di un amico scelto (non modificabile)
    public Iterator<Data> getFriendIterator(String friend){
                Vector<Data> dataFriends = new Vector<Data>();
        
        Vector<String> amcat = new Vector<String>();
        
        for(int cam = 0; cam < catAmici.size(); cam++){
            for(int cam2 = 0; cam2 < catAmici.get(cam).friends.size(); cam2++){
                if(friend.equals(catAmici.get(cam).friends.get(cam2))){
                    amcat.add(new String(catAmici.get(cam).getName()));
                }
            }
        }
        
        
        
        Iterator<Map.Entry<Integer,Data>> it = contents.entrySet().iterator();
        while(it.hasNext()){
            
            Map.Entry<Integer, Data> e = it.next();
            for(int vc = 0; vc < amcat.size(); vc++){
                if(e.getValue().getCate().equals(amcat.get(vc))){
                    dataFriends.add(e.getValue());
                }
            }
            }
        
        
        Collections.sort(dataFriends);
        List<Data> finale = Collections.unmodifiableList(dataFriends);
        return finale.iterator();
        
    }

    

        //effects: aggiunge i contenuti all'hashmap di DATA (aggiunge anche le categorie alla lista delle categorie)
        //modify: contents
        //throws: se esiste gia un elemtno creato con lo stesso codice allora si lancia un eccezione 
        public void addContent(String testo, String aut, String cate, int ci){
            Data tmp = new Data(testo,aut,cate,ci);
            int count = 0;

            for(int var = 0; var < catAmici.size(); var++){
            if(cate.equals(catAmici.get(var).getName()) ==  true){
                count++;
            }
            }
        if(count == 0){
            this.catAmici.add(new Category(cate));
        }

            this.contents.put(ci,tmp);

        }
    
    //effects: funziona analoga a display solo che stampa anche le persone che hanno messo like
    public void displayLiked(){
        Iterator<Map.Entry<Integer,Data>> it = contents.entrySet().iterator();
        while(it.hasNext()){
            
            Map.Entry<Integer, Data> e = it.next();
            System.out.println("Codice:"+e.getKey()+" DATO: "+e.getValue());
            e.getValue().stampaWhoLiked();
        }
    }

        //effects: stampa tutti i contenuti che sono stati inseriti
        public void display(){
            Iterator<Map.Entry<Integer,Data>> it = contents.entrySet().iterator();
            while(it.hasNext()){

                Map.Entry<Integer, Data> e = it.next();
                System.out.println("Codice:"+e.getKey()+" DATO: "+e.getValue());
            }

        }
    
    //effects: funzione intermendia che aiuta la funzione "get" grazie all'ausilio del codice per riconoscere di quale contenuto
    //stiamo parlando
    public void getData(final String passw,final int codice){
        for(int gd = 0; gd < contents.size(); gd++){
            if(codice == contents.get(gd).getCi()){
                try{
                    get(passw,contents.get(gd));
                }catch(FailLogin e){
                    System.out.println(e);
                }
            }
        }
    }
    
    //effects: ritorna un contenuto che abbiamo scelto tramite la funzione "getData"
    //throw: lancia un eccezione se la password é sbagliata
    //return: il contenuto di tipo Data
    public Data get(final String passw, final Data dato) throws FailLogin{
        final Data tmp = dato;
        this.checkPassword(passw);
        return tmp;
    }
        


} 
