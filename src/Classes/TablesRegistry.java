package Classes;

import java.util.HashMap;

public class TablesRegistry <K,V>{

    private static TablesRegistry instance = null;
    HashMap<K,V> items = null;


    private TablesRegistry(){
        items = new HashMap<K, V>();
    }


    public static TablesRegistry getInstance(){

        if(instance == null)
        {
            synchronized (TablesRegistry.class)
            {
                if(instance == null)
                    instance = new TablesRegistry();
            }
        }
        return instance;
    }


    public V get(K key){
        return items.get(key);
    }


    public void set(K key, V value){
        items.put(key, value);
    }



}
