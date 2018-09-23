package com.tensquare;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-22 17:25
 **/
public class MongoDBTest {

    public static void main(String[] args) {

        MongoClient client = new MongoClient("192.168.66.133");
        MongoDatabase spitdb = client.getDatabase("spitdb");
        MongoCollection<Document> collection = spitdb.getCollection("spit");
        //查询所有
        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println("内容: " + document.getString("content"));
            System.out.println("用户id: " + document.getString("userid"));
            System.out.println("浏览量: " + document.getInteger("visits"));
            System.out.println("---------------------------");
        }
        client.close();
    }

    @Test
    public void test1() {

        MongoClient client = new MongoClient("192.168.66.133");
        MongoDatabase spitdb = client.getDatabase("spitdb");
        MongoCollection<Document> spit = spitdb.getCollection("spit");
        Bson bson = new BasicDBObject("userid", "1013");
        FindIterable<Document> documents = spit.find(bson);
        for (Document document : documents) {
            System.out.println("内容: " + document.getString("content"));
            System.out.println("用户id: " + document.getString("userid"));
            System.out.println("浏览量: " + document.getInteger("visits"));
            System.out.println("---------------------------");
        }
        client.close();
    }

//    查询浏览量大于1000的记录

    @Test
    public void test2() {
        MongoClient client = new MongoClient("192.168.66.133");
        MongoDatabase spitdb = client.getDatabase("spitdb");
        MongoCollection<Document> spit = spitdb.getCollection("spit");
        BasicDBObject bson = new BasicDBObject("visits", new BasicDBObject("$gt", 10));

        FindIterable<Document> documents = spit.find(bson);
        for (Document document : documents) {
            System.out.println("内容: " + document.getString("content"));
            System.out.println("用户id: " + document.getString("userid"));
            System.out.println("浏览量: " + document.getInteger("visits"));
            System.out.println("---------------------------");
        }
        client.close();

    }

    //查询吐槽集合中userid字段不包含1013和1014的文档
    @Test
    public void test3() {
        MongoClient client = new MongoClient("192.168.66.133");
        MongoDatabase spitdb = client.getDatabase("spitdb");
        MongoCollection<Document> spit = spitdb.getCollection("spit");
        String[] strings = new String[]{"1013", "1014"};
//        BasicDBObject bson = new BasicDBObject("userid",new BasicDBObject("$nin",strings));
        BasicDBObject bson = new BasicDBObject("userid", new BasicDBObject("$nin", new String[]{"1013", "1014"}));

        FindIterable<Document> documents = spit.find(bson);
        for (Document document : documents) {
            System.out.println("内容: " + document.getString("content"));
            System.out.println("用户id: " + document.getString("userid"));
            System.out.println("浏览量: " + document.getInteger("visits"));
            System.out.println("---------------------------");
        }
        client.close();
    }


    //插入数据
    @Test
    public void testInset() {
        MongoClient client = new MongoClient("192.168.66.133");
        MongoDatabase spitdb = client.getDatabase("spitdb");
        MongoCollection<Document> spit = spitdb.getCollection("spit");
        Map map = new HashMap();
        map.put("content", "我要吐槽");
        map.put("userid", "1243124");
        map.put("_id", "12");
        map.put("visits", 123);
        Document document = new Document(map);
        spit.insertOne(document);
        client.close();


    }
}
