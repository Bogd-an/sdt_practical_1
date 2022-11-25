package org.sdt_practical_4.DB;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class QueryCreate {

    public static List<Document> orderCreate(
            int orderId,
            String orderName,
            String orderNumber,
            String orderUnit,
            String orderDataStart,
            String orderDataEnd,
            String orderCityStart,
            String orderCityEnd,
            String orderRoute,
            String customerDetails
    ) {
        List<Document> seedData = new ArrayList<Document>();
        seedData.add(new Document("orderId", orderId)
                .append("orderName", orderName)
                .append("orderNumber", orderNumber)
                .append("orderUnit", orderUnit)
                .append("orderDataStart", orderDataStart)
                .append("orderDataEnd", orderDataEnd)
                .append("orderCityStart", orderCityStart)
                .append("orderCityEnd", orderCityEnd)
                .append("orderRoute", orderRoute)
                .append("customerDetails", customerDetails)
                .append("orderSent", "")
                .append("orderReceive", "")
        );
        return seedData;
    }

    public static Document byId(int id){
        return new Document("orderId", id);
    }

    public static Document update(String value_name, String value){
        return new Document("$set", new Document(value_name, value));
    }

}
