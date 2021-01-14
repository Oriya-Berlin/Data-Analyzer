package Classes;

public class Frame {

    private String DBName;
    private String DBType;
    private String address;
    private boolean isConnected;

    public Frame(String DBName, String DBType, String address) {
        this.DBName = DBName;
        this.DBType = DBType;
        this.address = address;
    }
}
