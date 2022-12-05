package org.sdt_practical_4.DB;

import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Query extends Mongo {

    public static String idCorrect(Object id){
        if (id == null){ return "empty"; }
        final Document doc = receiptsDB.find(new Document("orderId", id.toString())).first();
        if (doc == null){ return "not found"; }
        return "found";
    }

    public static String orderGetAll() {
        StringBuilder items = new StringBuilder();
        MongoCursor<Document> cursor = receiptsDB.find().iterator();
        try (cursor) {
            while (cursor.hasNext()) {
                Document item = cursor.next();
                item.remove("_id");
                items.append(item.toJson());
                if (cursor.hasNext()) { items.append(","); }
            }
        }
        return String.format("{\"Orders\": [ %s ]}", items); //items.toString()
    }

    public static String orderGet(String id){
        if (idCorrect(id).equals("found")) {
            final Document doc = receiptsDB.find(new Document("orderId", id)).first();
            assert doc != null;
            doc.remove("_id");
            return doc.toJson();
        } return new Document("Error", "Invalid orderId").toJson();
    }

    public static String orderCreate(Document request){
        try { receiptsDB.insertOne(
                new Document("orderId", request.get("orderId"))
                        .append("orderName", request.get("orderName"))
                        .append("orderNumber", request.get("orderNumber"))
                        .append("orderUnit", request.get("orderUnit"))
                        .append("orderDataStart", request.get("orderDataStart"))
                        .append("orderDataEnd", request.get("orderDataEnd"))
                        .append("orderCityStart", request.get("orderCityStart"))
                        .append("orderCityEnd", request.get("orderCityEnd"))
                        .append("orderRoute", request.get("orderRoute"))
                        .append("customerDetails", request.get("customerDetails"))
                        .append("orderSent", "")
                        .append("orderRouteCheck", "")
                        .append("orderReceive", "") );
            return  new Document("Ok", "Order save").toJson();
        } catch (Exception e) { return  new Document("Error", "Order not save").toJson(); }
    }

    public static String orderRouteCheck(Document request) {
        return setQuery(request.get("orderId").toString(),"orderRouteCheck", dateNow(), "check");
    }

    public static String orderSent(Document request) {
        return setQuery(request.get("orderId").toString(),"orderSent", dateNow(), "sent");
    }

    public static String orderReceive(Document request) {
        return setQuery(request.get("orderId").toString(),"orderReceive", dateNow(), "receive");
    }

    public static String orderEdit(Document request) {
        return setQuery(request.get("orderId").toString(), request.get("editKey").toString(),
                        request.get("editValue"), "edit");
    }

    private static String setQuery(String id, String editKey, Object editValue, String msg){
        try { receiptsDB.updateOne(
                new Document("orderId", id),
                new Document( "$set", new Document(editKey, editValue) ) );
            return  new Document("Ok", "Order " + msg).toJson();
        } catch (Exception e) { return  new Document("Error", "Order not " + msg).toJson(); }
    }

    public static boolean tokenCheckFalse(String token) {
        final Document doc = tokensDB.find(new Document("token", token)).first();
        if (doc == null || token.length() == 0){ return true;}
        return !doc.get("token").toString().equals(token);
    }

    public static void tokenCreate(String token){
        tokensDB.insertOne( new Document("token", token).append("data", dateNow()) );
    }

    public static String tokenGetAll(){
        StringBuilder items = new StringBuilder();
        MongoCursor<Document> cursor = tokensDB.find().iterator();
        try (cursor) {
            while (cursor.hasNext()) {
                Document item = cursor.next();
                items.append(item.get("token")).append("  ").append(item.get("date"));
                if (cursor.hasNext()) { items.append(",\n"); }
            }
        }
        return items.toString();
    }

    public static String tokenDelete(String token){
        if (tokenCheckFalse(token)) { return "Invalid token"; }
        tokensDB.deleteOne(new Document("token", token));
        return "Token delete";
    }

    private static String dateNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

}
