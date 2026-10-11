package com.untilDawn.Module;
import java.sql.*;
import java.util.ArrayList;

import com.untilDawn.Module.User;

public class UserDataBase {
    private static final String DB_URL = "jdbc:sqlite:users.db";


    public static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "username TEXT PRIMARY KEY," +
                "password TEXT," +
                "avatarPath TEXT," +
                "kills INTEGER," +
                "score INTEGER," +
                "survivalTime INTEGER" +
                ")";
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void saveUser(User user) {
        String sql = "INSERT INTO users(username, password, avatarPath, isGuest, question, answer, score, kills, survivalTime, hasWonLastGame) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getAvatarPath());
            pstmt.setBoolean(4, user.isGuest());
            pstmt.setString(5, user.getQuestion());
            pstmt.setString(6, user.getAnswer());
            pstmt.setInt(7, user.getScore());
            pstmt.setInt(8, user.getKills());
            pstmt.setInt(9, user.getSurvivalTime());
            pstmt.setBoolean(10, user.isHasWonLastGame());

            pstmt.executeUpdate();
            System.out.println("User saved to database.");

        } catch (SQLException e) {
            e.printStackTrace();
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

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

}
