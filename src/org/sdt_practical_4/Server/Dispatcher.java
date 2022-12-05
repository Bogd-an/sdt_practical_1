package org.sdt_practical_4.Server;

import org.bson.Document;
import org.sdt_practical_4.DB.Query;

public class Dispatcher {
    public static String getResponse(String requestString) {
        final Document request = Document.parse(requestString);

        if (Query.tokenCheckFalse(request.get("token").toString())) { return getError("Invalid token"); }
        String isIdCorrect = Query.idCorrect(request.get("orderId"));

        switch (request.get("type").toString()) {
            case "OrderCreate" -> {
                if (!orderCreateFieldsCheck(request)) { return getError("Required fields are missing"); }
                if (isIdCorrect.equals("found")) { return getError("OrderId already use"); }
                return Query.orderCreate(request);
            }
            case "OrderAllGet" -> {
                if (!isIdCorrect.equals("empty")) { return getError("Not need orderId "); }
                return Query.orderGetAll();
            }
        }

        if (!isIdCorrect.equals("found")) { return getError("Invalid orderId"); }

        switch (request.get("type").toString()) {
            case "OrderGet" -> { return Query.orderGet(request.get("orderId").toString()); }
            case "OrderRouteCheck" -> { return Query.orderRouteCheck(request); }
            case "OrderSent" -> { return Query.orderSent(request); }
            case "OrderReceive" -> { return Query.orderReceive(request); }
            case "OrderEdit" -> {
                if (!orderEditFieldsCheck(request)) { return getError("Required fields are missing"); }
                return Query.orderEdit(request);
            }
            default -> { return getError("Invalid request type"); }
        }
    }

    private static boolean orderCreateFieldsCheck(Document request){
        final String[] orderFields = {"orderName", "orderNumber", "orderUnit",
                                      "orderDataStart","orderDataEnd", "orderCityStart",
                                      "orderCityEnd", "orderRoute", "customerDetails",};
        return fieldsCheck(request, orderFields);
    }
    private static boolean orderEditFieldsCheck(Document request){
        final String[] orderFields = {"editKey", "editValue"};
        return fieldsCheck(request, orderFields);
    }
    private static boolean fieldsCheck(Document request, String[] orderFields){
        for (String field: orderFields){
            String item = request.get(field).toString();
            if (item.equals("null") || item.length()==0) { return false; }
        }
        return true;
    }
    private static String getError(String text){ return new Document("Error", text).toJson(); }

}
