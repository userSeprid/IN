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

/**
 * This class is designed for concurrent processing of files.
 */
public class ConcurrentHandler extends RecursiveAction {

    private LineObjectService service;
    private List<File> files;
    //Constant flag that represent limit of elements for array
    private static final int THRESHOLD = 2;

    /**
     * Default constructor. Set required parameters.
     * @param files <code>java.util.List</code> of files that will be processed.
     * @param service <code>com.seprid.fileanalyser.service.LineObjectService</code> that will be use for persistence operations.
     * @see LineObjectService
     */
    public ConcurrentHandler(List<File> files, LineObjectService service) {

        this.service = service;
        this.files = files;
    }

    /**
     * Method check size of array that must be processed,
     * if size lover that constant flag then private void analiseFile(File file) will be launched directly,
     * in other case array will be split on two arrays and each of them will be an argument for new <code>ConcurrentHandler</code> object.
     * If size of array more than <code>THRESHOLD</code> value then list will be processed with usage of concurrent api(Fork/Join Framework).
     */
    @Override
    protected void compute() {
        if (files.size() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubTasks());
        } else analiseFile(files.get(0));
    }

    /**
     * Helper method for <code>protected void compute()</code>. It split array and prepare new list for concurrent processing.
     * @return prepared for concurrent processing list that contain 2 elements, each of them is instance of <code>ConcurrentHandler</code> class.
     */
    private List<ConcurrentHandler> createSubTasks() {
        List<File> partOne = files.subList(0, files.size() / 2);
        List<File> partTwo = files.subList(files.size() / 2, files.size() - 1);

        List<ConcurrentHandler> subTasks = new ArrayList<>();
        subTasks.add(new ConcurrentHandler(partOne, service));
        subTasks.add(new ConcurrentHandler(partTwo, service));

        return subTasks;
    }

    /**
     * Method perform splitting file by lines.
     * Each <code>com.seprid.fileanalyser.entity.LineObject</code> object after creation collect all required statistics and then persist to DB.
     * @param file Instance of file that will be analysed.
     */
    private void analiseFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = reader.lines().collect(Collectors.toList());
            for (String line :
                    lines) {
                int id = service.create(new LineObject(line, file.getName()));
                System.out.println("New line created. ID:\'" + id + "\'");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't found file " + file.getName() + " file path: " + file.getAbsolutePath());
        }
    }
}
