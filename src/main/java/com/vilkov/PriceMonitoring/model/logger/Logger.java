package com.vilkov.PriceMonitoring.model.logger;

import java.io.*;
import java.util.Properties;

public class Logger {
    private final String fullPath;
    private final String fileName;

    //TODO вынести названия файлов и папок с логами в отдельный пропрерти файл. Сделать методы чтения этих имен. Переписать в местах использования
    public Logger(String folderName, String fileName) {
        Properties properties = getLoggerProperties();
        File root = new File(properties.getProperty("rootFolder") + "/" + folderName + "/");
        checkAndCreateRootFolder(root);
        fullPath = properties.getProperty("rootFolder") + "/" + folderName + "/";
        this.fileName = fileName;

    }

    public void save(String msg) {
        writeInFile(fullPath, fileName, msg);
    }

    private void writeInFile(String path, String fileName, String msg) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName, true));
            writer.write(msg + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties getLoggerProperties() {
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
            fileReader = new FileReader("./src/main/java/com/vilkov/PriceMonitoring/model/logger/logger.properties");
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
