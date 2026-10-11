package com.untilDawn.Module;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    static final String DB_URL = "jdbc:sqlite:users.db";

    public static void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT," +
            "password TEXT," +
            "avatarPath TEXT," +
            "isGuest BOOLEAN," +
            "question TEXT," +
            "answer TEXT," +
            "score INTEGER," +
            "kills INTEGER," +
            "survivalTime INTEGER," +
            "hasWonLastGame BOOLEAN" +
            ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("User table checked/created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
