package com.chilikinow.sellers.bot.commands;

import com.chilikinov.sellers.bot.settings.BotData;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    public String create() {

        Path phonesInfoPropertiesFile = BotData.outResources.resolve("phonesInfo.properties");

        Properties phonesInfoProperties = new Properties();
        try {
            phonesInfoProperties.load(new FileReader(phonesInfoPropertiesFile.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();

        for (String name : phonesInfoProperties.stringPropertyNames()) {
            map.put(name, phonesInfoProperties.getProperty(name));
        }

        List<String> seviceInfoStringList =  new ArrayList<>();

        Path serviceInfoFile = BotData.outResources.resolve("serviceInfo.txt");

        try {
            seviceInfoStringList = new ArrayList<>(Files.readAllLines(serviceInfoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder response = new StringBuilder();

        for (String serviceInfoLine: seviceInfoStringList) {
            response.append(serviceInfoLine + "\n");
        }

        response.append("Телефоны Поддержки:\n\n");
        for (Map.Entry<String, String> entry: map.entrySet()) {
            response.append(entry.getKey()+ ": ")
                    .append(entry.getValue()+"\n");
        }
        return response.toString().trim();

    }
}
