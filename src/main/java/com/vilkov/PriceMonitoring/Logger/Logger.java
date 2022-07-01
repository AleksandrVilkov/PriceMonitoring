package com.vilkov.PriceMonitoring.Logger;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class Logger {
    private static Logger instance;
    private static String pathForController;
    private static String pathForParser;
    private static String pathForDataBase;
    private static String rootPath;

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            Properties properties = getLoggerProperties();
            createDirectory(properties.getProperty("parserLogFolder"));
            createDirectory(properties.getProperty("dataStorageLogFolder"));
            createDirectory(properties.getProperty("controllersLogFolder"));
            pathForDataBase = properties.getProperty("rootFolder") + properties.getProperty("dataStorageLogFolder") + "/";
            pathForParser = properties.getProperty("rootFolder") + properties.getProperty("parserLogFolder") + "/";
            pathForController = properties.getProperty("rootFolder") + properties.getProperty("controllersLogFolder") + "/";
            rootPath = properties.getProperty("rootFolder");
            instance = new Logger();
        }
        return instance;
    }


    //TODO выделить ERROR красным
    public void save(MessageType messageType, String msg, Place place) {
        final String ANSI_RED = "\u001B[31m";
        switch (messageType) {
            case MSG -> saveMessage(msg, place);
            case ERROR -> saveMessage("!ERROR>>> " + msg, place);
        }
    }


//    private void saveJSON(String msg, JsonType jsonType) {
//        String fileName = jsonType.name() + "_" + getFileName(Place.CONTROLLER) + ".json";
//        String path = pathForController;
//        writeInFile(path, fileName, msg);
//    }

    private void writeInFile(String path, String fileName, String msg) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName, true));
            writer.write(msg + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveMessage(String msg, Place place) {
        String fileName = getFileName(place);
        String path = getPath(place);
        writeInFile(path,fileName,msg);
    }

    private String getPath(Place place) {
        String result;
        switch (place) {
            case CONTROLLER -> result = pathForController;
            case PARSER -> result = pathForParser;
            case DATASTORAGE -> result = pathForDataBase;
            default -> result = rootPath;
        }
        return result;
    }

    //TODO продумать имя файла
    private String getFileName(Place place) {
        String result;
        Date date = new Date();
        String stringDate = date.getDay()
                + "." + date.getMonth()
                + "_" + date.getHours()
                + ":" + date.getDate();
        switch (place) {
            case CONTROLLER -> result = "Controller_Trace_" + stringDate;
            case PARSER -> result = "Parser_Trace_" + stringDate;
            case DATASTORAGE -> result = "DB_Trace_" + stringDate;
            default -> result = rootPath;
        }
        return result;
    }

    private static void createDirectory(String path) {
        Properties properties = getLoggerProperties();
        File root = new File(properties.getProperty("rootFolder"));
        checkAndCreateRootFolder(root);
        File file = new File(properties.getProperty("rootFolder") + path);
        file.mkdirs();
    }

    private static Properties getLoggerProperties() {
        try {
            FileReader fileReader = tryGetFileReader();
            Properties properties = new Properties();
            properties.load(fileReader);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static FileReader tryGetFileReader() {
        FileReader fileReader;
        try {
            fileReader = new FileReader("./src/main/java/com/vilkov/PriceMonitoring/Logger/logger.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileReader;
    }

    private static void checkAndCreateRootFolder(File file) {
        if (file.isDirectory()) {
        } else {
            file.mkdirs();
        }
    }
}
