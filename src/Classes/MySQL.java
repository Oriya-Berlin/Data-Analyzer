package Classes;

import com.mysql.jdbc.ResultSetImpl;
import java.sql.*;


public class MySQL {

    private String USERNAME;
    private String PASSWORD;
    private String connectionString;
    private Connection connection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;


    public MySQL(String connectionString, String username, String password){

        this.USERNAME = username;
        this.PASSWORD = password;
        this.connectionString = connectionString;

        try
        {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + connectionString, username, password);
            this.statement = connection.createStatement(ResultSetImpl.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.resultSet = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public ResultSet setCommandAndGetResultSet(String command) throws SQLException {
        this.resultSet = this.statement.executeQuery(command);
        return this.resultSet;
    }


    public void KillConnection() throws SQLException {
        this.connection.close();
    }


    public boolean isConnected() throws SQLException {
        if(!this.connection.isClosed())
            return true;
        return false;
    }


}
