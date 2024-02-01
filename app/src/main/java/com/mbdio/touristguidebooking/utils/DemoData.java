package com.mbdio.touristguidebooking.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoData {

public static List<Map<String, Object>> getTouristGuidesData() {
        List<Map<String, Object>> touristGuidesList = new ArrayList<>();

        // Guide 1
        Map<String, Object> guide1 = createGuide(4.3, new String[]{"English", "Arabic"}, "Ahmed Hassan", "Certified guide with a passion for history and culture.", "Marrakech","+212611234567");
        touristGuidesList.add(guide1);

        // Guide 2
        Map<String, Object> guide2 = createGuide(4.5, new String[]{"French", "Spanish"}, "Fatima Amrani", "Friendly guide who loves showcasing the beauty of Rabat.", "Rabat","+212620987654");
        touristGuidesList.add(guide2);

        // Guide 3
        Map<String, Object> guide3 = createGuide(4.0, new String[]{"Arabic", "Berber"}, "Youssef El Khoury", "Enthusiastic guide with a love for exploring Tangier.", "Tangier","+212632345678");
        touristGuidesList.add(guide3);

        // Guide 4
        Map<String, Object> guide4 = createGuide(4.2, new String[]{"English", "French", "Arabic"}, "Lina El Fassi", "Experienced guide specializing in Casablanca's rich heritage.", "Casablanca","+212643210987");
        touristGuidesList.add(guide4);

        // Guide 5
        Map<String, Object> guide5 = createGuide(4.8, new String[]{"Arabic", "Spanish"}, "Karim Benali", "Passionate guide offering personalized tours in Fes.", "Fes","+212654567890");
        touristGuidesList.add(guide5);

        // Guide 6
        Map<String, Object> guide6 = createGuide(4.1, new String[]{"Berber", "French"}, "Amina Rahal", "Knowledgeable guide showcasing the beauty of Chefchaouen.", "Chefchaouen","+212665432109");
        touristGuidesList.add(guide6);

        // Guide 7
        Map<String, Object> guide7 = createGuide(4.4, new String[]{"Arabic", "English"}, "Nadia Mansouri", "Friendly guide offering unique experiences in Agadir.", "Agadir","+212676789012");
        touristGuidesList.add(guide7);

        // Guide 8
        Map<String, Object> guide8 = createGuide(4.6, new String[]{"Arabic", "Spanish"}, "Omar El Maghribi", "Certified guide with extensive knowledge of Essaouira.", "Essaouira","+212687654321");
        touristGuidesList.add(guide8);

        // Guide 9
        Map<String, Object> guide9 = createGuide(4.0, new String[]{"Arabic", "Berber"}, "Laila Ben Youssef", "Passionate about showing the wonders of Ouarzazate.", "Ouarzazate","+212698901234");
        touristGuidesList.add(guide9);

        // Guide 10
        Map<String, Object> guide10 = createGuide(4.7, new String[]{"Arabic", "French"}, "Hassan El Amrani", "Experienced guide with a deep love for the history of Meknes.", "Meknes","+212609876543");
        touristGuidesList.add(guide10);

        // Guide 11
        Map<String, Object> guide11 = createGuide(4.2, new String[]{"Arabic", "Spanish"}, "Sofia El Maghribi", "Passionate about showing the wonders of Agadir.", "Agadir","+212731234567");
        touristGuidesList.add(guide11);

        // Guide 12
        Map<String, Object> guide12 = createGuide(4.9, new String[]{"Berber", "French"}, "Khalid Rahal", "Knowledgeable guide showcasing the beauty of Tetouan.", "Tetouan","+212742345678");
        touristGuidesList.add(guide12);

        // Guide 13
        Map<String, Object> guide13 = createGuide(4.4, new String[]{"Arabic", "English"}, "Aicha Mansouri", "Friendly guide offering unique experiences in Marrakech.", "Marrakech","+212753456789");
        touristGuidesList.add(guide13);

        // Guide 14
        Map<String, Object> guide14 = createGuide(4.1, new String[]{"Arabic", "French"}, "Omar Benali", "Certified guide with extensive knowledge of Tangier.", "Tangier","+212764567890");
        touristGuidesList.add(guide14);

        // Guide 15
        Map<String, Object> guide15 = createGuide(4.6, new String[]{"Arabic", "Spanish"}, "Fatima El Fassi", "Passionate guide offering personalized tours in Casablanca.", "Casablanca","+212775678901");
        touristGuidesList.add(guide15);

        return touristGuidesList;
        }

private static Map<String, Object> createGuide(double rating, String[] languages, String name, String bio, String city,String phone) {
        Map<String, Object> guide = new HashMap<>();
        guide.put("rating", rating);
        guide.put("languages", languages);
        guide.put("name", name);
        guide.put("bio", bio);
        guide.put("city", city);
        guide.put("phone", phone);
        return guide;
        }


        }