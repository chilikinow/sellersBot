package com.chilikinow.sellers.bot.settings;

import lombok.extern.slf4j.Slf4j;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
public class BotData {

    public final static Path outResources;
    public final static String botName;
    public final static String botToken;
    public final static String botId;
    public final static String botPassword;
    public final static String bonusBaseURI;
    public final static String bonusUserName;
    public final static String bonusPassword;
    public static String readPromoInfoFileUrl;
    public static String downloadPromoInfoFileUrl;

    static {

        // todo
//        URI outResourcesURI = null;
//        try {
//            outResourcesURI = BotData.class.getResource( BotData.class.getSimpleName() + ".class" ).toURI();
//        } catch (URISyntaxException uriSyntaxException) {
//            log.info(uriSyntaxException.getMessage());
//        }
//        log.info("outResourcesURI {}", outResourcesURI);


        outResources = Paths.get("outResources");

        Path botSettingsPath = outResources.resolve("botSettings.properties");
        Properties botSettingsProperties = new Properties();
        try {
            botSettingsProperties.load(new FileReader(botSettingsPath.toFile()));
        } catch (IOException ioException) {
            log.info(ioException.getMessage());
        }

        botName = botSettingsProperties.getProperty("userNameBotAuthorization");
        botToken = botSettingsProperties.getProperty("tokenBotAuthorization");
        botId = botSettingsProperties.getProperty("idBotAuthorization");

        botPassword = "12345";
        
        bonusBaseURI = botSettingsProperties.getProperty("uriBonusCardSystem");
        bonusUserName = botSettingsProperties.getProperty("userNameBonusCardSystem");
        bonusPassword = botSettingsProperties.getProperty("passwordBonusCardSystem");
        
        readPromoInfoFileUrl = botSettingsProperties.getProperty("readUriPromoInfoFile");
        downloadPromoInfoFileUrl = botSettingsProperties.getProperty("downloadFileUriPromoInfoFile");

    }
}
