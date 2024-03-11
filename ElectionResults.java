package com.example.votersproject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Region {
    String name;
    Map<String, Integer> analysis = new HashMap<String, Integer>();
    int invalid = 0;
}

public class ElectionResults {

    public static void main(String[] args) throws IOException {
        List<Integer> voters = new ArrayList<Integer>();
        Map<String, Integer> total_results = new HashMap<String, Integer>();
        List<Region> regions = new ArrayList<Region>();
        String file = "C:\\Users\\divit\\eclipse-workspace\\VotersProject\\src\\com\\example\\votersproject\\votes.dat";
        FileReader fr;

        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String initial;
            Region currentRegion = null;

            while ((initial = br.readLine()) != null) {
                if (initial.equals("//")) {
                    while (!(initial = br.readLine()).equals("&&")) {
                        if (initial.equals("//")) {
                            continue;
                        } else if (Character.isDigit(initial.charAt(0))) {
                            String[] vt = initial.split(" ");
                            String[] slicedArray = Arrays.copyOfRange(vt, 1, vt.length);

                            Integer voterId = Integer.valueOf(vt[0]);
                            if (voters.contains(voterId) || vt.length > 4 || vt.length < 2 || !allKeysPresent(currentRegion, slicedArray)) {
                                currentRegion.invalid += 1;
                            } else {
                                voters.add(voterId);
                                for (int i = 0; i < slicedArray.length; i++) {
                                    String votedFor = slicedArray[i];
                                    Integer currentVotes = currentRegion.analysis.get(votedFor);
                                    currentVotes += i * (-1) + 3;
                                    currentRegion.analysis.put(votedFor, currentVotes);
                                    Integer totalCurrentVotes = total_results.get(votedFor);
                                    totalCurrentVotes += i * (-1) + 3;
                                    total_results.put(votedFor, totalCurrentVotes);
                                }
                            }
                        } else {
                            for (Region regn : regions) {
                                if (regn.name.equals(initial)) {
                                    currentRegion = regn;
                                }
                            }
                        }
                    }
                } else if (initial.contains("/")) {
                    String[] tmp = initial.split("/");
                    char[] cnts = tmp[1].toCharArray();
                    Region r = new Region();
                    r.name = tmp[0];
                    for (char cntst : cnts) {
                        r.analysis.put(String.valueOf(cntst), 0);
                        if (!(total_results.containsKey(String.valueOf(cntst)))) {
                            total_results.put(String.valueOf(cntst), 0);
                        }
                    }
                    regions.add(r);
                }

                if (initial.equals("&&")) {
                    break;
                }
            }
            ElectionResults(regions, total_results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ElectionResults(List<Region> regions, Map<String, Integer> total_results) {
        for (Region r : regions) {
            String wonAtRegion = "";
            Integer currentHigh = 0;
            if (r.analysis.keySet().size() >= 1) {
                for (String candidate : r.analysis.keySet()) {
                    if (r.analysis.get(candidate) > currentHigh) {
                        currentHigh = r.analysis.get(candidate);
                        wonAtRegion = candidate;
                    }
                }
                System.out.println(wonAtRegion + "  with score: " + currentHigh + " And No of invalid votes in this region is: " + r.invalid);
                System.out.println(r.name + " REGIONAL HEAD is " + wonAtRegion);
            }
        }

        String ChiefOfficer = "";
        Integer currentHigh = 0;
        for (String cd : total_results.keySet()) {
            if (total_results.get(cd) > currentHigh) {
                ChiefOfficer = cd;
                currentHigh = total_results.get(cd);
            }
        }
        System.out.println("Chief Officer is: " + ChiefOfficer + "  with total votes: " + currentHigh);
        System.out.println("Cogratulations : "+ChiefOfficer);
    }

    private static boolean allKeysPresent(Region currentRegion, String[] slicedarray) {
        boolean allKeysPresent = true;
        for (String i : slicedarray) {
            if (!currentRegion.analysis.containsKey(i)) {
                allKeysPresent = false;
            }
        }
        return allKeysPresent;
    }
}
