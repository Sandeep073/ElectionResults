import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ElectionResults {
    public static void main(String[] args) {
        String fileName = "C:/Users/divit/OneDrive/Documents/Java Materials/Voteslist.dat"; // Specify the path to your .dat file
        List<String> regions = new ArrayList<>(); // List to store unique region names
        List<String> contestants = new ArrayList<>(); // List to store contestants for each region
        List<String> remainingData = new ArrayList<>(); // List to store data from specified line number
        Set<String> voterIds = new HashSet<>(); // Set to store unique voter IDs

        // Specify the line number from which to start saving data into the remainingData list
        int startLine = 6; // (number of regions + 1)

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int currentLine = 1; // Variable to keep track of the current line number
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (currentLine <= startLine) {
                    if (!line.isEmpty() && line.charAt(0) != '/') {
                        int index = line.indexOf('/'); // Find the index of the '/' character
                        if (index != -1) {
                            String reg_name = line.substring(0, index); // Extract the region name
                            String contestantsList = line.substring(index + 1); // Extract the contestants' names
                            System.out.println("Region: " + reg_name);
                            System.out.println("Contestants: " + contestantsList);
                            regions.add(reg_name); // Add the region name to the list
                            contestants.add(contestantsList);
                        }
                    }
                } else {
                    // Save the data into the remainingData list
                    remainingData.add(line);
                }
                currentLine++; // Increment the current line number
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Break the remainingData list into different lists based on the delimiter "//"
        List<List<String>> regionDataLists = new ArrayList<>();
        List<String> currentList = new ArrayList<>();
        for (String data : remainingData) {
            if (data.equals("//")) {
                regionDataLists.add(currentList);
                currentList = new ArrayList<>();
            } else {
                currentList.add(data);
            }
        }
        if (!currentList.isEmpty()) {
            regionDataLists.add(currentList);
        }

        // Display the data lists for each region
        
        System.out.println("Data lists for each region:");
        int numOfRegions = regions.size();
        Map<String, Integer> winnerList = new LinkedHashMap<>();
        int winnerIndex = 0;
        for (int i = 0; i < numOfRegions; i++) {
        	int count =0 ;
            List<String> regionData = regionDataLists.get(i);
            System.out.println("Region " + regions.get(i));
            List<String> contestantList = new ArrayList<>(Arrays.asList(contestants.get(i).split("")));
            List<Integer> votes = new ArrayList<>(Collections.nCopies(contestantList.size(), 0));
            for (int j = 1; j < regionData.size(); j++) { // Start from the second element
                String[] parts = regionData.get(j).split(" ");
                String voterId = parts[0]; // Extract voter ID
                if (!voterIds.contains(voterId)) { // If voter ID is not in the set, add it
                    voterIds.add(voterId);
                    String preferences = parts[1];
                    List<String> preferenceList = new ArrayList<>(Arrays.asList(preferences.split("")));
                    System.out.println("Voter ID: " + voterId);
                    System.out.println("------" + preferenceList + "--------");
                    for (String preference : preferenceList) {
                        if (contestantList.contains(preference)) {
                            // Preference is valid
                            System.out.println("Valid preference: " + preference);
                            count++;
                        } 
                    }
                    if (count == 3) {
                        int index = 0;
                        for (String contest : contestantList) {
                            if (preferenceList.contains(contest)) {
                                int indexnum = preferenceList.indexOf(contest);
                                if (indexnum == 0) {
                                    votes.set(index, votes.get(index) + 3);
                                } else if (indexnum == 1) {
                                    votes.set(index, votes.get(index) + 2);
                                } else {
                                    votes.set(index, votes.get(index) + 1);
                                }
                            }
                            index++;
                        }
                    }

                    
                    	
                 
                }
            }
            
            
            int maxIndex = votes.indexOf(Collections.max(votes));
            winnerList.put(contestantList.get(maxIndex), votes.get(maxIndex));
            winnerIndex++;// Add the winner to the winnerList
            System.out.println("--------"); // Add separator between region data lists
        }
        int temp =0;
        if (regions.size() == winnerList.size()) {
        	
        	for (Map.Entry<String, Integer> entry : winnerList.entrySet()) {
        	    System.out.println("Region " + regions.get(temp) + " Winner " + entry.getKey() + " Votes " + entry.getValue());
        	    temp++;
        	}
        

        } else {
            System.out.println("Error: Size mismatch between regions and winnerList.");
            System.out.println("Size of regions list: " + regions.size());
            System.out.println("Size of winnerList: " + winnerList.size());
        }
        System.out.println("Cheif winner");

    }
}
