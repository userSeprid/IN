package com.seprid.fileanalyser;

import com.seprid.fileanalyser.service.LineObjectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainLogic {

    private ConcurrentHandler handler;
    private List<File> filesList;
    private String informationMessage = "Hello.\nPlease write path to text file or to directory that contains at least one file." +
            "\nIn case if subdirectories present, they also will checked.";

    {
        filesList = new ArrayList<>();
    }

    @Autowired
    public MainLogic(LineObjectService service) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(informationMessage);
        String mainPath = scanner.nextLine();
        if (!mainPath.isEmpty() && mainPath != null)init(mainPath, service);
        System.out.println("shit");
    }

    public void init(String mainPath, LineObjectService service) {
        getFilesList(mainPath);
        if (!filesList.isEmpty()) {
            handler = new ConcurrentHandler(filesList, service);
            handler.compute();
        }
    }

    private void getFilesList(String mainPath) {
        File currentFile = new File(mainPath);
        if (currentFile.isFile()) {
            filesList.add(currentFile);
        } else if (currentFile.isDirectory()) {
            for (File file :
                    currentFile.listFiles()) {
                getFilesList(file.getPath());
            }
        }
    }


}
