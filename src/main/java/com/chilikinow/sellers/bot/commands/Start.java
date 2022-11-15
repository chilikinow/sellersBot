package com.chilikinow.sellers.bot.commands;

import com.chilikinov.sellers.bot.settings.BotData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Start {

    public String create() {

        List<String> seviceInfoStringList =  new ArrayList<>();

        Path serviceInfoFile = BotData.outResources.resolve("startInfo.txt");

        try {
            seviceInfoStringList = new ArrayList<>(Files.readAllLines(serviceInfoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder response = new StringBuilder();

        for (String serviceInfoLine: seviceInfoStringList) {
            response.append(serviceInfoLine + "\n");
        }

        return response.toString().trim();
    }

}
