import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

public class Start{


   public static void main(String[] args){
    Start.test1();
    System.out.println("----------------------------------------------------------------");
    Start.test2();
    
    }

    //test numero 1

    public static void test1(){
    // dichiariamo costanti e valori

    //dichiarazione delle password
    String passwordNicola = "nnnnn1999"; 
    String passwordLorenzo = "lorenzo123";
    String passwordDavide = "informatica2019";
    //dichiarazione dei nomi utente
    String utente1 = "nicola";
    String utente2 = "lorenzo";
    String utente3 = "davide";
    //dichiarazione delle categorie
    String categoria1 = "video";
    String categoria2 = "foto";
    String categoria3 = "gif";
    String categoria4 = "sticker";
    //dichiarazione codice identificativi dei contenuti
    int ci1 = 0;
    int ci2 = 1;
    int ci3 = 2;
    int ci4 = 3;
    int ci5 = 4;


        System.out.println("Inizio programma 1");
        //controlliamo subito se i nomi per gli utenti vanno bene
        try{
        Control.dataControl(utente1);
            Control.nullControl(utente1);
        Control.dataControl(utente2);
            Control.nullControl(utente2);
        Control.dataControl(utente3);
            Control.dataControl(utente3);
        }catch(InvalidData e){
            System.out.println(e);
        }
        
        try{
            Control.nullControl(categoria1);
            Control.nullControl(categoria2);
            Control.nullControl(categoria3);
            Control.nullControl(categoria4);
        }catch(InvalidData e){
            System.out.println(e);
        }

        //definiamo 3 utenti
        NewAr<Data> nicola = new NewAr<Data>(utente1,passwordNicola);
        NewAr<Data> lorenzo = new NewAr<Data>(utente2,passwordLorenzo);
        NewAr<Data> davide = new NewAr<Data>(utente3,passwordDavide);
        //creiamo 3 categorie per nicola
        System.out.println("---definiamo 3 categorie e una che genera un errore sbagliando la password e uno con una categoria gia creata---");
        //
        try{
        nicola.CreateCategory(categoria1, passwordNicola);
        nicola.CreateCategory(categoria2, passwordNicola);
        nicola.CreateCategory(categoria3, passwordNicola);
        nicola.CreateCategory(categoria4, passwordLorenzo);
        }catch(FailLogin e){
            System.out.println(e);
        }catch(InternalError e2){
            System.out.println(e2);
        } 

        try{
            nicola.CreateCategory(categoria1, passwordNicola);
        }catch(FailLogin e){
            System.out.println(e);
        }catch(InternalError e2){
            System.out.println(e2);
        } 

        System.out.println("---abbiamo constatato che funziona l'eccezione del faillogin quindi non la proveremo piu---");
        //creiamo 2 contenuti a nicola
        System.out.println("---aggiungiamo 2 contenuti---");
        //
        try{
        nicola.addContent("testoesempio1",utente1,nicola,categoria1,ci1);
        nicola.addContent("testcdisjvwevij",utente1,nicola,categoria3,ci2);
        }catch(DataDuplicate e){
            System.out.println(e);
        }
        System.out.println("---Aggiungiamo un contenuto gia esistente per vedere se lancia un eccezione---");
        try{
            nicola.addContent("test", utente1, nicola, categoria1, ci1);
        }catch(DataDuplicate e){
            System.out.println(e);
        }

        //stampa di nicola

        System.out.println(nicola.getUtente());

        //stampa dati inseriti
        System.out.println("---Stampiamo i contenuti che abbiamo inserito---");
        //
        for(int x = 0; x < nicola.contents.size();x++){
            System.out.println(nicola.contents.get(x));
        }

        //stampiamo le categorie
        System.out.println("---stampiamo le categorie---");
        //
        nicola.getCatAmici();
        //rimuoviamo una categoria e dopo andiamo a stampare di nuovo le categorie
        System.out.println("---proviamo anche a rimuovere una categoria che non esiste---");
        System.out.println("---rimuoviamo una categoria e dopo stampiamo di nuovo tutto per vedere se abbiamo eliminato effettivamente---");
        //
        try{
        nicola.removeCategory(categoria1,passwordNicola);
        nicola.removeCategory("videostrani", passwordNicola);
        }catch(FailLogin e){
            System.out.println(e);
        }catch(InternalError e2){
            System.out.println(e2);
        }
        nicola.getCatAmici();
        
        //inseriamo amici per la categoria che vogliamo
        System.out.println("---inseriamo degli amici , che possono vedere determinate categorie---");
        //
        try{
        nicola.addFriend(categoria2,passwordNicola,"garosi");
        nicola.addFriend(categoria2,passwordNicola,"ariele");
        nicola.addFriend(categoria3,passwordNicola,"mario");
        nicola.addFriend(categoria3,passwordNicola,"francesco");
        }catch(FailLogin e){
            System.out.println(e);
        }catch(InternalError e){
            System.out.println(e);
        }
        // stampiamo la categoria con i relativi amici
        System.out.println("---stampiamo le categorie con i relativi amici---");
        //
        nicola.sf(categoria2);
        nicola.sf(categoria3);

        // aggiungiamo un like
        System.out.println("---aggiungiamo dei like e testiamo l'eccezione che non trova un dato---");
        //
        try{
        nicola.like(ci1, "garosi");
        nicola.like(ci2, "mario");
        nicola.like(ci2, "francesco");
        nicola.like(999, "piermario");
        }catch(DataNotFound e){
            System.out.println(e);
        }
        // stampiamo di nuovo per vedere se il like é stato aggiunto
        //e stampiamo la lista di chi ha messo like
        System.out.println("---stampiamo di nuovo per vedere se il like é stato aggiunnto e vediamo chi lo ha messo---");
        //
        for(int x = 0; x < nicola.contents.size();x++){
            System.out.println(nicola.contents.get(x));
            nicola.contents.get(x).stampaWhoLiked();
        }

        //rimuoviamo un amico e ri stampiamo tutto
        System.out.println("---rimuoviamo un amico e verifichiamo---");
        //
        try{
        nicola.removeFriend(categoria2,passwordNicola,"ariele");
        }catch(FailLogin e){
            System.out.println(e);
        }catch(InternalError e){
            System.out.println(e);
        }
        nicola.sf(categoria2);

        //rimuoviamo un contenuto
        System.out.println("---rimuoviamo un contenuto e stampiamo per verificare---");
        //
        try{
        nicola.removeContent(passwordNicola,ci1);
        }catch(DataNotFound e){
            System.out.println(e);
        }

        
        //stampiamo tutti i cont

        //
        for(int x = 0; x < nicola.contents.size();x++){
            System.out.println(nicola.contents.get(x));
        }

        //testiamo List e iterator

        System.out.println("------------");

        //
        System.out.println("---aggiungiamo dei contenuti per test successivi---");
        //
        try{
        nicola.addContent("test per la list",utente1,nicola,categoria3,ci4);
        nicola.addContent("test cge bon exsce",utente1,nicola,categoria2,ci5);
        }catch(DataDuplicate e){
            System.out.println(e);
        }

        //testiamo la lista
        System.out.println("---stampiamo la lista generata con la funzione getdatacategory e stampiamo---");
        //
        try{
            List<Data> test;
        test = nicola.getDataCategory(passwordNicola, categoria3);
        for(int tt = 0; tt < test.size(); tt++){
            System.out.println(test.get(tt));
        }
        }catch(FailLogin e){
            System.out.println(e);
        }




        System.out.println("------------");
        System.out.println("---testiamo i due iteratori---");

        Iterator<Data> itr = nicola.getFriendIterator("mario");
        while(itr.hasNext()){
            Data el = itr.next();
            System.out.println(el.display());
        }

        System.out.println("------------");
        try{
        nicola.like(ci4, "francesco");
        }catch(DataNotFound e){
            System.out.println(e);
        }

        try{
        Iterator<Data> itrOrd = nicola.getIterator(passwordNicola);
        while(itrOrd.hasNext()){
            Data el2 = itrOrd.next();
            System.out.println(el2.display());
        }
        }catch(FailLogin e){
            System.out.println(e);
        }




    }

    //-------------------------------------------
    //test2

    public static void test2(){

         // dichiariamo costanti e valori

    //dichiarazione delle password
    String pinGiulia = "skfnlwd22";

    //dichiarazione dei nomi utente
    String utente4 = "maria";
    String utente5 = "francesca";
    String utente6 = "giulia";
    //dichiarazione delle categorie
    String categoria5 = "video";
    String categoria6 = "foto";
    String categoria7 = "gif";
    String categoria8 = "sticker";

    //dichiarazione codice identificativi dei contenuti

    int codice1 = 1;
    int codice2 = 2;
    int codice3 = 3;


        System.out.println("Inizio programma 2");
        
        //controlliamo subito se i nomi per gli utenti vanno bene
        try{
            Control.dataControl(utente4);
            Control.nullControl(utente4);
            Control.dataControl(utente5);
            Control.nullControl(utente5);
            Control.dataControl(utente6);
            Control.dataControl(utente6);
        }catch(InvalidData e){
            System.out.println(e);
        }
        
        try{
            Control.nullControl(categoria5);
            Control.nullControl(categoria6);
            Control.nullControl(categoria7);
            Control.nullControl(categoria8);
        }catch(InvalidData e){
            System.out.println(e);
        }


        NewAr2<Data> giulia = new NewAr2<Data>(utente4,pinGiulia);
        System.out.println(giulia.getPassword());

        //andiamo ad aggiungere un contenuto all'hashmap chiamando la funzione che fa da "intermediaria"
  
        giulia.addContent("Testo test 2 esempio", utente6, categoria5, codice1);
        giulia.addContent("immagine di un gattino", utente6, categoria6, codice2);
        giulia.addContent("memememememememememememem", utente6, categoria6, codice3);
    
        
        //stampiamo tutti i contenuti per visualizzare

        giulia.display();

        //aggiungiamo delle categorie

        System.out.println("---Aggiungiamo alle categorie gif, testando un errore di login---");
        try{
            giulia.CreateCategory(categoria7,pinGiulia);
            giulia.CreateCategory("categoriaexample", "125");
        }catch(FailLogin e){
            System.out.println(e);
        }catch(InternalError e){
            System.out.println(e);
        }

        //stampiamo le categorie che abbiamo inserito 
        System.out.println("---stampiamo la categoria inserita---");
        giulia.getCategory();
        
        //rimuoviamo una categoria e ri stampiamo
        System.out.println("---Rimuoviamo una categoria, ad esempio gif , proviamo a rimuovere una cat e non esiste e dopo stampiamo---");
        try{
            giulia.removeCategory(categoria7,pinGiulia);
            giulia.removeCategory("categorianotex",pinGiulia);
        }catch(FailLogin e){
            System.out.println(e);
        }catch(InternalError e){
            System.out.println(e);
        }
        giulia.getCategory();
        
        //inseriamo degli amici per le 2 categorie e gli stampiamo
        System.out.println("---inseriamo 3 amici, 2 per una categorie e 1 per l'altra e stampiamo---");
        try{
            giulia.addFriend(categoria5,pinGiulia,"matilde");
            giulia.addFriend(categoria5,pinGiulia,"linda");
            giulia.addFriend(categoria6,pinGiulia,"alessia");
            
        }catch(FailLogin e){
            System.out.println(e);
        }catch(InternalError e){
            System.out.println(e);
        }
        
        giulia.getCatAmici();
        
        //andiamo a rimuovere un amico e stampiamo
        System.out.println("---rimuoviamo matilde dagli amici e stampiamo---");
        try{
            giulia.removeFriend(categoria5,pinGiulia,"matilde");
        }catch(FailLogin e){
            System.out.println(e);
        }catch(InternalError e){
            System.out.println(e);
        }
        giulia.getCatAmici();
        
        //stampiamo i contenuti, poi ne elimianmo uno e lo ristampiamo
        System.out.println("---andiamo a stampare di nuovo tutti i contenuti---");
                giulia.display();
        System.out.println("---adesso ne rimuoviamo uno e proviamo a rimuoverne uno che non esiste e stampiamo di nuovo per verificare---");
        try{
        giulia.removeContent(pinGiulia,999);

        }catch(DataNotFound e){
            System.out.println(e);
        }
        try{
            giulia.removeContent(pinGiulia,codice2);
            
        }catch(DataNotFound e){
            System.out.println(e);
        }
        giulia.display();

        //aggiungiamo tre like totali e ri stampiamo i contenuti vedendo anche chi ha messo like
        
        System.out.println("---mettiamo 3 like totali ai post e vediamo chi messo like---");
        
        try{
            giulia.like(codice1, "giuditta");
            giulia.like(codice3, "sara");
            giulia.like(codice3, "martina");
        }catch(DataNotFound e){
            System.out.println(e);
        }
        giulia.displayLiked();
        
        //andiamo a testare iteratori e getCategoryData
        
        System.out.println("---andiamo a testare gli iteratori e una funziona di tipo get implementata da noi---");
                System.out.println("---stampiamo la list di data della categoria che volevamo---");
        //stampiamo la lista della funzione getdatacategory
        try{
            List<Data> testList;
            testList = giulia.getDataCategory(pinGiulia,categoria6);
            for(int s = 0; s < testList.size(); s++){
                System.out.println(testList.get(s));
            }
        }catch(FailLogin e){
            System.out.println(e);
        }
        
        //testiamo l'iteratore 1 
        System.out.println("---restituisce un iteratore che da i contenuti ordinati per like---");
        
        
        try{
            Iterator<Data> itrOrd = giulia.getIterator(pinGiulia);
            while(itrOrd.hasNext()){
                Data el2 = itrOrd.next();
                System.out.println(el2.display());
            }
        }catch(FailLogin e){
            System.out.println(e);
        }
        
        //testiamo l'iteratore 2
        System.out.println("---testiamo un iteratore successivo con i dati visibili ad un amico specifico---");
        
        Iterator<Data> itr = giulia.getFriendIterator("linda");
        while(itr.hasNext()){
            Data el = itr.next();
            System.out.println(el.display());
        }
        
    }

}
