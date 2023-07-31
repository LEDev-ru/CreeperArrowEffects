package ru.ledev.creeperarroweffects.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.ledev.creeperarroweffects.CAE;
import ru.ledev.creeperarroweffects.util.ArrowEffect;
import ru.ledev.creeperarroweffects.util.MenuManager;
import ru.ledev.creeperarroweffects.util.PlayerManager;

public class CAECommand implements CommandExecutor {
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
}
