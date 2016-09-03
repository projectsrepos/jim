/*
 *
 *
 */

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @version 1.0
 */
public class MongoDbTest {
    private MongoClient client;
    private DB db;

    //@Before
    public void init() {
        try {
            MongoCredential credential =
                    MongoCredential.createScramSha1Credential("mongo", "admin", "im2016".toCharArray());
            ServerAddress serverAddress = new ServerAddress("172.16.0.123", 27017);
            client = new MongoClient(serverAddress, Arrays.asList(credential));
            db = client.getDB("im_message");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    //@After
    public void destroy() {
        client.close();
    }

    //@Test xxx
    public void testQuery() {
        DBCollection collection = db.getCollection("msg");
        Iterator<DBObject> iterator = collection.find().limit(10).iterator();
        while (iterator.hasNext()) {
            DBObject next = iterator.next();
            next.put("fromId", "642693");
            collection.save(next);
        }
    }
}
