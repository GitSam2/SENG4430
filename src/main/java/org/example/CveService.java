package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Dependency;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class CveService {
    private static final String URL_STRING = "https://api.osv.dev/v1/query";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<CveInfoBatch> fetchCves(List<DependencyModel> models) throws IOException, InterruptedException {
        List<CveInfoBatch> result = new ArrayList<>();
        
        // Create a list of purls from the models
        List<String> purls = models.stream()
            .map(m -> "pkg:maven/" + m.groupId() + "/" + m.artifactId() + "@" + m.version())
            .toList();

        List<String> bodyList = purls.stream()
            .map(purl -> """
                {
                    "package": {
                        "purl": "%s"
                    }
                }
                """.formatted(purl))
            .toList();

        // Parameterise string query
        String body = """
        {
            "queries": [
                {
                    %s
                }
            ]
        }
        """.formatted(String.join(",", bodyList));

        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(URL_STRING))
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) return null;

        // Read Json and get list of ids
        JsonNode root = mapper.readTree(resp.body());
        JsonNode results = root.get("results");
        if (results != null && results.isArray()) {
            int index = 0;
            for (JsonNode r : results) {
                JsonNode vulns = r.get("vulns");
                DependencyModel model = models.get(index++);
                if (vulns != null && vulns.isArray()) {
                    for (JsonNode v : vulns) {
                        JsonNode id = v.get("id");
                        JsonNode modified = v.get("modified");

                        if (id == null || modified == null) continue;
                        if (!id.isString() || !modified.isString()) continue;
                        CveInfoBatch cveInfoBatch = new CveInfoBatch(id.asString(), modified.asString(), model);
                        result.add(cveInfoBatch);
                    }
                }
            }
        }

        return result;
    }
    
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
