package com.untilDawn.Module;
import java.io.*;
import java.util.ArrayList;

public class UserJsonIO {
    public void saveUsersToJson(ArrayList<User> users, String filePath) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.isGuest()) continue; // skip guests

            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"username\": \"").append(user.getUsername()).append("\",\n");
            jsonBuilder.append("    \"kills\": ").append(user.getKills()).append(",\n");
            jsonBuilder.append("    \"score\": ").append(user.getScore()).append(",\n");
            jsonBuilder.append("    \"survivalTime\": ").append(user.getSurvivalTime()).append("\n");
            jsonBuilder.append("  }");

            if (i < users.size() - 1) {
                jsonBuilder.append(",\n");
            } else {
                jsonBuilder.append("\n");
            }
        }

        jsonBuilder.append("]");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonBuilder.toString());
            System.out.println("User stats saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
