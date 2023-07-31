package ru.ledev.creeperarroweffects.util;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static final Map<Player, ArrowEffect> playerArrowEffectMap = new HashMap<>();

    public static void setPlayerEffect(Player player, ArrowEffect effect) {
        playerArrowEffectMap.put(player, effect);
    }

    public static ArrowEffect getPlayerEffect(Player player) {
        return playerArrowEffectMap.get(player);
    }

    public static void removePlayerEffect(Player player) {
        playerArrowEffectMap.remove(player);
    }
}
