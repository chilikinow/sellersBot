package com.chilikinow.sellers.bot;

import com.chilikinow.sellers.bot.processing.ProcessingUserMessage;
import com.chilikinow.sellers.bot.processing.Response;
import com.chilikinow.sellers.bot.settings.BotData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Bot extends TelegramLongPollingBot {

    private String botName;
    private String botToken;
    private String botPassword;

    private Message message;
    private String userName;
    private Long chatId;
    private String messageText;
    private boolean pass;
    private List<DeleteMessage> deleteMessageList;
    private boolean canDeleteMessageList;

    {
        botName = BotData.botName;
        botToken = BotData.botToken;
        botPassword = BotData.botPassword;

        this.pass = false;
        deleteMessageList = new LinkedList<>();
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            this.userName = callbackQuery.getFrom().getUserName();
            this.chatId = callbackQuery.getFrom().getId();
            this.messageText = callbackQuery.getData();
        }else {
            this.message = update.getMessage();
            this.userName = this.message.getFrom().getUserName();
            this.chatId = this.message.getFrom().getId();
            this.messageText = this.message.getText();
        }

        if (this.userName == null){
            this.userName = "UNKNOWN_User";
        }

        this.pass = true; //new AuthorizationWithUsername().pass(this.userName);

        if (this.pass) {
            Object replyMessage =  new ProcessingUserMessage().searchAnswer(this.chatId, this.userName, this.messageText);
            sendReply(replyMessage);
        } else {
            Object replyMessage = Response.createTextMessage(chatId
                    ,"У Вас нет доступа к данной системе."
            + "\n\n"
            + "Для получения доступа просим отправить Ваше Имя Пользователя (@UserName), Вашему КД.");
            sendReply(replyMessage);
        }
    }

    public void sendReply(Object reply){

        Message sentOutMessage = new Message();

        if (reply instanceof List){

           for (Object replyObject: (List)reply){
               sendReply(replyObject);
           }
            return;
        }

        try {
            if (reply instanceof SendDocument)
                sentOutMessage = execute((SendDocument) reply);
            if (reply instanceof SendMessage)
                sentOutMessage = execute((SendMessage) reply);
            if (reply instanceof SendPhoto)
                sentOutMessage = execute((SendPhoto) reply);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        if (this.canDeleteMessageList){
            deleteMessageList = deleteMessageList.stream()
                    .peek(deleteMessage -> {
                        if (Long.valueOf(deleteMessage.getChatId()).equals(this.chatId)) {
                            try {
                                execute(deleteMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .filter(deleteMessage -> !Long.valueOf(deleteMessage.getChatId()).equals(this.chatId))
                    .collect(Collectors.toList());
            this.canDeleteMessageList = false;
        }

        if (!sentOutMessage.hasReplyMarkup()) {
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(String.valueOf(sentOutMessage.getChatId()));
            deleteMessage.setMessageId(sentOutMessage.getMessageId());
            deleteMessageList.add(deleteMessage);
        }else{
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(String.valueOf(sentOutMessage.getChatId()));
            deleteMessage.setMessageId(sentOutMessage.getMessageId());
            deleteMessageList.add(deleteMessage);
            canDeleteMessageList = true;
        }



    }
}