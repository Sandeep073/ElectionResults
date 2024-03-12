package com.example.votersproject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Region {
    String name;
    Map<String, Integer> analysis = new HashMap<>();
    int invalid = 0;
}

public class ElectionResults{

    public static void main(String[] args) {
        List<Integer> voters = new ArrayList<>();
        Map<String, Integer> totalResults = new HashMap<>();
        List<Region> regions = new ArrayList<>();
        String file = "C:\\Users\\divit\\eclipse-workspace\\VotersProject\\src\\com\\example\\votersproject\\votes.dat";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Region currentRegion = null;

            while ((line = br.readLine()) != null) {
                if (line.equals("//")) {
                    while (!(line = br.readLine()).equals("&&")) {
                        if (line.equals("//"))
                            continue;
                        else if (Character.isDigit(line.charAt(0))) {
                            processVote(line, voters, currentRegion, totalResults);
                        } else {
                            currentRegion = findRegion(line, regions);
                        }
                    }
                } else if (line.contains("/")) {
                    createRegion(line, regions, totalResults);
                }

                if (line.equals("&&")) {
                    break;
                }
            }
            printElectionResults(regions, totalResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processVote(String voteLine, List<Integer> voters, Region currentRegion, Map<String, Integer> totalResults) {
        String[] vt = voteLine.split(" ");
        String[] slicedArray = Arrays.copyOfRange(vt, 1, vt.length);

        Integer voterId = Integer.valueOf(vt[0]);
        if (voters.contains(voterId) || vt.length > 4 || vt.length < 2 || !allKeysPresent(currentRegion, slicedArray)) {
            currentRegion.invalid++;
        } else {
            voters.add(voterId);
            for (int i = 0; i < slicedArray.length; i++) {
                String votedFor = slicedArray[i];
                int currentVotes = currentRegion.analysis.getOrDefault(votedFor, 0);
                currentVotes += i * (-1) + 3;
                currentRegion.analysis.put(votedFor, currentVotes);
                totalResults.put(votedFor, totalResults.getOrDefault(votedFor, 0) + i * (-1) + 3);
            }
        }
    }

    private static Region findRegion(String regionName, List<Region> regions) {
        for (Region region : regions) {
            if (region.name.equals(regionName)) {
                return region;
            }
        }
        return null;
    }

    private static void createRegion(String regionLine, List<Region> regions, Map<String, Integer> totalResults) {
        String[] tmp = regionLine.split("/");
        char[] cnts = tmp[1].toCharArray();
        Region r = new Region();
        r.name = tmp[0];
        for (char cntst : cnts) {
            r.analysis.put(String.valueOf(cntst), 0);
            totalResults.putIfAbsent(String.valueOf(cntst), 0);
        }
        regions.add(r);
    }

    private static void printElectionResults(List<Region> regions, Map<String, Integer> totalResults) {
        for (Region r : regions) {
            String wonAtRegion = "";
            int currentHigh = 0;
            if (!r.analysis.isEmpty()) {
                for (Map.Entry<String, Integer> entry : r.analysis.entrySet()) {
                    if (entry.getValue() > currentHigh) {
                        currentHigh = entry.getValue();
                        wonAtRegion = entry.getKey();
                    }
                }
                System.out.println(wonAtRegion + "  with score: " + currentHigh + " And No of invalid votes in this region is: " + r.invalid);
                System.out.println(r.name + " REGIONAL HEAD is " + wonAtRegion);
            }
        }

        String chiefOfficer = "";
        int currentHigh = 0;
        for (Map.Entry<String, Integer> entry : totalResults.entrySet()) {
            if (entry.getValue() > currentHigh) {
                chiefOfficer = entry.getKey();
                currentHigh = entry.getValue();
            }
        }
        System.out.println("Chief Officer is: " + chiefOfficer + "  with total votes: " + currentHigh);
        System.out.println("Congratulations: " + chiefOfficer);
    }

    private static boolean allKeysPresent(Region currentRegion, String[] slicedArray) {
        for (String i : slicedArray) {
            if (!currentRegion.analysis.containsKey(i)) {
                return false;
            }
        }
        return true;
    }
}
