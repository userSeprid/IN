package com.seprid.fileanalyser.processor;

import com.seprid.fileanalyser.service.LineObjectService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class analise prompt from command line.
 * If as input program receive path to directory then content of directory and it subdirectories will be analysed
 * In other case only specified file will be analysed
 * @see ConcurrentHandler
 */
public class MainLogic {

    private ConcurrentHandler handler;
    private List<File> filesList;

    {
        filesList = new ArrayList<>();
    }

    /**
     * This method invoke helper method, validate result and in case of success invoke <code>com.seprid.fileanalyser.processor.ConcurrentHandler</code> for analise.
     * @param mainPath path to file/directory
     * @param service <code>com.seprid.fileanalyser.service.LineObjectService</code> that used as parameter for  <code>ConcurrentHandler</code>
     * @see ConcurrentHandler
     */
    public void init(String mainPath, LineObjectService service) {
        filesList = getFilesList(mainPath);
        if (!filesList.isEmpty()) {
            handler = new ConcurrentHandler(filesList, service);
            handler.compute();
        }
    }

    /**
     * Helper method that lookup a files, using a specified path (if it's a directory)
     * @param mainPath specified path
     */
    public List<File> getFilesList(String mainPath) {
        File currentFile = new File(mainPath);
        List<File> files = new ArrayList<>();
        if (currentFile.isFile()) {
            files.add(currentFile);
        } else if (currentFile.isDirectory()) {
            for (File file :
                    currentFile.listFiles()) {
                getFilesList(file.getPath());
            }
        }
        return files;
    }

    public List<File> getFilesList() {
        return filesList;
    }
}
