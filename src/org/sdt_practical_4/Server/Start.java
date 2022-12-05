package org.sdt_practical_4.Server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Start {
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public static void startServer() throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 1);
        server.createContext("/response", he -> {
            try (he) {
                final Headers headers = he.getResponseHeaders();
                final String requestMethod = he.getRequestMethod().toUpperCase();
                if ("POST".equals(requestMethod)) {
                    final String requestBody = new String(he.getRequestBody().readAllBytes());
                    final byte[] response = Dispatcher.getResponse(requestBody).getBytes(CHARSET);

                    headers.set("Content-Type", String.format("application/json; charset=%s", CHARSET));
                    he.sendResponseHeaders(200, response.length);
                    he.getResponseBody().write(response);
                } else {
                    headers.set("Allow", "POST");
                    he.sendResponseHeaders(405, -1);
                }
            }
        });
        server.start();
    }
}
