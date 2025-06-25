import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalTime;
import java.util.Random;

public class S3InfoServer {

    private static final String[] S3_FACTS = {
        "Scale storage resources to meet fluctuating needs with 99.999999999% (11 9s) of data durability.",
        "Store data across Amazon S3 storage classes to reduce costs without upfront investment or hardware refresh cycles.",
        "Protect your data with unmatched security, compliance, and audit capabilities.",
        "Easily manage data at any scale with robust access controls, flexible replication tools, and organization-wide visibility.",
        "Run big data analytics, artificial intelligence (AI), machine learning (ML), and high performance computing (HPC) applications.",
        "Meet Recovery Time Objectives (RTO), Recovery Point Objectives (RPO), and compliance requirements with S3’s robust replication features."
    };

    public static void main(String[] args) throws IOException {
        System.out.println("Starting Java HTTP server on port 8002...");

        HttpServer server = HttpServer.create(new InetSocketAddress(8002), 0);
        server.createContext("/", new S3Handler());
        server.setExecutor(null); // use default executor
        server.start();

        System.out.println("Server started. Listening on http://localhost:8002/");
    }

    static class S3Handler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            Random rand = new Random();
            int index = rand.nextInt(S3_FACTS.length);
            String fact = LocalTime.now() + " - " + S3_FACTS[index];

            byte[] response = fact.getBytes();
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}
