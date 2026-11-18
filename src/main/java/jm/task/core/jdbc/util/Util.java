package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/pp_jdbc_hibernate";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "rcuj2007";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                configuration.setProperty("hibernate.connection.url", DB_URL);
                configuration.setProperty("hibernate.connection.username", DB_USERNAME);
                configuration.setProperty("hibernate.connection.password", DB_PASSWORD);
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

                configuration.setProperty("hibernate.show_sql", "true");
                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Ошибка создания SessionFactory");
            }
        }
        return sessionFactory;
    }
}
