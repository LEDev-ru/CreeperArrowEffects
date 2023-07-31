package ru.ledev.creeperarroweffects;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import ru.ledev.creeperarroweffects.commands.CAECommand;
import ru.ledev.creeperarroweffects.events.*;
import ru.ledev.creeperarroweffects.util.EffectsManager;

public final class CAE extends JavaPlugin {

    private static CAE instance;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new ArrowListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getCommand("creeperarroweffects").setExecutor(new CAECommand());

    }

    @Override
    public void onDisable() {

        HandlerList.unregisterAll(this);

        EffectsManager.stopAllTasks();

    }

    public static CAE getInstance() {
        return instance;
    }

    public static String getLocale(String key) {
        return ChatColor.translateAlternateColorCodes('&',
                instance.getConfig().getString("locale." + key));
    }

    public static void reloadCfg() {
        instance.reloadConfig();
    }
}
