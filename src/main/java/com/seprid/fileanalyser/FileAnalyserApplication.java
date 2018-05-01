package com.seprid.fileanalyser;

import com.seprid.fileanalyser.DAO.LineObjectDAOImpl;
import com.seprid.fileanalyser.processor.MainLogic;
import com.seprid.fileanalyser.service.LineObjectService;
import com.seprid.fileanalyser.service.LineObjectServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FileAnalyserApplication{
    /**
     * 'Main' method that start an app.
     * Contain important configuration.
     * @param args console input
     */
	public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/dev",
                            "root", "root")){
                LineObjectService service = new LineObjectServiceImpl(
                        new LineObjectDAOImpl(connection));
                MainLogic logic = new MainLogic();

            /**Here is very interesting place.
             * The project works okay if I run it from my IDE,
             * but when I pack it ito jar file it fail.
             * Honestly  spend lots of time to find out why its happen,
             * but no any success so I let it like this (manual input of path to file)
             */
            logic.init("a.txt", service);
            } catch (SQLException e) {
                System.out.println("Problem with connection to DB");
                e.printStackTrace();
            }

    }

}
