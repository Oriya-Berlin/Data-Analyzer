package Classes;

public class GeneralConnection {

    private String DbType;
    private boolean isConnected;

    public GeneralConnection(String dbType, boolean isConnected) {
        DbType = dbType;
        this.isConnected = isConnected;
    }

    public String getDbType() {
        return DbType;
    }

    public void setDbType(String dbType) {
        DbType = dbType;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
