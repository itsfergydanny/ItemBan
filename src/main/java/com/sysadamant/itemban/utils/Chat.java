package com.sysadamant.itemban.utils;

import com.sysadamant.itemban.ItemBan;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {
    public static String format(String message) {
        if(ItemBan.serverVersion >= 16) {
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String strip(String message) {
        return ChatColor.stripColor(message);
    }
}