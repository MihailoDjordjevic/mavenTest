import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        String followersFilepath = "src/main/resources/djurdjija/followers_1.json";
        String followingFilepath = "src/main/resources/djurdjija/following.json";

        Scanner scanner = new Scanner(System.in);

//        System.out.println("followers filepath:");
//        followersFilepath = scanner.nextLine();
//
//        System.out.println("followers filepath:");
//        followingFilepath = scanner.nextLine();

        List<String> followers = readData(followersFilepath);
        List<String> following = readData(followingFilepath);

        List<String> nonFollowers = new ArrayList<>();

        for (String follower : following){

            if(!followers.contains(follower))
                nonFollowers.add(follower);

        }

        System.out.println(nonFollowers);

    }

    public static List<String> readData(String filename) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(filename));

        List<String> names = jsonNode.findValuesAsText("value");
        List<String> time = jsonNode.findValuesAsText("timestamp");

        List<LocalDateTime> parsedTime = time.stream().map(s -> {

            Long num = Long.parseLong(s);
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(num), TimeZone.getDefault().toZoneId());

        }).toList();

        List<String> finalList = new ArrayList<>();

        for (int i = 0; i < names.size(); i++)
            finalList.add(names.get(i) + " " + parsedTime.get(i));

        return finalList;

    }

    public static List<String> readNamesOnly(String filename) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(filename));

        return jsonNode.findValuesAsText("value");

    }
}
