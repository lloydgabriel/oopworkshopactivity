package org.example.oopworkshopactivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    // 1. Transaction Pooler URL on port 6543 with SSL explicitly required
    private static final String URL = "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require";

    // 2. Your project's transaction pooler username
    private static final String USER = "postgres.nangqkfpwmtolmkvtbht";

    // 3. Try "rukiakuchiki@123" first. If it fails, try "rukiakuchiki123"
    private static final String PASSWORD = "rukiakuchiki@123";

    public static Connection getConnection() throws SQLException {
        System.out.println("DEBUG: Connecting via Supabase Transaction Pooler (Port 6543)...");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}