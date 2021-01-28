package Classes;

import com.mysql.jdbc.ResultSetImpl;
import java.sql.*;



public class MySQL extends GeneralConnection{

    private String USERNAME;
    private String PASSWORD;
    private String connectionString;
    private Connection connection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;
    private String DbType;
    private Boolean isConnected;



    public MySQL(String connectionName, String connectionString, String username, String password) throws SQLException {

        super(connectionName);
        this.USERNAME = username;
        this.PASSWORD = password;
        this.connectionString = connectionString;
        this.DbType = "MySQL";


        try
        {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + connectionString, username, password);
            this.statement = connection.createStatement(ResultSetImpl.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.resultSet = null;
            this.isConnected = true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



    @Override
    public String getConnectionString() {
        return connectionString;
    }

    @Override
    public String getDbType() {
        return DbType;
    }

    @Override
    public ResultSet setCommandAndGetResultSet(String command) throws SQLException {
        this.resultSet = this.statement.executeQuery(command);
        return this.resultSet;
    }


    public void KillConnection() throws SQLException {
        this.connection.close();
    }


    @Override
    public Boolean getIsConnected() throws SQLException {
        if(!this.connection.isClosed())
            return true;
        return false;
    }


}
