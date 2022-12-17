package com.chilikinow.sellers.bot.settings;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

@Slf4j
public class BotData {

    public static Path outResources;
    public static String botName;
    public static String botToken;
    public static String botId;
    public static String botPassword;
    public static String promoFileName;
    public static String bonusBaseURI;
    public static String bonusUserName;
    public static String bonusPassword;
    public static String readPromoInfoFileUrl;
    public static String downloadPromoInfoFileUrl;

    @SneakyThrows
    public static void init(Path mainResources){

        if (mainResources == null){
            log.error("Рессурсы для запуска бота не найдены");
        }

        outResources = mainResources;
        Path botSettingsPath = outResources.resolve("botSettings.properties");
        Properties botSettingsProperties = new Properties();
        try {
            botSettingsProperties.load(new FileReader(botSettingsPath.toFile()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        botName = botSettingsProperties.getProperty("userNameBotAuthorization");
        botToken = botSettingsProperties.getProperty("tokenBotAuthorization");
        botId = botSettingsProperties.getProperty("idBotAuthorization");

        botPassword = "12345";

        promoFileName = new String(botSettingsProperties.getProperty("promoFileName").getBytes("windows-1251"), "UTF-8");
        bonusBaseURI = botSettingsProperties.getProperty("uriBonusCardSystem");
        bonusUserName = botSettingsProperties.getProperty("userNameBonusCardSystem");
        bonusPassword = botSettingsProperties.getProperty("passwordBonusCardSystem");
        
        readPromoInfoFileUrl = botSettingsProperties.getProperty("readUriPromoInfoFile");
        downloadPromoInfoFileUrl = botSettingsProperties.getProperty("downloadFileUriPromoInfoFile");

    }
}
