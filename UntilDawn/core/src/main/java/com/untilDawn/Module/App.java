package com.untilDawn.Module;

import java.sql.*;
import java.util.ArrayList;
import java.io.*;
import java.util.ArrayList;

import static com.untilDawn.Module.DatabaseInitializer.DB_URL;

public class App {
//    private static final String DB_URL = ;
    private static App app;
    private ArrayList<User> users;
    private User currentUser;
    private User guestUser;
    private int tileWidth ;
    private int tileHeight ;

    private App() {
//        UserJsonIO.saveUsersToJson(users, "user_stats.json");
        users = new ArrayList<>();
        loadUsersFromJson("user_stats.json");

//        UserDataBase.initDatabase(); // create table if not exists
//        users = UserDataBase.loadUsers();

    }

    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isUsernameTaken(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getGuestUser() {
        return guestUser;
    }

    public void setGuestUser(User guestUser) {
        this.guestUser = guestUser;
    }

    public void removeUser(User user) {
        for (User user1 : users) {
            if(user1.getUsername().equals(user.getUsername())) {
                users.remove(user1);
                break;
            }
        }
    }

    public void removeUserWithNoQuestion(){
        for (User user : users) {
            if(user.getQuestion() == null) {
                users.remove(user);
                break;
            }
        }
    }

    public void addQuestionForUser(String question , String answer) {
        for (User user : users) {
            if(user.getQuestion() == null) {
                user.setQuestion(question);
                user.setAnswer(answer);
            }
        }
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }



//     Read user data from JSON file and load into App
    public void loadUsersFromJson(String filePath) {
        StringBuilder json = new StringBuilder();

        // Read file into string
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Parse JSON
        String content = json.toString().trim();
        if (content.isEmpty() || content.equals("[]")) return;

        content = content.substring(1, content.length() - 1); // remove [ and ]
        String[] entries = content.split("\\},\\{");

        for (String entry : entries) {
            entry = entry.replace("{", "").replace("}", "").replace("\"", "");
            String[] fields = entry.split(",");

            String username = "";
            String password = "";
            String avatarPath = "";
            int kills = 0, score = 0, survivalTime = 0;

            for (String field : fields) {
                String[] parts = field.split(":");
                if (parts.length != 2) continue;
                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "username": username = value; break;
                    case "kills": kills = Integer.parseInt(value); break;
                    case "score": score = Integer.parseInt(value); break;
                    case "survivalTime": survivalTime = Integer.parseInt(value); break;
                    case "password": password = value; break;
                    case "avatarPath": avatarPath = value; break;
                }
            }

            User user = new User(username, "", ""); // You can improve this with stored avatars/passwords if desired
            user.setKills(kills);
            user.setScore(score);
            user.setSurvivalTime(survivalTime);
            user.setPassword(password);
            user.setAvatarPath(avatarPath);
            this.users.add(user);
        }
    }

    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("avatarPath")
                );
                user.setKills(rs.getInt("kills"));
                user.setScore(rs.getInt("score"));
                user.setSurvivalTime(rs.getInt("survivalTime"));

                try { user.setQuestion(rs.getString("question")); } catch (SQLException ignored) {}
                try { user.setAnswer(rs.getString("answer")); } catch (SQLException ignored) {}
                try { user.setHasWonLastGame(rs.getBoolean("hasWonLastGame")); } catch (SQLException ignored) {}

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }



}
