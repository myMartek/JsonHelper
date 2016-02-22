import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Marcel Wlotzka on 22.02.16.
 */
public class JsonHelper implements Iterable<JsonHelper> {
    private JsonElement element;

    public final class JsonHelperIterator implements Iterator<JsonHelper> {
        private int index = 0;

        public boolean hasNext() {
            return index < size();
        }

        public JsonHelper next() {
            return get(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException("This class is read-only");
        }
    }

    public final class JsonHelperEntry<String, JsonHelper> implements Map.Entry<String, JsonHelper> {
        private final String key;
        private JsonHelper value;

        public JsonHelperEntry(String key, JsonHelper value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public JsonHelper getValue() {
            return value;
        }

        @Override
        public JsonHelper setValue(JsonHelper object) {
            JsonHelper old = this.value;

            this.value = object;
            return old;
        }
    }

    public final class JsonHelperObjectIterator implements Iterable<JsonHelperEntry<String, JsonHelper>> {
        public JsonHelperObjectIterator() {
            super();
        }
        @Override
        public Iterator<JsonHelperEntry<String, JsonHelper>> iterator() {
            return new JsonHelperActualObjectIterator();
        }

        public final class JsonHelperActualObjectIterator implements Iterator<JsonHelperEntry<String, JsonHelper>> {
            private Iterator<Map.Entry<String, JsonElement>> iterator;

            public JsonHelperActualObjectIterator() {
                super();

                if (element != null && element.isJsonObject()) {
                    iterator = element.getAsJsonObject().entrySet().iterator();
                }
            }

            @Override
            public boolean hasNext() {
                if (iterator != null) {
                    return iterator.hasNext();
                }

                return false;
            }

            @Override
            public JsonHelperEntry<String, JsonHelper> next() {
                if (iterator != null) {
                    Map.Entry<String, JsonElement> el = iterator.next();

                    return new JsonHelperEntry<>(el.getKey(), new JsonHelper(el.getValue()));
                }

                return null;
            }

            @Override
            public void remove() {
                if (iterator != null) {
                    iterator.remove();
                }
            }
        }
    }

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

    public JsonHelper get(int index) {
        if (element == null) {
            return this;
        }

        if (element.isJsonArray() && index < element.getAsJsonArray().size() && index >= 0) {
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

    public JsonHelperObjectIterator entrySet() {
        return new JsonHelperObjectIterator();
    }

    @Override
    public Iterator<JsonHelper> iterator() {
        return new JsonHelperIterator();
    }
}
