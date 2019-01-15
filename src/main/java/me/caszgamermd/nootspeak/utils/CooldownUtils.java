package me.caszgamermd.nootspeak.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownUtils {

    //Cooldown for Squawk CMD
    private final Map<UUID, Long> cooldown = new HashMap<>();

    public void setCooldown(UUID player, long time) {
        if (time < 1) {
            cooldown.remove(player);
        } else {
            cooldown.put(player, time);
        }
    }

    public Long getCooldown(UUID player) {
        return cooldown.getOrDefault(player, 0L);
    }
}
