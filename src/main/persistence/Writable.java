package persistence;

import org.json.JSONObject;

// References: JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}