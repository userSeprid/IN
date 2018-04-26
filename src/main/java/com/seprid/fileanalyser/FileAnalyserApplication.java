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
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/dev",
                            "root", "root")){
                System.out.println("flag");
                LineObjectService service = new LineObjectServiceImpl(
                        new LineObjectDAOImpl(connection));
                System.out.println("flag2");
                MainLogic logic = new MainLogic();
                System.out.println("flag3");
                logic.init(args[0], service);
                System.out.println("flag4");
            } catch (SQLException e) {
                System.out.println("Problem with connection to DB");
                e.printStackTrace();
            }

    }

}
