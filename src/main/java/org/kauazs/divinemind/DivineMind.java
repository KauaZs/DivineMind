package org.kauazs.divinemind;

import org.bukkit.plugin.java.JavaPlugin;
import org.kauazs.divinemind.listeners.PlayerChat;
import org.kauazs.divinemind.managers.GeminiChats;

public final class DivineMind extends JavaPlugin {
    GeminiChats geminiChats = new GeminiChats();
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerChat(geminiChats, this), this);
    }

    @Override
    public void onDisable() {}
}
