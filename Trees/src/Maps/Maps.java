package Maps;

import java.util.*;

public class Maps {
    public static void main(String args[]) {
        List<String> words = new ArrayList<>();
        //  "abstract", "class", "fields", "methods"
        words.add("abstract");
        words.add("class");
        words.add("fields");
        words.add("methods");
        Map<Integer, List<String>> map = new HashMap<>();
        map.put(2, words);
        words = new ArrayList<>();
        //  "class", "fields", "types"
        words.add("class");
        words.add("fields");
        words.add("types");
        map.put(1, words);
        Map<String, List<Integer>> newMap = reverseMap(map);
        System.out.println(newMap);
    }

    /**
     * Convert a map that has page number as key and list of words as value to a map
     * has word as key and list of page number as value
     * @param map - the original map that has page number as key and list of words as value
     * @return a map that has word as key and list of page number as value
     */
    public static Map<String, List<Integer>> reverseMap(Map<Integer, List<String>> map) {
        Set<Integer> keySet = map.keySet();
        Map<String, List<Integer>> newMap = new HashMap<>();
        for (int key : keySet) {
            List<String> words = map.get(key);
            for (String word : words) {
                List<Integer> pages = newMap.get(word);
                if (pages == null) {
                    pages = new ArrayList<>();
                    pages.add(key);
                    newMap.put(word, pages);
                } else {
                    pages.add(key);
                    Collections.sort(pages);
                    newMap.put(word, pages);
                }
            }
        }
        return newMap;
    }
}
