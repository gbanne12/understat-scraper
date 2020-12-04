import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataOrganiser {

    /**
     * Returns a {@code List} which is sorted by the given attribute from highest value to lowest
     *
     * @param array the array containing the items to be sorted
     * @param key the name of the attribute to sort on e.g. xG
     * @return the sorted list in descending order
     */
    public List<JSONObject> sortByKey(JSONArray array, String key) {
         List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }

        list.sort((a, b) -> {
            String valA = a.getString(key);
            String valB = b.getString(key);
            return -valA.compareTo(valB);
        });
        return list;
    }

    /**
     *  Similar to {@link #limit(List, int)} but accepts a JSONArray as parameter
     *
     * @param array the array to be shortened
     * @param size the number of items in the new list
     * @return the shortened list
     */
    public List<JSONObject> limit(JSONArray array, int size) {
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }
        return limit(list, size);
    }

    /**
     * Returns a {@code List} containing the first items from index 0 to the value of size
     *
     * @param list the array to be shortened
     * @param size the number of items in the new list
     * @return the shortened list
     */
    private List<JSONObject> limit(List<JSONObject> list, int size) {
        return list.stream().limit(size).collect(Collectors.toList());
    }

}
