package ru.allfire.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;  // ← ВОТ ЭТОТ ИМПОРТ БЫЛ ПРОПУЩЕН!

public class ServerRestarterUltraPlus extends JavaPlugin {

    private static ServerRestarterUltraPlus instance;
    private LangManager langManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getLogger().info("╔══════════════════════════════════════╗");
        getLogger().info("║   ServerRestarterUltraPlus v1.0    ║");
        getLogger().info("║        by AllFiRE67 (c) 2026       ║");
        getLogger().info("╚══════════════════════════════════════╝");

        langManager = new LangManager(this);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            String fullMessage = langManager.getFullMessage("loaded");
            
            Bukkit.broadcastMessage("§6[§cServerRestarterUltraPlus§6] §a" + fullMessage);
            
            getLogger().info("✅ Сервер успешно перезапущен!");
            getLogger().info("📢 Сообщение: " + fullMessage);
            
            List<String> parts = langManager.getMessageParts("loaded");
            getLogger().info("🧩 Части сообщения: " + String.join(" | ", parts));
            
        }, 20L * 3);
    }

    @Override
    public void onDisable() {
        getLogger().info("👋 ServerRestarterUltraPlus выключается...");
    }

    public static ServerRestarterUltraPlus getInstance() {
        return instance;
    }

    public LangManager getLangManager() {
        return langManager;
    }
}
