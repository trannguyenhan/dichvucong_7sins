package crawler.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import crawler.builer.BuilderServices;
import crawler.models.PublicService;
import org.bson.Document;

public class DVC {
    private BuilderServices builderServices;
    private PublicService publicService;
    private GsonBuilder builder;

    public DVC(){
        builderServices = new BuilderServices();

        builder = new GsonBuilder();
        builder.setPrettyPrinting();
    }

    public void run(){
        Gson gson = builder.create();
        String json = gson.toJson(publicService, PublicService.class); // create json string
        System.out.println(json);
        // URI
        String uri = "mongodb://127.0.0.1:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("dichvucong"); // get database
            MongoCollection<Document> collection = database.getCollection("data_1"); //get collection ~ table in SQL

            Document document = Document.parse(json); // create document with json string
            collection.insertOne(document);
        }
    }

    // reconnect service with other URL
    public void updateService(String url){
        builderServices.connect(url);
        builderServices.build();

        publicService = builderServices.getPublicService();
    }
}
