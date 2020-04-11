package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.apache.http.client.fluent.Executor;
import org.testng.SkipException;

import java.io.IOException;
import java.util.Set;

public class TestBase {



    public void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen (int issueId) {
       String json = RestAssured.get("https://bugify.stqa.ru/api/issues" + issueId + ".json").asString();
       JsonElement parsed = new JsonParser().parse(json).getAsJsonObject().get("issues").getAsJsonArray().get(0);
       String state  = parsed.getAsJsonObject().get("state_name").getAsString();
       System.out.println("Issue with id " + issueId + " is " + state);
        if ( state.equals("open")) {
           return false;
        } else {
           return true;
       }

    }
    protected Set<Issue> getIssues () throws IOException {
        String json = RestAssured.get(" https://bugify.stqa.ru/api/issue.json").asString();;
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }

    protected Executor getExecutor () {
        return Executor.newInstance().auth(" 28accbe43ea112d9feb328d2c00b3eed", "");
    }

    protected int createIssue (Issue newIssue) throws IOException {
        String json = RestAssured.given().parameter("subject", newIssue.getSubject())
                .parameter("description", newIssue.getDescription())
                .post(" https://bugify.stqa.ru/api").asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

}
