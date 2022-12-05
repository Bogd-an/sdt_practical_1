package org.sdt_practical_4;

import java.io.IOException;

import static org.sdt_practical_4.DB.Mongo.connect;
import static org.sdt_practical_4.Server.Start.startServer;

public class Main {
    public static void main(String[] args) throws IOException {
        connect();
        startServer();
    }
}