package com.chilikinow.sellers.bot.commands;

import com.chilikinov.sellers.bot.info.Device;

import java.nio.file.Path;
import java.util.List;

public class Info {

    public String create(String heading, Path directory, String ending) {

        StringBuilder response = new StringBuilder();
        response.append(heading);
        List<String> productNamesList = new Device().getFilesName(directory);

        for (String mobileName: productNamesList){
            response.append(mobileName + "\n");
        }

        response.append(ending);

        return response.toString();
    }
}
