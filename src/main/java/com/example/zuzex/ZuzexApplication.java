package com.example.zuzex;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class ZuzexApplication {

    public static void main(String[] args) {
     /*   try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/zuzex", "postgres", "root");

            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml",
                    new ClassLoaderResourceAccessor(),
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation
                            (new JdbcConnection(connection)));
            liquibase.update("");

            connection.close();


        } catch (Exception e) {
            e.printStackTrace();
        }*/
        SpringApplication.run(ZuzexApplication.class, args);


    }

}
