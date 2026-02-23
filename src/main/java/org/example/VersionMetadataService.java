package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;


public class VersionMetadataService {
    
    private static final String URL_STRING = "https://search.maven.org/solrsearch/select?q=%s&rows=200&wt=json";

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    /// Fetches the latest version of a java artifact
    /// @return version String or null if request failed
    /// @throws InterruptedException 
    /// @throws IOException 
    public String fetchLatestVersion(String groupId, String artifactId) throws IOException, InterruptedException {
        // Parameterise string query
        String query = "g:" + groupId + "+AND+a:" + artifactId;
        String url = URL_STRING.formatted(query);

        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) return null;

        // Get root of main data of json
        JsonNode root = mapper.readTree(resp.body());
        JsonNode response = root.get("response");
        if (response == null) return null;

        // Get artifact information
        JsonNode docs = response.get("docs");
        if (docs == null || !docs.isArray() || docs.isEmpty()) return null;

        // Get first result and version
        JsonNode first = docs.get(0);
        JsonNode latestVersion = first.get("latestVersion");
        if (latestVersion != null && latestVersion.isString()) return latestVersion.asString();

        return null;
    }
}
