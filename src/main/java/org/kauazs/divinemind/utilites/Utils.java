package org.kauazs.divinemind.utilites;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {
    public static void sendMessageAllPlayers(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public static void sendMessageHighlighted(String message) {
        sendMessageAllPlayers("");
        sendMessageAllPlayers(message);
        sendMessageAllPlayers("");
    }
}
