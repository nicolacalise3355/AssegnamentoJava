import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.Collections;

import java.util.*;

import jdk.jfr.ContentType;



public class NewAr<E extends Data> implements DataBoard<Data>{   // la classe NewAr estende arraylist per prenderne i metodi 
    //OVERVIEW: la classe NewAr é un estenzione di ArrayList che permette di implementare dei nuovi metodi ma usare quelli 
    //principali di ArrayList, questa struttura é usata per rappresentare un "utente" con i dati principali e le cose che può fare

    
    /*
     Invariante NewAr
     Utente e Password devono essere validi e non nulli
     I contenuti sono ordinati in base ai Like
     Ogni contenuto ha la propria categoria
     I valori contenuti nelle liste degli amici e categorie devono essere validi
    */
    
    
    private final String utente;  //stringa per il nome dell'utente
    private final String password; //codice password per accedere ai dati e modificarli
    ArrayList<Data> contents; //arraylist di DATA che serve per creare i contenuti
    ArrayList<String> amici; //arraylist di stringhe per inserire gli amici
    ArrayList<String> categorie; //arraylist di categorie che l'utente si crea
    ArrayList<Category> catAmici; //lista delle categorie con all'interno gli amici che vogliamo le possano visualizzare
    Control controllo; //serve per sfruttare la classe dei controlli

        public NewAr(final String name, final String cod){  // viene inizializzata inserendo il nome dell'utente e la password
            //Inizializziamo tutti i dati principali
            this.utente = name;
            this.password = cod;
            this.contents = new ArrayList<Data>();
            this.amici = new ArrayList<String>();
            this.categorie = new ArrayList<String>();
            this.catAmici = new ArrayList<Category>();
            this.controllo = new Control();
        }

        //effect: ritorna il nome dell'utente
        //return: this.utente 
        public String getUtente(){ 
            return this.utente;
        }
        //effect: ritorna la password dell'utente
        //return: password
        public String getPassword(){
            return this.password;
        }

        //effects: controlla se la password é quella corretta
        //throw: lancia un eccezione se la password é sbagliata
        public void checkPassword(final String p) throws FailLogin{
            if(!p.equals(password)){
                System.out.println("Errore");
                throw new FailLogin("Password Sbagliata");
            }
        }

        //effects: va a creare uan nuova categoria 
        //aggiungendola sia alla lista unica di categorie sia all'arraylist di tipo category
        //throw: lancia un eccezione se la password é sbagliata o se la cat é gia esistete
        public void CreateCategory(final String cat, final String pssw) throws FailLogin, InternalError{
            this.checkPassword(pssw);
            if(controllo.findCategory(cat, categorie) ==  true){
                throw new InternalError("Errore: Categoria gia esistente!");
            }
            this.put(pssw,new ArrayList<String>(),cat);

        }

        //effects: va a rimuovere una categoria 
        // sia dalla lista categorie sia dall'arraylist category
        //throw: lancia un eccezione se la password é sbagliata
        public void removeCategory(final String cat, final String pssw) throws FailLogin, InternalError{
            this.checkPassword(pssw);

            if(controllo.findCategory(cat, categorie) ==  false){
                throw new InternalError("Errore: Categoria non presente nella lista!");
            }
            
            final Iterator<String> i = categorie.iterator();
            while(i.hasNext()){
                final String s = i.next();
                if(s.equals(cat)){
                    i.remove();
                }

            }
            
            final Iterator<Category> ic = catAmici.iterator();
            while(ic.hasNext()){
                final Category s2 = ic.next();
                if(cat.equals(s2.getName())){
                    ic.remove();
                }

            }

        }

        //effetcs: va ad aggiungere all'arraylist catFriends un amico e lo inserisce in una categoria che può vedere
        //throw: lancia un eccezione se la password é sbagliata
        public void addFriend(final String cate, final String passw, final String friend) throws FailLogin, InternalError{
            this.checkPassword(passw);
            if(controllo.findCategory(cate, categorie) ==  false){
                throw new InternalError("Errore: Categoria non presente nella lista!");
            }

            this.amici.add(new String(friend));
            for(int af = 0; af < catAmici.size(); af++){
                if(cate.equals(catAmici.get(af).getName())){
                    catAmici.get(af).addF(friend);
                }
            }
        }

        //effects: rimuove un amico dalla lista di amici e dalle categorie con gli amici
        //throw: lancia un eccezione se la password é sbagliata o se la cat non esiste 
        public void removeFriend(final String category, final String passw, final String friend) throws FailLogin, InternalError{

            this.checkPassword(passw);
            if(controllo.findCategory(category, categorie) ==  false){
                throw new InternalError("Errore: Categoria non presente nella lista!");
            }
            
            final Iterator<String> a = amici.iterator();
            while(a.hasNext()){
                final String s = a.next();
                if(s.equals(friend)){
                    a.remove();
                }

            }
            
            for(int vc = 0; vc < catAmici.size(); vc++){
                if(category.equals(catAmici.get(vc).getName())){
                    catAmici.get(vc).remFriend(friend);
                }
            }
           

        }



        //effects: stampa tutti gli amici di una determinata categoria
        public void sf(final String cat){
            System.out.println("categoria: "+cat);
            for(int sf = 0; sf < catAmici.size(); sf++){
                if(cat.equals(catAmici.get(sf).getName())){
                    catAmici.get(sf).stampaFriends();
                }
            }
        }
        //effetcs: stampa tutti i nomi delle categorie che possono avere delle liste di amici 
        public void getCatAmici(){
            for(int ca = 0; ca < catAmici.size(); ca++){
                System.out.println(catAmici.get(ca).getName());
            }
        }

        
        //effects: aggiunge una nuova categoria alle liste dedicate
        //throw: lancia un eccezione se la password é sbagliata
        //return: true se la categoria viene inserita

        public boolean put(final String passw,final List dato,final String cate) throws FailLogin{
            this.checkPassword(passw);
            this.categorie.add(new String(cate));
            this.catAmici.add(new Category(cate));
            return true;

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
        
   
        //effects: funzione intermedia che permettere di scegliere a quale dato dobbiamo aggiungere un like
        //throw: se il dato non viene tovato lancia un eccezione
        public void like(final int codice, final String friend) throws DataNotFound{
            if(controllo.findData(codice, contents) == false){
                throw new DataNotFound("Errore: dato non trovato");
            }
            for(int lik = 0; lik < contents.size(); lik++){
                if(codice == contents.get(lik).getCi()){
                    insertLike(friend,contents.get(lik));
                }
            }
        }

        //effects: aggiungi un like al dato che abbiamo scelto in precedenza
        public void insertLike(String friend, Data dato){
            final Data tmp = dato;
            tmp.addPeopleWhoLiked(friend);
            
        }

        //effetcs: sceglie quale é l'elemento che andremo ad eliminare testando la password con try/catch
         //throw: lancia un eccezione se il dato non viene trovato
        public void removeContent(final String passw, final int codiceI) throws DataNotFound{

            if(controllo.findData(codiceI, contents) == false){
                throw new DataNotFound("Errore: dato non trovato");
            }

            for(int rc = 0; rc < contents.size(); rc++){
                if(codiceI == contents.get(rc).getCi()){
                    try{
                    remove(passw, contents.get(rc));
                    }catch(FailLogin e){
                        System.out.println(e);
                    }
                }
            }
        }
        
        //effetcs: elimina il dato scelto
        //throw: lancia un eccezione se la password é sbagliata
        //return: dato aggiornato
        public Data remove(final String passw, final Data dato) throws FailLogin{
            this.checkPassword(passw);
            final Data tmp = dato;
            for(int vada = 0 ; vada < contents.size(); vada++){
                if(tmp.getCi() == contents.get(vada).getCi()){
                    contents.remove(vada);
                }
            }
            return tmp;
        }
        

        //effects: restituisce una lista di data di una certa categoria
        //throw: lancia un eccezione se la password é sbagliata
        //return: lista con i data di quella categoria che abbiamo scelto
        public List<Data> getDataCategory(String passw, String category) throws FailLogin{
            this.checkPassword(passw);

            ArrayList<Data> catData = new ArrayList<Data>();

            for(int gdc = 0; gdc < contents.size(); gdc++){
                if(category.equals(contents.get(gdc).getCate())){
                    catData.add(contents.get(gdc));
                }
            }
            return catData;
        }

        //effects: crea un iteratore con i data visibili da un amico specifico
        //return: un iteratore avente i data di un amico scelto (non modificabile)
        public Iterator<Data> getFriendIterator(String friend){
            ArrayList<Data> friendsPost = new ArrayList<Data>();
            ArrayList<String> amcat = new ArrayList<String>();

                    for(int cam = 0; cam < catAmici.size(); cam++){
                        for(int cam2 = 0; cam2 < catAmici.get(cam).friends.size(); cam2++){
                            if(friend.equals(catAmici.get(cam).friends.get(cam2))){
                                amcat.add(new String(catAmici.get(cam).getName()));
                            }
                        }
                    }

                    for(int insf = 0; insf < contents.size(); insf++){
                        for(int vc = 0; vc < amcat.size(); vc++){
                            if(contents.get(insf).getCate().equals(amcat.get(vc))){
                                friendsPost.add(contents.get(insf));
                            }
                        }
                    }
                
                    List<Data> nonModificabile = Collections.unmodifiableList(friendsPost);
                    return nonModificabile.iterator();

        }

        //effects: crea un iteratore che restituisce tutti i contenuti ordinati per like
        //throw: FailLogin se login sbagliato
        //return: restituisce l'iteratore non modificabile
        public Iterator<Data> getIterator(String passw) throws FailLogin{
            this.checkPassword(passw);
            ArrayList<Data> tutto = new ArrayList<Data>();
            for(int insc = 0; insc < contents.size(); insc++){
                tutto.add(contents.get(insc));
            }

            Collections.sort(tutto);
            List<Data> finale = Collections.unmodifiableList(tutto);
            return finale.iterator();

        }

        
        
        //effects: aggiunge i contenuti alla lista di DATA insieme alle categorie alla lista di categorie
        //modify: contents
        //throws: se esiste gia un elemtno creato con lo stesso codice allora si lancia un eccezione 
        public void addContent(final String text, final String aut, final NewAr ogg,final String cat, final int coid) throws DataDuplicate{
            
            if(controllo.findData(coid, contents) == true){
                throw new DataDuplicate("Errore: dato già esistente");
            }

            if(controllo.findCategory(cat, categorie) ==  false){
                this.categorie.add(new String(cat));
                this.catAmici.add(new Category(cat));
            }

            ogg.contents.add(new Data(text,aut,cat,coid));



        }
    
    //effects: stampa tutti i contenuti
    public void display(){
        for(int d = 0; d < contents.size(); d++){
            System.out.println(contents.get(d));
        }
    }
        


} 
