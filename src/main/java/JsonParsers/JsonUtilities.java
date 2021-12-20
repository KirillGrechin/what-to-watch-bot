package JsonParsers;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtilities {
    public static Integer getJSONObjectIndex(JSONArray array, String id) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);

            if (jsonObject.getString("id").equals(id)) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }
}
