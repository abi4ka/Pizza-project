import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> globalLike = new HashMap<>();
        Map<String, Integer> globalNoLike = new HashMap<>();

        Map<String, Integer> result = new HashMap<>();

        ArrayList<Client> list = parseClientsFromFile("src\\main\\resources\\text1.txt");

        for (Client c : list) {
            for (String s : c.like) {
                globalLike.put(s, globalLike.getOrDefault(s, 0) + 1);
            }
            for (String s : c.noLike) {
                globalNoLike.put(s, globalNoLike.getOrDefault(s, 0) + 1);
            }
        }

        for (String ingredient : globalLike.keySet()) {
            int likeScore = globalLike.getOrDefault(ingredient, 0);
            int dislikeScore = globalNoLike.getOrDefault(ingredient, 0);
            if (likeScore - dislikeScore > 0) {
                result.put(ingredient, likeScore - dislikeScore);
            }
        }

        System.out.println("--- RESULTS ---");
        for (String res : result.keySet()) {
            System.out.println("Result: " + res + " " + result.get(res));
        }

        saveResultToFile(result, "src\\main\\resources\\BestPizza.txt");
    }

    public static ArrayList<Client> parseClientsFromFile(String filePath) {
        ArrayList<Client> clients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                Client c = new Client();

                // Like
                String[] likeParts = line.trim().split(" ");
                int likeCount = Integer.parseInt(likeParts[0]);
                for (int i = 1; i <= likeCount; i++) {
                    if (i < likeParts.length) {
                        c.like.add(likeParts[i]);
                    }
                }

                // No like
                line = br.readLine();
                if (line != null) {
                    String[] noLikeParts = line.trim().split(" ");
                    int noLikeCount = Integer.parseInt(noLikeParts[0]);
                    for (int i = 1; i <= noLikeCount; i++) {
                        if (i < noLikeParts.length) {
                            c.noLike.add(noLikeParts[i]);
                        }
                    }
                }

                clients.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public static void saveResultToFile(Map<String, Integer> result, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            Set<String> ingredients = result.keySet();

            bw.write(String.valueOf(ingredients.size()));
            bw.write(" ");

            boolean first = true;
            for (String ing : ingredients) {
                if (!first) bw.write(" ");
                bw.write(ing);
                first = false;
            }
            bw.newLine();

            System.out.println("Result saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}