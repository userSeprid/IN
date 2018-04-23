package com.seprid.fileanalyser.processor;

import com.seprid.fileanalyser.service.LineObjectService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainLogic {

    private ConcurrentHandler handler;
    private List<File> filesList;

    {
        filesList = new ArrayList<>();
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

    public List<File> getFilesList() {
        return filesList;
    }
}
