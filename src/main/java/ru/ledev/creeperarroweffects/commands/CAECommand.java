package ru.ledev.creeperarroweffects.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import ru.ledev.creeperarroweffects.CAE;
import ru.ledev.creeperarroweffects.effects.ArrowEffect;
import ru.ledev.creeperarroweffects.managers.MenuManager;
import ru.ledev.creeperarroweffects.managers.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public class CAECommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("creeperarroweffects.reload")) {
                sender.sendMessage(CAE.getLocale("not-enough-permission"));
                return true;
            }
            CAE.reloadCfg();
            if (sender instanceof Player) sender.sendMessage(CAE.getLocale("config-reloaded"));
            else sender.sendMessage("Config reloaded! (This is version of config-reloaded message for console only)");
            return true;
        }

        if (!(sender instanceof Player)) {
            if (args.length > 0) {
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                if (targetPlayer != null) {
                    MenuManager.openMenu(targetPlayer);
                    sender.sendMessage("Menu opened for " + targetPlayer.getName());
                    return true;
                }
            }
            sender.sendMessage("Use '/" + label.toLowerCase() + " <player>' to open menu for player");
            sender.sendMessage("or use '/" + label.toLowerCase() + " reload' to reload config");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("creeperarroweffects.cmd")) {
            player.sendMessage(CAE.getLocale("not-enough-permission"));
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                if (PlayerManager.getPlayerEffect(player) == null) {
                    player.sendMessage(CAE.getLocale("no-effect-for-clear"));
                }
                else {
                    PlayerManager.removePlayerEffect(player);
                    player.sendMessage(CAE.getLocale("effect-cleared"));
                }
                return true;
            }
            String effectName = args[0].toUpperCase();
            try {
                ArrowEffect effect = ArrowEffect.valueOf(effectName);
                if (CAE.getInstance().getConfig().getBoolean("effects." + effectName.toLowerCase() + ".enabled")) {
                    if (!player.hasPermission("creeperarroweffects.effect." + effectName.toLowerCase())) {
                        player.sendMessage(CAE.getLocale("not-enough-permission"));
                        return true;
                    }
                    PlayerManager.setPlayerEffect(player, effect);
                    player.sendMessage(CAE.getLocale("effect-set"));
                    return true;
                }
            }
            catch (Exception ignored) {}
        }

        MenuManager.openMenu(player);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            List<String> result = new ArrayList<>();
            result.add("clear");
            for (ArrowEffect effect : ArrowEffect.values()) {
                if (CAE.getInstance().getConfig().getBoolean(
                        "effects." + effect.name().toLowerCase() + ".enabled")) {
                    result.add(effect.name().toLowerCase());
                }
            }
            return result;
        }

        return null;
    }
}
