package com.seprid.tests;

import com.seprid.fileanalyser.entity.LineObject;
import org.junit.Assert;
import org.junit.Test;

public class LineObjectTest {

    private static final String line = "Luxoft is a global IT service provider of innovative technology " +
            "solutions that delivers measurable business outcomes to multinational companies. " +
            "Its offerings encompass strategic consulting, custom software development services, " +
            "and digital solution engineering.";
    private static final String longestWord = "multinational";
    private static final String shorestWord = "a";
    private static final int averageWordLength = 8;
    private static final String containerName = "lead-container";
    private static final int lineLength = 262;
    private LineObject object;

    private void init() {
        object = new LineObject(line, containerName);
    }

    @Test
    public void creationTest() {
        init();
        Assert.assertNotNull(object);
    }

    @Test
    public void valueGenerationTest() {
        init();
        Assert.assertEquals("Proper generation of \'Longest word\'", longestWord, object.getLongestWord().toLowerCase());
        Assert.assertEquals("Proper generation of \'Shortest word\'", shorestWord, object.getShortestWord().toLowerCase());
        Assert.assertEquals("Proper generation of \'Average Word-Length\'", averageWordLength, object.getAverageWordLength());
        Assert.assertEquals("Proper generation of \'Line Length\'", lineLength, object.getLineLength());
    }

    @Test
    public void valueSavingTest() {
        init();
        Assert.assertEquals("Proper saving of \'Container Name\' value", containerName, object.getContainerName());
        Assert.assertEquals("Proper saving of \'value\' parameter", line, object.getValue());
    }
}
