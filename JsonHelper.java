import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Marcel Wlotzka on 22.02.16.
 */
public class JsonHelper {
    private JsonElement element;

    public JsonHelper() {
        this.element = null;
    }

    public JsonHelper(JsonElement element) {
        this.element = element;
    }

    public JsonHelper(String content) {
        try {
            element = new JsonParser().parse(content);
        } catch (Exception e) {
            element = null;
        }
    }

    public JsonHelper get(String key) {
        if (element == null) {
            return this;
        }

        if (element.isJsonObject() && element.getAsJsonObject().has(key)) {
            return new JsonHelper(element.getAsJsonObject().get(key));
        }

        return new JsonHelper();
    }

    public int size() {
        if (element == null) {
            return 0;
        }

        if (element.isJsonArray()) {
            return element.getAsJsonArray().size();
        }

        return 0;
    }

    public JsonHelper at(int index) {
        if (element == null) {
            return this;
        }

        if (element.isJsonArray() && index < element.getAsJsonArray().size() && index > 0) {
            return new JsonHelper(element.getAsJsonArray().get(index));
        }

        return new JsonHelper();
    }

    public String stringValue() {
        if (element == null) {
            return null;
        }

        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            return element.getAsJsonPrimitive().getAsString();
        }

        return null;
    }

    public Integer intValue() {
        if (element == null) {
            return null;
        }

        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsInt();
        }

        return null;
    }

    public Long longValue() {
        if (element == null) {
            return null;
        }

        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsLong();
        }

        return null;
    }

    public Float floatValue() {
        if (element == null) {
            return null;
        }

        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsFloat();
        }

        return null;
    }

    public Double doubleValue() {
        if (element == null) {
            return null;
        }

        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsDouble();
        }

        return null;
    }

    public Boolean boolValue() {
        if (element == null) {
            return null;
        }

        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) {
            return element.getAsJsonPrimitive().getAsBoolean();
        }

        return null;
    }

    public JsonElement jsonElement() {
        return element;
    }

    public JsonObject jsonObject() {
        if (element == null) {
            return null;
        }

        if (element.isJsonObject()) {
            return element.getAsJsonObject();
        }

        return null;
    }

    public JsonArray jsonArray() {
        if (element == null) {
            return null;
        }

        if (element.isJsonArray()) {
            return element.getAsJsonArray();
        }

        return null;
    }

    public String stringify() {
        if (element == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.toJson(element);
    }
}
