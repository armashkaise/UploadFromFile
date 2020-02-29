

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        Set<Map> stringsMap = new HashSet<>();
        File file = new File(location.getFile() + "/citiesObjects.txt");
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Scanner scan = new Scanner(bufferedReader);
        while (scan.hasNextLine()) {
            stringsMap.add(parseString(scan.nextLine()));
        }

        List<Map> streetsList = stringsMap.stream().peek(s -> s.values()).filter(s -> "Street".equals(s.get("class"))).collect(Collectors.toList());
        List<Map> parksList = stringsMap.stream().peek(s -> s.values()).filter(s -> "Park".equals(s.get("class"))).collect(Collectors.toList());

        System.out.println(streetsList);
        System.out.println(parksList);

    }

    private static Map<String, String> parseString(String nextLine) {
        Map<String, String> currentMap = new HashMap<>();
        String[] split = nextLine.split(",");
        for (int i = 0; i < split.length; i++) {
            String[] s = split[i].split(":");
            String key = s[0].trim();
            String value = checkValue(key, s[1]);

            if (Objects.nonNull(key) && Objects.nonNull(value))
                currentMap.put(key, value);
        }
        return currentMap;
    }

    private static String checkValue(String key, String value) {
        if (("id".equals(key)) && ("null".equals(value)))
            return UUID.randomUUID().toString();
        return value;
    }

}
