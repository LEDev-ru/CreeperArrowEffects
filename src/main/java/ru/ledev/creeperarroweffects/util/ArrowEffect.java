package ru.ledev.creeperarroweffects.util;

import org.bukkit.Particle;

public enum ArrowEffect {
    FIRE(Particle.FLAME),
    TOTEM(Particle.TOTEM),
    TRACER_GREEN(Particle.VILLAGER_HAPPY),
    TRACER_LAVA(Particle.DRIP_LAVA),
    TRACER_WATER(Particle.DRIP_WATER),
    TRACER_CRIT(Particle.CRIT),
    TRACER_ENCHANTMENT(Particle.ENCHANTMENT_TABLE),
    TRACER_HEARTS(Particle.HEART),
    SMOKE(Particle.SMOKE_NORMAL),
    BIG_SMOKE(Particle.SMOKE_LARGE);

    public final Particle particle;

    ArrowEffect(Particle particle) {
        this.particle = particle;
    }
}
