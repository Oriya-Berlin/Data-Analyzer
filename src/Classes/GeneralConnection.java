package Classes;

import java.sql.SQLException;

public class GeneralConnection {

    private String DbType;
    private boolean isConnected;

    public GeneralConnection(String dbType, boolean isConnected) {
        this.DbType = dbType;
        this.isConnected = isConnected;
    }

    public GeneralConnection() {

    }

    public String getDbType() {
        return DbType;
    }

    public void setDbType(String dbType) {
        DbType = dbType;
    }

    public boolean isConnected() throws SQLException {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
