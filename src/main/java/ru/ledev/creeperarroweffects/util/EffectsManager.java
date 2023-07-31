package ru.ledev.creeperarroweffects.util;

import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitTask;
import ru.ledev.creeperarroweffects.CAE;

import java.util.ArrayList;
import java.util.List;

public class EffectsManager {

    private static final List<ArrowInfo> arrows = new ArrayList<>();

    public static void addArrow(Arrow arrow, ArrowEffect effect) {

        Particle particle = effect.particle;
        String name = effect.toString().toLowerCase();

        FileConfiguration cfg = CAE.getInstance().getConfig();

        if (!cfg.getBoolean("effects." + name + ".enabled")) return;

        World world = arrow.getWorld();

        long delay = cfg.getLong("effects." + name + ".delay-ticks", 1);
        int count = cfg.getInt("effects." + name + ".particles-count", 1);

        BukkitTask task = CAE.getInstance().getServer().getScheduler().runTaskTimer(
                CAE.getInstance(), () -> {
                    world.spawnParticle(particle, arrow.getLocation(), count);
                }, delay, delay);

        arrows.add(new ArrowInfo(arrow, task));
    }

    public static void removeArrow(Arrow arrow) {
        for (ArrowInfo arrowInfo : arrows)
            if (arrowInfo.arrow == arrow && arrowInfo.task != null)
                arrowInfo.task.cancel();
    }

    public static void stopAllTasks() {
        for (ArrowInfo arrowInfo : arrows)
            if (arrowInfo.task != null) arrowInfo.task.cancel();
    }

    private static class ArrowInfo {
        public final Arrow arrow;
        public final BukkitTask task;

        public ArrowInfo(Arrow arrow, BukkitTask task) {
            this.arrow = arrow;
            this.task = task;
        }
    }
}
