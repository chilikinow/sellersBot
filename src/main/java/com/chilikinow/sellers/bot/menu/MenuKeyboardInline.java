package com.chilikinow.sellers.bot.menu;

import com.chilikinow.sellers.bot.info.Promo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class MenuKeyboardInline implements MenuKeyboard{

    @Override
    public ReplyKeyboard getStartMenu() {
        return createStartMenu();
    }

    @Override
    public ReplyKeyboard getInfoMenu() {
        return createInfoMenu();
    }

    @Override
    public ReplyKeyboard getMobileTVPromoMenu() {

        List<String> keyList = new ArrayList<>(Promo.getInstancePromoMobileTV().keySet());
        return createPromoMenu(keyList);
    }

    @Override
    public ReplyKeyboard getAppliancesMenu() {
        List<String> keyList = new ArrayList<>(Promo.getInstancePromoAppliances().keySet());
        return createPromoMenu(keyList);
    }





    public InlineKeyboardMarkup getMenu(String buttonName){
        String callbackData = buttonName;
        Map<String,String> map = new HashMap<>();
        map.put(buttonName, callbackData);
        return createMenu(map);
    }

    public InlineKeyboardMarkup getMenu(String buttonName, String callbackData){
        Map<String,String> map = new HashMap<>();
        map.put(buttonName, callbackData);
        return createMenu(map);
    }

    public InlineKeyboardMarkup getMenu(Map<String,String> map){
        return createMenu(map);
    }

//    private InlineKeyboardMarkup createMenu(List<List<String>> buttonList){
//
//
//        Map<String,String> map;
//
//
//
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        InlineKeyboardButton button;
//        List<InlineKeyboardButton> keyboardRow;
//
//        for (Map.Entry<String,String> entry : map.entrySet()) {
//            button = new InlineKeyboardButton();
//            button.setText(entry.getKey());
//            button.setCallbackData(entry.getValue());
//
//            keyboardRow = new ArrayList<>();
//            keyboardRow.add(button);
//            rowList.add(keyboardRow);
//        }
//
//        button = new InlineKeyboardButton();
//        button.setText("Главное меню");
//        button.setCallbackData("Главное меню");
//        keyboardRow = new ArrayList<>();
//        keyboardRow.add(button);
//        rowList.add(keyboardRow);
//
//        inlineKeyboardMarkup.setKeyboard(rowList);
//
//        return inlineKeyboardMarkup;
//    }

    private InlineKeyboardMarkup createMenu(Map<String,String> map){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardButton button;
        List<InlineKeyboardButton> keyboardRow;

        for (Map.Entry<String,String> entry : map.entrySet()) {
            button = new InlineKeyboardButton();
            button.setText(entry.getKey());
            button.setCallbackData(entry.getValue());

            keyboardRow = new ArrayList<>();
            keyboardRow.add(button);
            rowList.add(keyboardRow);
        }

        button = new InlineKeyboardButton();
        button.setText("Главное меню");
        button.setCallbackData("Главное меню");
        keyboardRow = new ArrayList<>();
        keyboardRow.add(button);
        rowList.add(keyboardRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createStartMenu(){

        List<String> buttonList = new ArrayList<>(StartMenu.menuList);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText(buttonList.get(0));
        button1.setCallbackData(buttonList.get(0));
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(buttonList.get(1));
        button2.setCallbackData(buttonList.get(1));
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(button1);
        keyboardRow.add(button2);
        rowList.add(keyboardRow);

        for (int i = 2; i < buttonList.size(); i++){
            button1 = new InlineKeyboardButton();
            button1.setText(buttonList.get(i));
            button1.setCallbackData(buttonList.get(i));
            keyboardRow = new ArrayList<>();
            keyboardRow.add(button1);
            rowList.add(keyboardRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createInfoMenu(){
        List<String> buttonList = new ArrayList<>(InfoMenu.menuList);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText(buttonList.get(0));
        button1.setCallbackData(buttonList.get(0));
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(buttonList.get(1));
        button2.setCallbackData(buttonList.get(1));
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(button1);
        keyboardRow.add(button2);
        rowList.add(keyboardRow);

        button1 = new InlineKeyboardButton();
        button1.setText(buttonList.get(2));
        button1.setCallbackData(buttonList.get(2));
        button2 = new InlineKeyboardButton();
        button2.setText(buttonList.get(3));
        button2.setCallbackData(buttonList.get(3));
        keyboardRow = new ArrayList<>();
        keyboardRow.add(button1);
        keyboardRow.add(button2);
        rowList.add(keyboardRow);

        button1 = new InlineKeyboardButton();
        button1.setText(buttonList.get(4));
        button1.setCallbackData(buttonList.get(4));
        keyboardRow = new ArrayList<>();
        keyboardRow.add(button1);
        rowList.add(keyboardRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createPromoMenu(List keyList){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        List<String> buttonList = new ArrayList<>(keyList);
        InlineKeyboardButton button;
        List<InlineKeyboardButton> keyboardRow;

        for (int i = 0; i < buttonList.size(); i++) {

            button = new InlineKeyboardButton();
            button.setText(buttonList.get(i));
            if (buttonList.get(i).length() > 30) {
                button.setCallbackData(buttonList.get(i).substring(0, 30));
            } else {
                button.setCallbackData(buttonList.get(i));
            }

            keyboardRow = new ArrayList<>();
            keyboardRow.add(button);
            rowList.add(keyboardRow);
        }

        button = new InlineKeyboardButton();
        button.setText("Главное меню");
        button.setCallbackData("Главное меню");
        keyboardRow = new ArrayList<>();
        keyboardRow.add(button);
        rowList.add(keyboardRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
