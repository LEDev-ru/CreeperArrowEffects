package ru.ledev.creeperarroweffects.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import ru.ledev.creeperarroweffects.managers.MenuManager;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        MenuManager.removeMenuOfPlayer((Player) e.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        try {
            if (MenuManager.processClick(e.getInventory(), e.getSlot())) e.setCancelled(true);
        }
        catch (Exception ignored) {}
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (MenuManager.isInventoryMenu(e.getInventory())) e.setCancelled(true);
    }

    @EventHandler
    public void onMoveItem(InventoryMoveItemEvent e) {
        if (MenuManager.isInventoryMenu(e.getDestination())) e.setCancelled(true);
        if (MenuManager.isInventoryMenu(e.getInitiator())) e.setCancelled(true);
        if (MenuManager.isInventoryMenu(e.getSource())) e.setCancelled(true);
    }
}
