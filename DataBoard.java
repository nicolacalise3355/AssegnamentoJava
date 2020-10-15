import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.Collections;
import java.util.Vector;
import java.util.HashMap;

/*
  L'interfaccia DataBoard contiene una mancanza:
  Le specifiche dei metodi delle classi NewAr e NewAr2 non dovrebbero
  essere presenti nelle classi stesse, ma in questa classe
  in quanto quei metodi non sono altro che implementazioni di metodi
  di questa interfaccia
*/

public interface DataBoard<E extends Data>{

    //tutte le funzioni di DataBoard sono definite in NewAr e NewAr2
    
    public void CreateCategory(String cat, String pssw) throws FailLogin, InternalError;

    public void removeCategory(String cat, String pssw) throws FailLogin, InternalError;

    public void addFriend(String category, String passw, String friend) throws FailLogin, InternalError;

    public void removeFriend(String category, String passw, String friend) throws FailLogin, InternalError;

    public boolean put(String passw,List dato,String categoria) throws FailLogin;
        
    public Data remove(String passw, Data dato) throws FailLogin;

    public void insertLike(String friend, Data dato);

    public List<Data> getDataCategory(String passw, String category) throws FailLogin;

    public Iterator<Data> getIterator(String passw) throws FailLogin;

    public Iterator<Data> getFriendIterator(String friend);

    public Data get(final String passw, final Data dato) throws FailLogin;

}
