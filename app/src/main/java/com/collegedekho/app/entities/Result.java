package com.collegedekho.app.entities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class Result {
    public static final String TAG_RESULTS = "results";
    private String count;
    private String next;
    private String previous;
    private String results;

    public Result() {
    }

    public static Result createFromJson(JsonParser jp) throws IOException {
        Result r = new Result();
        jp.nextToken();
        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String field = jp.getCurrentName();
            jp.nextToken();
            switch (field) {
                case TAG_RESULTS:
                    r.results = jp.getText();
                    break;
                default:
                    jp.skipChildren();
                    break;
            }
        }
        return r;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

}
