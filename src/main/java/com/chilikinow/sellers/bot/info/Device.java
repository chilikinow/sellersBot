package com.chilikinow.sellers.bot.info;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.chilikinow.sellers.bot.settings.BotData;
import org.apache.commons.io.FilenameUtils;

public class Device {

    private List<String> categoryDeviceList;

    public List<String> getCategoryDeviceList(){
        Path directory = BotData.outResources.resolve("categoryDeviceForFind.txt");
        List<String> tempCategoryList = new ArrayList<>();
        try {
            tempCategoryList = new ArrayList<>(Files.readAllLines(directory));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.categoryDeviceList = new ArrayList<>();
        for (String tempCategoryName: tempCategoryList){
            if (!tempCategoryName.equals("")) {
                this.categoryDeviceList.add(tempCategoryName.toLowerCase(Locale.ROOT));
            }
        }
        return categoryDeviceList;
    }

    public List<Path> findInfo(String messageText, Path directory){
        messageText = messageText
                .toLowerCase(Locale.ROOT)
                .replaceAll(" ", "")
                .replaceAll("galaxy", "")
                .replaceAll("samsung", "")
                .replaceAll("-", "")
                .replaceAll("_", "")
                .replaceAll("plus", "\\+")
                .replace("+", "\\+");
        List<Path> deviceInfoFilesList = new ArrayList<>();
        try {
            deviceInfoFilesList = Files.walk(directory)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Path> resultDeviceInfoList = new ArrayList<>();
        for (Path deviceInfoFile : deviceInfoFilesList){
            String tempDeviceInfoFile = deviceInfoFile.getFileName().toString();
            tempDeviceInfoFile = tempDeviceInfoFile
                    .toLowerCase(Locale.ROOT)
                    .replaceAll(" ", "")
                    .replace("-", "")
                    .replace("_", "");
            Pattern pattern = Pattern.compile(messageText);
            Matcher matcher = pattern.matcher(tempDeviceInfoFile);
            if (matcher.find()){
                resultDeviceInfoList.add(deviceInfoFile);
            }
        }
        return resultDeviceInfoList;
    }

    public List<String> getFilesName(Path directory){
        List<Path> filesList = new ArrayList<>();
        try {
            filesList =  Files.walk(directory)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> filesNamesList = new ArrayList<>();
        for(Path deviceInfo : filesList){
            String bufferFileName = deviceInfo.getFileName().toString();
//            bufferFileName = bufferFileName.replaceFirst("[.][^.]+$", "");
            bufferFileName = FilenameUtils.removeExtension(bufferFileName);
            if (!bufferFileName.equals(""))
                filesNamesList.add(bufferFileName);
        }
        return filesNamesList;
    }
}
