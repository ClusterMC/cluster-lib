package org.clustermc.lib.utils.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bukkit.configuration.file.FileConfiguration;

public class MongoDB {
	private MongoClient client;
	private MongoDatabase collections;
	
	private int port;
	private String host;
	private String database;
	private String user;
	private String password;
	
	public MongoDB(FileConfiguration file) {
		port = file.getInt("port");
		host = file.getString("host");
		database = file.getString("database");
		user = file.getString("user");
		password = file.getString("password");
	}
	
	public void open() {
		String path = "mongodb://" + user + ":" + password + "@" + host + ":" + port;
		client = new MongoClient(new MongoClientURI(path));
		collections = client.getDatabase(database);
	}

    public MongoClient getClient() {return this.client;}

    public MongoDatabase getCollections() {return this.collections;}
}
