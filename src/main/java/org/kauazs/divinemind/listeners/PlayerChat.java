package org.kauazs.divinemind.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.kauazs.divinemind.managers.GeminiChats;
import org.kauazs.divinemind.managers.Gemini;
import org.kauazs.divinemind.utilites.Utils;

public class PlayerChat implements Listener {
    private GeminiChats geminiChats;
    private Plugin plugin;
    public PlayerChat(GeminiChats geminiChats, Plugin plugin) {
        this.geminiChats = geminiChats;
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        Utils.sendMessageAllPlayers(player.getDisplayName() + ": " + event.getMessage());

        boolean invokedMestre = event.getMessage().toLowerCase().contains("mestre");
        if (!invokedMestre) return;

        Gemini gemini = new Gemini(geminiChats, plugin);
        String response = gemini.sendMessageChat(player, event.getMessage());

        Utils.sendMessageHighlighted(ChatColor.RED + "" + ChatColor.BOLD + "MESTRE: " + ChatColor.RESET + response);
    }
}
