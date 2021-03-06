package mongoDBApp;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;

public class ConnectToDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoClient mongoclients =  MongoClients.create("mongodb://admin:password@192.168.56.100:27017");
		MongoDatabase dbase = mongoclients.getDatabase("my-db");
		System.out.println("Database Name: "+dbase.getName());
		
		try{
		dbase.createCollection("users");
		System.out.println("Collection users created");
		} catch(MongoCommandException ex) {
			dbase.getCollection("users").drop();
			System.out.println("Collection dropped");
		}
	
		MongoCollection<Document> coll = dbase.getCollection("users");
		ArrayList<Document> docs = new ArrayList<Document>();
		
		Document doc1 = new Document();
		doc1.append("_id", 1);
		doc1.append("_firstName", "Sam");
		doc1.append("_lastName",	"Shaw");		
		docs.add(doc1);
		
		Document doc2 = new Document();
		doc2.append("_id", 2);
		doc2.append("_firstName", "Kiran");
		doc2.append("_lastName",	"Kundu");		
		docs.add(doc2);
		
		Document doc3 = new Document();
		doc3.append("_id", 3);
		doc3.append("_firstName", "Yogesh");
		doc3.append("_lastName","Ahuja");		
		docs.add(doc3);
		
		coll.insertMany(docs); // Will create collection also if not exists
		
		// Retrieving the documents 
		System.out.println("Retrieving the documents");
        try (MongoCursor < Document > cur = coll.find().iterator()) {

            while (cur.hasNext()) {

                Document doc = cur.next();
                ArrayList<Object> users = new ArrayList <> (doc.values());

                System.out.printf("%s: %s%n", users.get(1), users.get(2));
            }
        }
        
        //Update document
        coll.updateOne(new Document("_firstName", "Yogesh"), new Document("$set", new Document("_lastName","Saluja")));
        
     // Retrieving the documents after update
        System.out.println("Retrieving the documents after update");
        try (MongoCursor < Document > cur = coll.find().iterator()) {

            while (cur.hasNext()) {

                Document doc = cur.next();
                ArrayList<Object> users = new ArrayList <> (doc.values());

                System.out.printf("%s %s: %s%n", users.get(0), users.get(1), users.get(2));
            }
        }
        
        //Deleting the document
        coll.deleteOne(Filters.eq("_id", 2));
        System.out.println("Retrieving the documents after delete");
        try (MongoCursor < Document > cur = coll.find().iterator()) {

            while (cur.hasNext()) {

                Document doc = cur.next();
                ArrayList<Object> users = new ArrayList <> (doc.values());

                System.out.printf("%s %s: %s%n", users.get(0), users.get(1), users.get(2));
            }
        }
	}
}
