package org.kauazs.divinemind.managers;

import com.google.genai.Chat;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.gson.Gson;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.kauazs.divinemind.utilites.ChatResponse;

import java.util.List;

public class Gemini {

    private GeminiChats geminiChats;
    private Client client;

    private Plugin plugin;
    public Gemini(GeminiChats geminiChats, Plugin plugin) {
        this.geminiChats = geminiChats;
        this.plugin = plugin;

        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API key is not defined in environment variable GEMINI_API_KEY");
        }
        this.client = Client.builder()
                .apiKey(apiKey).build();
    }

    public String sendMessageChat(Player player, String message) {
        String prompt = "Você é o mestre de um servidor de Minecraft. Você é obcecado por poder, é egoísta e egocêntrico. E boca suja\n\n" +
                "Suas ações devem condizer com suas respostas." +
                "Você tem as seguintes ações disponíveis:\n" +
                "DESTRUTIVAS: spawn_creeper, create_explosion, wither_surprise, monster_house\n" +
                "ARMADILHAS: trap_in_obsidian, fake_diamond, blindness_maze, lava_shower\n" +
                "CONFUSÃO: cursed_items, inventory_shuffle, upside_down_world, fake_death\n" +
                "EFEITOS: set_fire, giveNausea, hunger_games, gravity_flip, phantom_swarm\n" +
                "CHASE: tnt_minecart_chase, chicken_apocalypse\n" +
                "INVENTÁRIO: clearinventory, giveOneDiamond\n" +
                "Sempre responda EXATAMENTE com este formato JSON (sem variações, sem comentários, sem quebra de linha, sem \\n):\n" +
                "{\"message\": \"sua resposta aqui\", \"action\": \"nome_da_ação_ou_null\"}\n\n" +
                "NÃO inclua explicações, NÃO use \\n, NÃO use markdown, NÃO quebre linhas. Apenas envie o JSON em uma única linha.\n\n";

        Content content = Content.builder().parts(List.of(
                Part.builder().text(prompt).build()
        )).build();
        GenerateContentConfig config = GenerateContentConfig.builder().systemInstruction(content).build();
        Chat chat = geminiChats.getChat(player.getUniqueId());
        if (chat == null) {
            chat = client.chats.create("gemini-2.5-flash", config);
            geminiChats.setChat(player.getUniqueId(), chat);
        }

        try {
            GenerateContentResponse response = chat.sendMessage(message);

            Gson gson = new Gson();
            ChatResponse chatResponse = gson.fromJson(response.text(), ChatResponse.class);

            if (chatResponse.action != null) {
                Actions actions = new Actions(player, plugin);
                actions.executeAction(chatResponse.action);
            }

            return chatResponse.message;
        } catch (Exception exception) {
            geminiChats.removeChat(player.getUniqueId());
            return "Erro interno.";
        }
    }
}
