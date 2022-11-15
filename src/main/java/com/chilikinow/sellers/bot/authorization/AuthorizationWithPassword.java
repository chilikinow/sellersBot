package com.chilikinow.sellers.bot.authorization;

import com.chilikinow.sellers.bot.settings.BotData;
import com.chilikinow.sellers.bot.Response;
import com.chilikinow.sellers.bot.commands.Start;

public class AuthorizationWithPassword {

    public static void authorizationUserWithPass(Long chatId, String messageText){
        int messageCounter = 0;
        boolean pass = false;

        if (messageText.equals("/start")){
            messageCounter = 1;
            pass = false;
        }

        if (messageCounter == 1) {

            Object replyMessage = Response.createTextMessage(chatId
                    ,new Start().create() + "\n\n\nВведите пароль:");
//            sendReply(replyMessage);

            messageCounter++;

            return;
        }

        //Финальная часть авторизации
        if (messageCounter == 2) {
            if (BotData.botPassword.equals(messageText)) {
                pass = true;
                Object replyMessage = Response.createTextMessage(chatId,
                        "Доступ Разрешен...\n\n");
//                sendReply(replyMessage);
                messageCounter++;
            }
            else {
                Object replyMessage = Response.createTextMessage(chatId,
                        "Пароль не от этого Бота, попробуйте другой...:\n");
//                sendReply(replyMessage);
                messageCounter = 2;
            }
            return;
        }
    }
}
