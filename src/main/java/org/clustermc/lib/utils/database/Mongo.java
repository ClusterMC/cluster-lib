package org.clustermc.lib.utils.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bukkit.configuration.file.FileConfiguration;

public class Mongo {
	private MongoClient client;
	//private final Map<String, MongoDatabase> databases = new HashMap<>();
	private MongoDatabase database;
	
	private int port;
	private String host;
	private String user;
	private String password;
	private String db;
	
	public Mongo(FileConfiguration file) {
		port = file.getInt("port");
		host = file.getString("host");
		user = file.getString("user");
		password = file.getString("password");
		db = file.getString("database");
	}
	
	public void open() {
		String path = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + db;
		client = new MongoClient(new MongoClientURI(path));
		database = client.getDatabase(db);
	}

    public MongoClient getClient() {return this.client;}

    public MongoDatabase getDatabase() {
		return database;
		/*
		name = name.toLowerCase();
		if(!databases.containsKey(name)){
			return databases.put(name, client.getDatabase(name));
		}
		return databases.get(name);
		*/
	}
}
