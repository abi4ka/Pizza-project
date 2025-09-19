import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //ArrayList<Client> list = new ArrayList<>();
        Map<String, Integer> globalLike = new HashMap<>();
        Map<String, Integer> globalNoLike = new HashMap<>();

        Map<String, Integer> result = new HashMap<>();

        ArrayList<Client> list = parseClientsFromFile("src\\main\\resources\\text2.txt");

        for (Client c : list) {
            for (String s : c.like) {
                globalLike.put(s, globalLike.getOrDefault(s, 0) + 1);
            }
            for (String s : c.noLike) {
                globalNoLike.put(s, globalNoLike.getOrDefault(s, 0) + 1);
            }
        }

        System.out.println("--- Like ---");
        for (String s : globalLike.keySet()) {
            System.out.println("Like: " + s + " " + globalLike.get(s));
        }
        System.out.println("--- No Like ---");
        for (String s : globalNoLike.keySet()) {
            System.out.println("NoLike: " + s + " " + globalNoLike.get(s));
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
}