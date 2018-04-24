package com.seprid.fileanalyser.entity;

import java.util.StringTokenizer;


/**
 * Entity that represent an entry in DB.
 */
public class LineObject {


    private int lineID;
    private String value;
    private String longestWord;
    private String shortestWord;
    private int averageWordLength;
    // Name of file that contains line
    private String containerName;

    /**
     * The default constructor that take only 2 parameters, other is generating by this object.
     * @param value Content of line that will be processed.
     * @param containerName Name of file that contain this line.
     */
    public LineObject(String value, String containerName) {
        this.value = value;
        this.containerName = containerName;
        longestWord = "";
        shortestWord = value;
        averageWordLength = 0;

        StringTokenizer tokenizer = new StringTokenizer(value, " ");
        String[] tokens = new String [tokenizer.countTokens()];
        for (int i = 0; i < tokens.length - 1; i++) {
            String currentToken = tokenizer.nextToken();
            tokens[i] = currentToken;
            if (currentToken.length() > longestWord.length()) longestWord = currentToken;
            if (currentToken.length() < shortestWord.length()) shortestWord = currentToken;
            averageWordLength =+ currentToken.length();
        }
        averageWordLength = averageWordLength / tokenizer.countTokens();
    }

    public LineObject() {
    }

    public int getLineID() {
        return lineID;
    }

    public void setLineID(int lineID) {
        this.lineID = lineID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLongestWord() {
        return longestWord;
    }

    public void setLongestWord(String longestWord) {
        this.longestWord = longestWord;
    }

    public String getShortestWord() {
        return shortestWord;
    }

    public void setShortestWord(String shortestWord) {
        this.shortestWord = shortestWord;
    }

    public int getAverageWordLength() {
        return averageWordLength;
    }

    public void setAverageWordLength(int averageWordLength) {
        this.averageWordLength = averageWordLength;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }
}
