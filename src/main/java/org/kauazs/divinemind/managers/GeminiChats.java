package org.kauazs.divinemind.managers;

import com.google.genai.Chat;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class GeminiChats {
    @Getter
    HashMap<UUID, Chat> chats = new HashMap<>();

    public void setChat(UUID uuid, Chat chat) {
        chats.put(uuid, chat);
    }

    public void removeChat(UUID uuid) {
        chats.remove(uuid);
    }

    public Chat getChat(UUID uuid) {
        return chats.get(uuid);
    }
}
