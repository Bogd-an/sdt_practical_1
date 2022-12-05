package org.sdt_practical_4;

import org.sdt_practical_4.DB.Mongo;
import org.sdt_practical_4.DB.Query;

import java.util.UUID;

public class TokenManager {
    public static void main(String[] args){
        Mongo.connect();
        if (args.length > 0) {
            switch (args[0]) {
                case "-create": case "-c":
                    String token = UUID.randomUUID().toString().replace("-", "");
                    Query.tokenCreate(token);
                    System.out.println("Token create: " + token);
                    break;
                case "-getAll": case "-ga":
                    System.out.println(Query.tokenGetAll());
                    break;
                case "-delete": case "-d":
                    if(args.length == 2){
                        System.out.println(Query.tokenDelete(args[1]));
                    } else { error(); }
                    break;
                default:
                    error();
                    break;
            }
        } else { error(); }
        Mongo.disconnect();
    }

    private static void error(){
        System.out.println("Invalid argument");
        System.out.println("Use: -create/-c -getAll/-ga -delete/-d [token]");
    }
}
