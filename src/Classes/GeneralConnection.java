package Classes;


// TODO: maybe make it abstract class, and then override it
public class GeneralConnection {

    private String connectionName;


    public GeneralConnection(String connectionName) {
        this.connectionName = connectionName;
    }


    public String getConnectionName(){
        return this.connectionName;
    }


    @Override
    public String toString() {
        return this.connectionName;
    }

}
