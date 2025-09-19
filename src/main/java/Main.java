import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ArrayList<Client> list = new ArrayList<>();
        Map<String, Integer> globalLike = new HashMap<>();
        Map<String, Integer> globalNoLike = new HashMap<>();

        Map<String, Integer> result = new HashMap<>();

        list.add(new Client());
        list.get(0).like.add("cheese");
        list.get(0).like.add("peppers");

        list.add(new Client());
        list.get(1).like.add("basil");
        list.get(1).noLike.add("pineapple");

        list.add(new Client());
        list.get(2).like.add("mushrooms");
        list.get(2).like.add("tomatoes");
        list.get(2).noLike.add("basil");

        for (Client c : list) {
            System.out.println(c.like.toString());
            System.out.println(c.noLike.toString());
        }

        for (Client c : list) {
            for (String s : c.like) {
                globalLike.put(s, globalLike.getOrDefault(s, 0) + 1);
            }
            for (String s : c.noLike) {
                globalNoLike.put(s, globalNoLike.getOrDefault(s, 0) + 1);
            }
        }

        for (String s : globalLike.keySet()) {
            System.out.println("Like: " + s + " " + globalLike.get(s));
        }
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

        for (String res : result.keySet()) {
            System.out.println("Result: " + res + " " + result.get(res));
        }
    }
}