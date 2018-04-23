package com.seprid.fileanalyser;

import com.seprid.fileanalyser.DAO.LineObjectDAOImpl;
import com.seprid.fileanalyser.processor.MainLogic;
import com.seprid.fileanalyser.service.LineObjectService;
import com.seprid.fileanalyser.service.LineObjectServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FileAnalyserApplication{

	public static void main(String[] args) {
	    String s = "a.txt";


			Properties props = new Properties();
            try (FileInputStream in = new FileInputStream("application.properties")){
                props.load(in);
                Class.forName(props.getProperty("db.driver"));
                LineObjectService service = new LineObjectServiceImpl(new LineObjectDAOImpl(DriverManager.getConnection(
                        props.getProperty("db.url"),
                        props.getProperty("db.username"),
                        props.getProperty("db.password")
                )));
                MainLogic logic = new MainLogic();
                logic.init(s, service);
            } catch (FileNotFoundException e) {
                System.out.println("Program can't connect to DB");
            } catch (IOException e) {
                System.out.println("Incorrect properties");
            } catch (SQLException e) {
                System.out.println("Problem with connection to DB");
            } catch (ClassNotFoundException e) {
                System.out.println("DB driver not found");
            }

	}

}
