package Classes;


import java.sql.ResultSet;
import java.sql.SQLException;

// TODO: maybe make it abstract class, and then override it
public abstract class GeneralConnection {

    private String connectionName;


    public GeneralConnection(String connectionName) {
        this.connectionName = connectionName;
    }


    public String getConnectionName(){
        return this.connectionName;
    }

    public String getConnectionString() {
        return null;
    }

    public String getDbType() {
        return null;
    }

    public Boolean getIsConnected() throws SQLException {
        return null;
    }

    public ResultSet setCommandAndGetResultSet(String command) throws SQLException {
        return null;
    }


    @Override
    public String toString() {
        return this.connectionName;
    }

}
