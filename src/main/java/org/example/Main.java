package org.example;

import java.util.*;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {

        String[] routes = new String[1000];
        for (int i = 0; i < routes.length; i++) {
            routes[i] = generateRoute("RLRFR", 100);
        }


        for (int i = 0; i < routes.length; i++) {
            char[] chars1 = routes[i].toCharArray();
            new Thread(() -> {
                int r = 0;
                int rTemporary = 1;
                int rTotal = 0;
                for (int j = 0; j < 99; j++) {
                    if (chars1[j] == 'R') r++;
                    if (chars1[j] == 'R' && chars1[j + 1] == 'R') {
                        rTemporary++;
                    } else rTemporary = 1;
                    if (rTemporary > rTotal) {
                        rTotal = rTemporary;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(r) == false) {
                        sizeToFreq.put(r, 1);
                    } else if (sizeToFreq.containsKey(r) == true)
                        sizeToFreq.put(r, sizeToFreq.get(r) + 1);
                }
            }).start();
        }
        System.out.println(sizeToFreq);

        int maxValue = 0;
        int key = 0;
        for (int i : sizeToFreq.keySet()) {
            if (sizeToFreq.get(i) > maxValue) {
                key = i;
                maxValue = sizeToFreq.get(i);
            }
        }
        System.out.println(key);
        System.out.println(maxValue);
        System.out.println("Самое частое количество повторений " + key + " (встретилось " + maxValue + " раз)");
        System.out.println("Другие размеры:");

        for (int i : sizeToFreq.keySet()) {
            if (sizeToFreq.get(i) != maxValue) System.out.println("- " + i + " (" + sizeToFreq.get(i) + " раз)");
        }

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
