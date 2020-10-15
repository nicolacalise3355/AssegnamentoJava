import java.util.ArrayList;
import java.util.Iterator;

public class Category{
    //OVERVIEW: classe che definisce il tipo categoria
    
    /*
     Invariante Category
     Ogni categoria ha un nome
     Ogni categoria ha una lista di amici che possono vedere i contenuti della categoria
     */

    private String name; //nome della categoria
    ArrayList<String> friends; //lista di amici ai quali sono visibili i post di tipo categoria

        public Category(String n){ // inizializziamo i dati
            this.name = n;
            friends = new ArrayList<String>();
        }

        //effects: stampa la lista di amici a cui é visibile la seguente categoria
        public void stampaFriends(){
            for(int x = 0; x < friends.size(); x++){
                System.out.println(friends.get(x));
            }
        }

        //effects: aggiunge un amico alla lista
        public void addF(String f){
            friends.add(new String(f));
        }

        //effects: rimuove un amico dalla lista
        public void remFriend(String fr){
            Iterator<String> f = friends.iterator();
            while(f.hasNext()){
                String s = f.next();
                if(s.equals(fr)){
                    f.remove();
                }

            }
        }

        //effects: ritorna il nome della categoria
        //return: name
        public String getName(){
            return name;
        }

    


}
