package org.clustermc.lib.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connects to and uses a database
 *
 * @author -_Husky_-
 * @author tips48
 * @author ktar5
 */
public class SQL extends Database {
    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;


    /**
     * Creates a new MySQL instance
     *
     * @param hostname
     *            Name of the host
     * @param port
     *            Port number
     * @param database
     *            Database name
     * @param username
     *            Username
     * @param password
     *            Password
     */
    public SQL(String hostname, String port, String database,
               String username, String password) {
        super();
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
    }

    @Override
    public Connection openConnection() throws SQLException,
            ClassNotFoundException {
        if (checkConnection()) {
            return connection;
        }
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"
                        + this.hostname + ":" + this.port + "/" + this.database,
                this.user, this.password);
        connection.setAutoCommit(false);
        return connection;
    }
}

