//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoDatabase;
//import org.bson.Document;
//
//public class QuickStart {
//    public static void main(String[] args) {
//        String uri = "mongodb+srv://froster02:<db_password>@cluster02.urykeix.mongodb.net/?retryWrites=true&w=majority&appName=Cluster02";
//
//        try (MongoClient mongoClient = MongoClients.create(uri)) {
//            // Test the connection
//            MongoDatabase database = mongoClient.getDatabase("admin");
//            database.runCommand(new Document("ping", 1));
//            System.out.println("Successfully connected to MongoDB Atlas!");
//
//            // Access your database
//            MongoDatabase sampleDB = mongoClient.getDatabase("sample_mflix");
//            System.out.println("Successfully connected to database: " + sampleDB.getName());
//
//            // List collections
//            System.out.println("Collections in sample_mflix:");
//            for (String name : sampleDB.listCollectionNames()) {
//                System.out.println("- " + name);
//            }
//
//        } catch (Exception e) {
//            System.err.println("Error connecting to MongoDB Atlas: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}