package Classes;

import java.util.HashMap;

public class ConnectionsRegistry {

    public static ConnectionsRegistry instance = null;
    HashMap<String,GeneralConnection> items;


    private ConnectionsRegistry(){
        items = new HashMap<>();
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


    public GeneralConnection get(String key){
        return items.get(key);
    }


    public void set(String key, GeneralConnection value){
        items.put(key, value);
    }


    public HashMap<String,GeneralConnection> getItems(){
        return items;
    }

}
