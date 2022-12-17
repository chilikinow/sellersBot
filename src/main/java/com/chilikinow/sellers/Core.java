package com.chilikinow.sellers;

import com.chilikinow.sellers.bot.Bot;
import com.chilikinow.sellers.bot.settings.BotData;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.nio.file.Paths;

@Slf4j
public class Core {

    public static void main(String[] args) {

        BotData.init(Paths.get(args[0]));
//        BotData.init(Paths.get("C:\\workspace\\sellersBot\\outResources"));

        Bot bot = new Bot();

        try {

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
