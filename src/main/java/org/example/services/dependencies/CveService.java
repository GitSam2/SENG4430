package org.example.services.dependencies;

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
import us.springett.cvss.Cvss;
import us.springett.cvss.Score;

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
                JsonNode severity = v.get("severity");
                JsonNode affected = v.get("affected");

                if (id != null && summary != null && details != null && severity != null && affected != null
                    && id.isString() && summary.isString() && details.isString() && severity.isArray() && affected.isArray()
                ) {
                    JsonNode cvss = severity.get(0).get("score");
                    JsonNode type = affected.get(0).get("ranges").get(0).get("type");
                    if (cvss != null && cvss.isString() && type != null && type.isString()) {
                        Cvss cvssObj = Cvss.fromVector(cvss.asString());

                        // Calculate the score
                        Score score = cvssObj.calculateScore();
                        double baseScore = score.getBaseScore(); // e.g. 9.8
                        String fixedVersion = null;
                        if (type.asString().equals("SEMVER") || type.asString().equals("ECOSYSTEM")) {
                            fixedVersion = extractFixedVersion(affected.get(0).get("ranges").get(0).get("events"));
                        }

                        CveInfo cveInfo = new CveInfo(id.asString(), summary.asString(), details.asString(), baseScore, fixedVersion);
                        result.add(cveInfo);
                    }
                }
            }
        }

        return result;
    }

    private String extractFixedVersion(JsonNode events) {
        for (JsonNode event : events) {
            JsonNode fixed = event.get("fixed");
            if (fixed != null && fixed.isString()) {
                return fixed.asString();
            }
        }
        return null;
    }
}
