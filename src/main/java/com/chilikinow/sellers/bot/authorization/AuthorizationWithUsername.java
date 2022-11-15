package com.chilikinow.sellers.bot.authorization;

import com.chilikinow.sellers.bot.settings.BotData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AuthorizationWithUsername {

    private static List<String> usernamelist;

    static {
        usernamelist = new ArrayList<>();
    }


    public static void setUsernameToListFOrTest(String ...usernames){
        if (usernames.length > 0)
            for (String username: usernames)
                usernamelist.add(username);
    }

    public static void removeUsernameToList(String ...usernames){
        if (usernames.length > 0)
            for (String username: usernames)
                if (usernamelist.contains(username))
                    usernamelist.remove(username);
    }

    public static boolean pass(String enterUserName){

//        enterUserName = "@" + enterUserName;

        Path userNameFile = BotData.outResources.resolve("users.txt");

        try {
            usernamelist.addAll(Files.readAllLines(userNameFile));
        } catch (IOException e) {
            System.out.println("File users.txt not found...");
        }

        usernamelist = usernamelist.stream()
                        .filter(username -> !username.equals("")
                                && !username.startsWith("+")
                                && !username.startsWith("\"")
                                && !username.startsWith("\\")
                                && !username.startsWith(" ")
                                && !Pattern.matches(".*\\p{InCyrillic}.*", username))
                        .map(username ->
                            username.startsWith("@") ? username.substring(1) : username)
                        .collect(Collectors.toList());

        if (usernamelist.isEmpty()) {
            return true;
        }

        if (usernamelist.contains(enterUserName)) {
            return true;
        }

        return false;
    }
}
