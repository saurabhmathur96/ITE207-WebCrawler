package ite207;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ServerAddress;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;


public class Storage
{
    private MongoClient mongo;
    private MongoCollection<Document> collection;
    public Storage( String storageURI, String dbName, String collectionName )
    {
        MongoClientURI uri = new MongoClientURI( storageURI );
        mongo = new MongoClient( uri );
        MongoDatabase db = mongo.getDatabase( dbName );

        collection = db.getCollection( collectionName );
    }

    public void save( URLData urlData )
    {
        Document d = new Document("_id", urlData.getURL())
                              .append( "url", urlData.getURL() )
                              .append( "links", urlData.getLinks()  )
                              .append( "count", urlData.getLinks().size() );

                    /*.append( urlData.getURL(), new Document()
                                              .append( "links", urlData.getLinks() )
                                              .append( "count", urlData.getLinks().size() ));*/
        //System.out.println(d);

        try
        {
            collection.insertOne( d);
        }
        catch( MongoWriteException e )
        {
            
            //e.printStackTrace();
        }
        catch( MongoWriteConcernException e )
        {
            //e.printStackTrace();
        }
        catch( MongoException e )
        {
            //e.printStackTrace();
        }

        /*for (Document doc : collection.find()) {
            System.out.println(doc);
        }*/
    }

    public void close()
    {
        mongo.close();
    }

}
