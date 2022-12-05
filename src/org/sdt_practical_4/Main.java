package org.sdt_practical_4;

import java.io.IOException;
import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;
import static org.sdt_practical_4.DB.Mongo.connect;
import static org.sdt_practical_4.Server.Start.startServer;

public class Main {
    public static void main(String[] args) throws IOException {
        getLogger("org.mongodb").setLevel(Level.OFF);
        connect();
        startServer();
    }
}