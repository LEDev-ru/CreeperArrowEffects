package ru.ledev.creeperarroweffects.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.ledev.creeperarroweffects.managers.MenuManager;
import ru.ledev.creeperarroweffects.managers.PlayerManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PlayerManager.removePlayerEffect(e.getPlayer());
        MenuManager.removeMenuOfPlayer(e.getPlayer());
    }
}
