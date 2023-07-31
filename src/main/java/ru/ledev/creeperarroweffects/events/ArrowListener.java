package ru.ledev.creeperarroweffects.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import ru.ledev.creeperarroweffects.util.EffectsManager;
import ru.ledev.creeperarroweffects.util.PlayerManager;

public class ArrowListener implements Listener {

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {

        if (!(e.getEntity() instanceof Arrow)) return;

        Arrow arrow = (Arrow) e.getEntity();

        if (!(arrow.getShooter() instanceof Player)) return;

        Player player = (Player) arrow.getShooter();

        if (PlayerManager.getPlayerEffect(player) == null) return;

        EffectsManager.addArrow(arrow, PlayerManager.getPlayerEffect(player));
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {

        if (!(e.getEntity() instanceof Arrow)) return;

        EffectsManager.removeArrow((Arrow) e.getEntity());
    }
}
