package org.sdt_practical_4.DB;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

public class Mongo {

    public static void main(String[] args) throws UnknownHostException{



        MongoClientURI uri  = new MongoClientURI("mongodb://localhost:27017");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase("railwayTransportation");


        MongoCollection<Document> receiptDB = db.getCollection("receipts");

        List<Document> seedData = new ArrayList<Document>();

        seedData.add(new Document("decade", "1970s")
                .append("artist", "Debby Boone")
                .append("song", "You Light Up My Life")
                .append("weeksAtOne", 10)
        );

        receiptDB.insertMany(seedData);


        Document updateQuery = new Document("song", "One Sweet Day");
        receiptDB.updateOne(updateQuery, new Document("$set", new Document("artist", "Mariah Carey ft. Boyz II Men")));


        Document findQuery = new Document("weeksAtOne", new Document("$gte",10));
        Document orderBy = new Document("decade", 1);

        MongoCursor<Document> cursor = receiptDB.find(findQuery).sort(orderBy).iterator();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(
                        "In the " + doc.get("decade") + ", " + doc.get("song") +
                                " by " + doc.get("artist") + " topped the charts for " +
                                doc.get("weeksAtOne") + " straight weeks."
                );
            }
        } finally {
            cursor.close();
        }

        client.close();
    }
}
