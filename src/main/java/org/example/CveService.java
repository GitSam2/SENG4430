package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class CveService {
    private static final String URL_STRING = "https://api.osv.dev/v1/query";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    
    public List<CveInfo> fetchCves(DependencyModel model) throws IOException, InterruptedException {
        List<CveInfo> result = new ArrayList<>();

        // Parameterise string query
        String purl = "pkg:maven/" + model.groupId() + "/" + model.artifactId() + "@" + model.version();
        String body = """
        {
            "package": {
                "purl": "%s"
            }
        }
        """.formatted(purl);

        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL_STRING))
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) return null;

        // Read Json and get list of ids
        JsonNode root = mapper.readTree(resp.body());
        JsonNode vulns = root.get("vulns");
        if (vulns != null && vulns.isArray()) {
            for (JsonNode v : vulns) {
                JsonNode id = v.get("id");
                JsonNode summary = v.get("summary");
                JsonNode details = v.get("details");

                if (id != null && summary != null && details != null
                    && id.isString() && summary.isString() && details.isString()
                ) {
                    CveInfo cveInfo = new CveInfo(id.asString(), summary.asString(), details.asString());
                    result.add(cveInfo);
                }
            }
        }

        return result;
    }
}
