package me.caszgamermd.nootspeak.utils;

        import java.util.HashMap;
        import java.util.Map;
        import java.util.UUID;

public class CooldownUtils {

    //Cooldown for Squawk CMD
    private final Map<UUID, Long> cooldownSqk = new HashMap<>();
    private final Map<UUID, Long> cooldownPing = new HashMap<>();

    public void setCooldownSqk(UUID player, long time) {
        if (time < 1) {
            cooldownSqk.remove(player);
        } else {
            cooldownSqk.put(player, time);
        }
    }

    public Long getCooldownSqk(UUID player) {
        return cooldownSqk.getOrDefault(player, 0L);
    }

    public void setCooldownPing(UUID player, long time) {
        if (time < 1) {
            cooldownPing.remove(player);
        } else {
            cooldownPing.put(player, time);
        }
    }

    public Long getCooldownPing(UUID player) {
        return cooldownPing.getOrDefault(player, 0L);
    }
}
