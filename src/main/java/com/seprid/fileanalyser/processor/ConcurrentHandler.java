package com.seprid.fileanalyser.processor;

import com.seprid.fileanalyser.entity.LineObject;
import com.seprid.fileanalyser.service.LineObjectService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;


public class ConcurrentHandler extends RecursiveAction {

    private LineObjectService service;
    private List<File> files;
    private static final int THRESHOLD = 2;

    public ConcurrentHandler(List<File> files, LineObjectService service) {

        this.service = service;
        this.files = files;
    }

    @Override
    protected void compute() {
        if (files.size() > 1) {
            ForkJoinTask.invokeAll(createSubTasks());
        } else analiseFile(files.get(0));
    }

    private List<ConcurrentHandler> createSubTasks() {
        List<File> partOne = files.subList(0, files.size() / 2);
        List<File> partTwo = files.subList(files.size() / 2, files.size() - 1);

        List<ConcurrentHandler> subTasks = new ArrayList<>();
        subTasks.add(new ConcurrentHandler(partOne, service));
        subTasks.add(new ConcurrentHandler(partTwo, service));

        return subTasks;
    }

    private void analiseFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = reader.lines().collect(Collectors.toList());
            for (String line :
                    lines) {
                service.create(new LineObject(line, file.getName()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't found file " + file.getName() + " file path: " + file.getAbsolutePath());
        }
    }
}
