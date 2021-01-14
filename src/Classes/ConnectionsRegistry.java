package Classes;

import java.util.HashMap;

public class ConnectionsRegistry <K,V>{

    public static ConnectionsRegistry instance = null;
    HashMap<K,V> items;


    private ConnectionsRegistry(){
        items = new HashMap<K, V>();
    }


    public static ConnectionsRegistry getInstance(){

        if(instance == null)
        {
            synchronized (ConnectionsRegistry.class)
            {
                if(instance == null)
                    instance = new ConnectionsRegistry();
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
