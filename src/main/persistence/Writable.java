package persistence;

import org.json.JSONObject;

// Interface for classes which needs to be written to JSON
// References: JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}