package mongodb;

import javax.swing.CellEditor;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import products.CellPhone;
import products.Product;
import products.Television;

public class MongoInstance {
	private static MongoInstance instance = null;

	protected MongoInstance() {
		// Exists only to defeat instantiation.
	}

	private static MongoClient mongoClient;
	private static MongoDatabase database;

	public static MongoInstance getInstance() {
		// Creating a Mongo client
		if (instance == null) {
			instance = new MongoInstance();
			mongoClient = new MongoClient("localhost", 27017);

			// Creating Credentials
			MongoCredential credential;
			credential = MongoCredential.createCredential("sampleUser", "myDb", "password".toCharArray());
			System.out.println("Connected to the database successfully");

			// Accessing the database
			database = mongoClient.getDatabase("myDb");
			System.out.println("Credentials ::" + credential);
			if (database.getCollection("sampleCollection") == null) {
				database.createCollection("sampleCollection");
				System.out.println("Collection created successfully");

			}

		}
		return instance;

	}

	public void InsertDocument(Product product) {

		MongoCollection<Document> collection = database.getCollection("sampleCollection");

		Document document = new Document("title", "Product").append("id", product.getpId())
				.append("brand", product.getpBrand()).append("model", product.getpModel());
		if (product instanceof CellPhone) {
			CellPhone cp = (CellPhone) product;
			document.append("ram_size", cp.getRamSize()).append("storage_size", cp.getStorageSize());

		} else if (product instanceof Television) {
			Television tv = (Television) product;
			document.append("screen_size", tv.getScreenSize()).append("resolution", tv.getResolution());

		}

		collection.insertOne(document);
		System.out.println("Document inserted successfully");
	}

}
