package com.chilikinow.sellers.bot.menu;

import com.chilikinov.sellers.bot.info.Promo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.*;

public class MenuKeyboardReply implements MenuKeyboard{

    @Override
    public ReplyKeyboard getStartMenu(){
        return createStartMenu();
    }

    @Override
    public ReplyKeyboard getInfoMenu(){
        return createInfoMenu();
    }

    @Override
    public ReplyKeyboard getMobileTVPromoMenu(){

        List<String> keyList = new ArrayList<>(Promo.getInstancePromoMobileTV().keySet());
        return createPromoMenu(keyList);
    }

    @Override
    public ReplyKeyboard getAppliancesMenu(){
        List<String> keyList = new ArrayList<>(Promo.getInstancePromoAppliances().keySet());
        return createPromoMenu(keyList);
    }

    private ReplyKeyboardMarkup createPromoMenu(List<String> mapKeyList){

        List<KeyboardRow> keyboard = new ArrayList<>();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);//размер клавиатуры адаптируется под количество клавиш
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрыть клавиатуру после использования
        replyKeyboardMarkup.setSelective(false);//персонолизация клавиатуры

        List<String> buttonList = new ArrayList<>(mapKeyList);
        buttonList.add("Назад");

        keyboard.clear();

        for (int i = 0; i < buttonList.size(); i++) {
            KeyboardRow keyboardRowBuffer = new KeyboardRow();
            keyboardRowBuffer.add(buttonList.get(i));
            keyboard.add(keyboardRowBuffer);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup createStartMenu(){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        replyKeyboardMarkup.setResizeKeyboard(false);//размер клавиатуры адаптируется под количество клавиш
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрыть клавиатуру после использования
        replyKeyboardMarkup.setSelective(false);//персонолизация клавиатуры

        keyboard.clear();

        List<String> buttonList = new ArrayList<>(StartMenu.menuList);

        KeyboardRow keyboardRowBuffer = new KeyboardRow();
        for (int i = 0; i < 2; i++) {
            keyboardRowBuffer.add(buttonList.get(i));
        }
        keyboard.add(keyboardRowBuffer);

        for (int i = 2; i < buttonList.size(); i++) {
            keyboardRowBuffer = new KeyboardRow();
            keyboardRowBuffer.add(buttonList.get(i));
            keyboard.add(keyboardRowBuffer);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup createInfoMenu(){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        replyKeyboardMarkup.setResizeKeyboard(false);//размер клавиатуры адаптируется под количество клавиш
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрыть клавиатуру после использования
        replyKeyboardMarkup.setSelective(false);//персонолизация клавиатуры

        keyboard.clear();

        List<String> buttonList = new ArrayList<>(InfoMenu.menuList);

        KeyboardRow keyboardRowBuffer = new KeyboardRow();
        keyboardRowBuffer.add(buttonList.get(0));
        keyboardRowBuffer.add(buttonList.get(1));
        keyboard.add(keyboardRowBuffer);
        keyboardRowBuffer = new KeyboardRow();
        keyboardRowBuffer.add(buttonList.get(2));
        keyboardRowBuffer.add(buttonList.get(3));
        keyboard.add(keyboardRowBuffer);
        keyboardRowBuffer = new KeyboardRow();
        keyboardRowBuffer.add(buttonList.get(4));
        keyboard.add(keyboardRowBuffer);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
