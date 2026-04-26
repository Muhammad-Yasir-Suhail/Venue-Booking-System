package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Using your exact connection details with Trusted Connection (Windows Authentication)
    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=event_booking_system;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
    private static final String USERNAME = "";  // Empty for Windows Authentication
    private static final String PASSWORD = "";  // Empty for Windows Authentication
    
    private static Connection connection = null;
    
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Database connected successfully!");
            }
        } catch (Exception e) {
            System.err.println("❌ Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
    
    public static void testConnection() {
        System.out.println("🔗 Testing connection to: localhost\\SQLEXPRESS03");
        System.out.println("📋 Using Windows Authentication...");
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("🎉 SUCCESS! Connected to SQL Server");
                
                // Test if we can create database
                System.out.println("🔄 Creating database if not exists...");
                conn.createStatement().executeUpdate("IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'event_booking_system') CREATE DATABASE event_booking_system");
                System.out.println("✅ Database ready!");
                
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("💥 FAILED: " + e.getMessage());
        }
    }
}