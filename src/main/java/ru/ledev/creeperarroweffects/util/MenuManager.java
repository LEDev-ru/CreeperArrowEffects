package ru.ledev.creeperarroweffects.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.ledev.creeperarroweffects.CAE;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    private static final List<Menu> menus = new ArrayList<>();

    public static void openMenu(Player player) {

        FileConfiguration cfg = CAE.getInstance().getConfig();

        List<ArrowEffect> activeEffects = new ArrayList<>();

        boolean playerHasOnePlusEffect = false;

        for (ArrowEffect effect : ArrowEffect.values()) {
            if (cfg.getBoolean("effects." + effect.toString().toLowerCase() + ".enabled")) {
                activeEffects.add(effect);
                if (player.hasPermission("creeperarroweffects.effect." + effect.toString().toLowerCase())) playerHasOnePlusEffect = true;
            }
        }

        if (!playerHasOnePlusEffect) {
            System.out.println("Error in permissions! Player " + player.getName() + " can open menu but can't enable any effect =(");
            return;
        }

        int rows = 3;
        if (activeEffects.size() > 21) rows = 6;
        else if (activeEffects.size() > 14) rows = 5;
        else if (activeEffects.size() > 7) rows = 4;

        Inventory menuInventory = CAE.getInstance().getServer().createInventory(
                player, rows*9,
                ChatColor.translateAlternateColorCodes('&', cfg.getString("menu-name", "&6 > &f Эффекты стрел")));

        // Рамка
        for (int i = 0; i < 10; i++) {
            menuInventory.setItem(i, getBorder());
        }
        for (int i = (rows-1)*9; i < rows*9; i++) {
            menuInventory.setItem(i, getBorder());
        }
        menuInventory.setItem(17, getBorder());
        if (rows > 3) {
            menuInventory.setItem(18, getBorder());
            menuInventory.setItem(26, getBorder());
        }
        if (rows > 4) {
            menuInventory.setItem(27, getBorder());
            menuInventory.setItem(35, getBorder());
        }
        if (rows > 5) {
            menuInventory.setItem(36, getBorder());
            menuInventory.setItem(44, getBorder());
        }

        // Кнопки
        List<MenuButton> menuButtons = new ArrayList<>();

        Material btnMaterial;
        try {
            btnMaterial = Material.valueOf(cfg.getString("button-material", "PAPER").toUpperCase());
        }
        catch (IllegalArgumentException e) {
            btnMaterial = Material.PAPER;
        }

        for (int i = 0; i < 7 && i < activeEffects.size(); i++) {
            ArrowEffect effect = activeEffects.get(i);
            ItemStack effectItem = new ItemStack(btnMaterial);
            ItemMeta itemMeta = effectItem.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                    cfg.getString("effects." + effect.toString().toLowerCase() + ".name",
                    "&cОшибка локализации! Key: effects." + effect.toString().toLowerCase() + ".name")));
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            if (cfg.getBoolean("enchanted-button")) {
                itemMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            effectItem.setItemMeta(itemMeta);
            int slot = i + 10;
            menuButtons.add(new MenuButton(slot, effect));
            menuInventory.setItem(slot, effectItem);
        }
        if (activeEffects.size() > 7) {
            for (int i = 7; i < 14 && i < activeEffects.size(); i++) {
                ArrowEffect effect = activeEffects.get(i);
                ItemStack effectItem = new ItemStack(btnMaterial);
                ItemMeta itemMeta = effectItem.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        cfg.getString("effects." + effect.toString().toLowerCase() + ".name",
                        "&cОшибка локализации! Key: effects." + effect.toString().toLowerCase() + ".name")));
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                if (cfg.getBoolean("enchanted-button")) {
                    itemMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                effectItem.setItemMeta(itemMeta);
                int slot = i + 12;
                menuButtons.add(new MenuButton(slot, effect));
                menuInventory.setItem(slot, effectItem);
            }
        }
        if (activeEffects.size() > 14) {
            for (int i = 14; i < 21 && i < activeEffects.size(); i++) {
                ArrowEffect effect = activeEffects.get(i);
                ItemStack effectItem = new ItemStack(btnMaterial);
                ItemMeta itemMeta = effectItem.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        cfg.getString("effects." + effect.toString().toLowerCase() + ".name",
                        "&cОшибка локализации! Key: effects." + effect.toString().toLowerCase() + ".name")));
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                if (cfg.getBoolean("enchanted-button")) {
                    itemMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                effectItem.setItemMeta(itemMeta);
                int slot = i + 14;
                menuButtons.add(new MenuButton(slot, effect));
                menuInventory.setItem(slot, effectItem);
            }
        }
        if (activeEffects.size() > 21) {
            for (int i = 21; i < 28 && i < activeEffects.size(); i++) {
                ArrowEffect effect = activeEffects.get(i);
                ItemStack effectItem = new ItemStack(btnMaterial);
                ItemMeta itemMeta = effectItem.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        cfg.getString("effects." + effect.toString().toLowerCase() + ".name",
                        "&cОшибка локализации! Key: effects." + effect.toString().toLowerCase() + ".name")));
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                if (cfg.getBoolean("enchanted-button")) {
                    itemMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                effectItem.setItemMeta(itemMeta);
                int slot = i + 16;
                menuButtons.add(new MenuButton(slot, effect));
                menuInventory.setItem(slot, effectItem);
            }
        }

        player.openInventory(menuInventory);

        menus.add(new Menu(menuInventory, menuButtons));
    }

    public static void removeMenuOfPlayer(Player player) {
        menus.removeIf(menu -> menu.inventory.getHolder() == player);
    }

    public static boolean isInventoryMenu(Inventory inventory) {
        for (Menu menu : menus) if (menu.inventory.getHolder() == inventory.getHolder()) return true;
        return false;
    }

    public static boolean processClick(Inventory inventory, int slot) {
        for (Menu menu : menus) {
            if (menu.inventory.getHolder() != inventory.getHolder()) continue;
            List<MenuButton> buttons = menu.buttons;
            for (MenuButton b : buttons) {
                if (b.slot != slot) continue;
                ArrowEffect effect = b.effect;
                Player player = (Player) inventory.getHolder();
                if (!player.hasPermission("creeperarroweffects.effect." + effect.toString().toLowerCase())) {
                    player.sendMessage(CAE.getLocale("not-enough-permission"));
                    return true;
                }
                PlayerManager.setPlayerEffect(player, effect);
                player.closeInventory();
                player.sendMessage(CAE.getLocale("effect-set"));
                removeMenuOfPlayer(player);
                return true;
            }
            return true;
        }
        return false;
    }

    private static ItemStack getBorder() {
        return new ItemStack(Material.STAINED_GLASS_PANE);
    }

    private static class Menu {
        public final Inventory inventory;
        public final List<MenuButton> buttons = new ArrayList<>();

        public Menu(Inventory inventory, List<MenuButton> buttons) {
            this.inventory = inventory;
            this.buttons.addAll(buttons);
        }
    }

    private static class MenuButton {
        public final int slot;
        public final ArrowEffect effect;
        public MenuButton(int slot, ArrowEffect effect) {
            this.slot = slot;
            this.effect = effect;
        }
    }

}
