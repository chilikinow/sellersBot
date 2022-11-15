package com.chilikinow.sellers;

import com.chilikinow.sellers.bot.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Core {

    public static String resourcesRoad;

    public static void main(String[] args) {

        if (args.length != 0)
            resourcesRoad = args[0];
        else
            resourcesRoad = "";

        Bot bot = new Bot();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
