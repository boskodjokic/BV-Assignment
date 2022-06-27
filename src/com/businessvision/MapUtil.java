package com.businessvision;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MapUtil {

    public static final String SPACE = " ";

    private static Map<String, Integer> convertMapToIntegerValues(Map<String, List<String>> map) {
        List<Map.Entry<String, List<String>>> list = new ArrayList<>(map.entrySet());

        Map<String, Integer> results = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : list) {
            results.put(entry.getKey(), entry.getValue().size());
        }
        return results;
    }

    private static Map<String, Integer> sortByValue(Map<String, List<String>> map) {

        return convertMapToIntegerValues(map).entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private static Map<String, List<String>> readLog(final File file) {

        Map<String, List<String>> mappedResults = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            Scanner sc = new Scanner(fis);

            while(sc.hasNextLine()) {
                String row = sc.nextLine();
                String website = row.substring(0, row.lastIndexOf(SPACE));
                String ip = row.substring(row.lastIndexOf(SPACE) + 1);
                List<String> ips = mappedResults.get(website);
                if(ips == null) {
                    ips = new ArrayList<>();
                }
                ips.add(ip);
                mappedResults.put(website, ips);
            }
            sc.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        if(!mappedResults.isEmpty()) {
            return mappedResults;
        } else {
            System.out.println("Error");
            return Collections.emptyMap();
        }
    }


    private static void printResults(final File file, boolean unique) {
        Map<String, List<String>> results = readLog(file);

        if(unique) {
            results.replaceAll((k, v) -> new ArrayList<>(new HashSet<>(v)));
        }
        sortByValue(results).forEach((k,v) -> System.out.println("Website: " + k + SPACE + v + (unique ? " unique view(s)" : " visit(s)")));

    }

    public static void printVisits(final File file) {
        printResults(file, false);
    }

    public static void printUniqueViews(final File file) {
        printResults(file, true);
    }
}
