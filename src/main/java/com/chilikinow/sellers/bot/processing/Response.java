package com.chilikinow.sellers.bot.processing;

import com.chilikinow.sellers.bot.menu.MenuKeyboardInline;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import java.io.File;

public class Response {

    public static SendMessage createTextMessage(Long chatId, String text){

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(false);
        sendMessage.setText(text);

        return sendMessage;
    }

    public static SendPhoto createPhotoMessage(Long chatId, String path){

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setPhoto(new InputFile(new File(path)));

        return sendPhoto;
    }

    public static SendPhoto createPhotoMessage(Long chatId, String text, String path){

        SendPhoto sendPhoto = createPhotoMessage(chatId, path);
        sendPhoto.setCaption(text);

        return sendPhoto;
    }

    public static SendDocument createDocumentMessage(Long chatId, String path){

        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId.toString());
        sendDocument.setDocument(new InputFile(new File(path)));
        return sendDocument;
    }

    public static SendDocument createDocumentMessage(Long chatId, String text, String path){

        SendDocument sendDocument = createDocumentMessage(chatId, path);
        sendDocument.setCaption(text);

        return sendDocument;
    }

    public static SendMessage createTextMessageWithKeyboard(Long chatId, String text, String buttonName, String callbackData){

        SendMessage sendMessage = createTextMessage(chatId, text);
        sendMessage.setReplyMarkup(new MenuKeyboardInline().getMenu(buttonName, callbackData));

        return sendMessage;
    }

    public static SendMessage createTextMessageWithKeyboard(Long chatId, String text, TypeKeyboard typeKeyboard){

        SendMessage sendMessage = createTextMessage(chatId, text);

        if (typeKeyboard == TypeKeyboard.START) {
            sendMessage.setReplyMarkup(new MenuKeyboardInline().getStartMenu());
            return sendMessage;
        }
        if (typeKeyboard == TypeKeyboard.INFO) {
            sendMessage.setReplyMarkup(new MenuKeyboardInline().getInfoMenu());
            return sendMessage;
        }
        if (typeKeyboard == TypeKeyboard.PROMO_MOBILE_TV){
            sendMessage.setReplyMarkup(new MenuKeyboardInline().getMobileTVPromoMenu());
            return sendMessage;
        }
        if (typeKeyboard == TypeKeyboard.PROMO_APPLIANCES){
            sendMessage.setReplyMarkup(new MenuKeyboardInline().getAppliancesMenu());
            return sendMessage;
        }

        return sendMessage;
    }

    public enum TypeKeyboard{
        START,
        INFO,
        PROMO_APPLIANCES,
        PROMO_MOBILE_TV
    }
}
