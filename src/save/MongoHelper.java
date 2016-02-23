package save;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoHelper {
	public static void main(String[] args) {
		MongoClient client = new MongoClient("localhost", 27017);
		String databaseName = "allusion";
		MongoDatabase database = client.getDatabase(databaseName);
		String collectionName = "diangu";
		database.createCollection(collectionName);
		MongoCollection<Document> collection = database.getCollection(collectionName);

		FileHelper helper = new FileHelper();
		List<List<String>> patterns = helper.readAllusionPattern();
		List<List<Example>> examples = helper.readAllusionExample();
		List<List<Source>> sources = helper.readAllusionSource();

		int size = patterns.size();
		int size2 = examples.size();
		if (size != size2) {
			System.out.println(size);
			System.out.println(size2);
			System.out.println("典面/用例数目不一致");
			client.close();
			return;
		}
		Document doc = null;
		String patternKey = "pattern";
		String sourceKey = "source";
		String exampleKey = "example";
		for (int i = 0; i < size; i++) {
			// String sourceKey="source";
			List<String> patternValue = patterns.get(i);
			List<Source> sourceTmpValue = sources.get(i);
			List<Document> sourceValue = convertSource(sourceTmpValue);
			List<Example> exampleTmpValue = examples.get(i);
			List<Document> exampleValue = convertExample(exampleTmpValue);
			doc = new Document().append(patternKey, patternValue).append(sourceKey, sourceValue).append(exampleKey,
					exampleValue);
			collection.insertOne(doc);
		}

		client.close();
	}

	private static List<Document> convertExample(List<Example> exampleValue) {
		List<Document> docs = new ArrayList<Document>();
		String poetKey = "poet";
		String titleKey = "title";
		String verseKey = "verse";
		Document doc = null;
		Example example = null;
		for (int i = 0; i < exampleValue.size(); i++) {
			example = exampleValue.get(i);
			String poetValue = example.getPoet();
			String titleValue = example.getTitle();
			String verseValue = example.getVerse();
			doc = new Document().append(poetKey, poetValue).append(titleKey, titleValue).append(verseKey, verseValue);
			docs.add(doc);
		}
		return docs;
	}

	private static List<Document> convertSource(List<Source> sourceValue) {
		List<Document> docs = new ArrayList<Document>();
		String titleKey = "title";
		String contentKey = "content";
		Document doc = null;
		Source source = null;
		for (int i = 0; i < sourceValue.size(); i++) {
			source = sourceValue.get(i);
			String titleValue = source.getTitle();
			String contentValue = source.getContent();
			doc = new Document().append(titleKey, titleValue).append(contentKey, contentValue);
			docs.add(doc);
		}
		return docs;
	}

	private void test1() {
		MongoClient client = new MongoClient("localhost", 27017);
		String databaseName = "allusion";
		MongoDatabase database = client.getDatabase(databaseName);
		String collectionName = "test";
		database.createCollection(collectionName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		Document doc = new Document("title", "MongoDB").append("description", "database").append("likes", 100)
				.append("url", "http://www.w3cschool.cc/mongodb/").append("by", "w3cschool.cc");
		collection.insertOne(doc);
		FindIterable<Document> iterator = collection.find();
		for (Document d : iterator) {
			System.out.println(d.toString());
		}
	}

}
