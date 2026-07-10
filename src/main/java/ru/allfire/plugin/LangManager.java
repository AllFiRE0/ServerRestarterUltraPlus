package ru.allfire.plugin;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class LangManager {

    private final JavaPlugin plugin;
    private final String language;
    private final Map<String, List<String>> messages = new HashMap<>();

    public LangManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.language = plugin.getConfig().getString("language", "en");

        plugin.getLogger().info("🌍 Загрузка языковой системы...");
        loadLanguage();
    }

    private void loadLanguage() {
        String langFile = "lang/" + language + ".yml";
        File file = new File(plugin.getDataFolder(), langFile);

        if (!file.exists()) {
            plugin.getLogger().info("📁 Создаём языковой файл: " + langFile);
            plugin.saveResource(langFile, false);
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (String key : config.getKeys(false)) {
            Object value = config.get(key);

            if (value instanceof List) {
                messages.put(key, (List<String>) value);
                plugin.getLogger().info("📝 Загружен список для '" + key + "' (" + ((List) value).size() + " частей)");
            } else if (value instanceof String) {
                messages.put(key, Collections.singletonList((String) value));
                plugin.getLogger().info("📝 Загружена строка для '" + key + "'");
            } else {
                messages.put(key, Collections.singletonList("Ошибка"));
                plugin.getLogger().warning("⚠️ Неизвестный формат для '" + key + "'");
            }
        }

        plugin.getLogger().info("✅ Язык '" + language + "' успешно загружен!");
        
        String fullMessage = getFullMessage("loaded");
        plugin.getLogger().info("💬 Тестовое сообщение: " + fullMessage);
    }

    public List<String> getMessageParts(String key) {
        return messages.getOrDefault(key, Collections.singletonList("Сообщение не найдено"));
    }

    public String getFullMessage(String key) {
        List<String> parts = getMessageParts(key);
        StringBuilder full = new StringBuilder();
        
        for (int i = 0; i < parts.size(); i++) {
            String part = parts.get(i);
            full.append(part);
            
            if (i < parts.size() - 1) {
                full.append(" ");
            }
        }
        
        return full.toString().trim();
    }
}
