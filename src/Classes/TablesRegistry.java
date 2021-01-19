package Classes;

import java.util.HashMap;

public class TablesRegistry {

    private static TablesRegistry instance = null;
    HashMap<String,AnalyzerTableView> items = null;


    private TablesRegistry(){
        items = new HashMap<>();
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


    public AnalyzerTableView get(String key){
        return items.get(key);
    }


    public void set(String key, AnalyzerTableView value){
        items.put(key, value);
    }


    public void remove(String key){
        items.remove(key);
    }


    public HashMap<String,AnalyzerTableView> getItems(){
        return items;
    }


}
