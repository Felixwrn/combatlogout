package de.wrn.combatlogout;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatManager {

    private final Combatlogout plugin;

    private final Map<UUID, Long> combatPlayers = new HashMap<>();
    private final Map<UUID, BossBar> bossBars = new HashMap<>();

    private int combatTime = 15;

    public CombatManager(Combatlogout plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            for (Player player : Bukkit.getOnlinePlayers()) {

                UUID uuid = player.getUniqueId();

                if (!combatPlayers.containsKey(uuid)) {
                    continue;
                }

                long endTime = combatPlayers.get(uuid);
                long remainingMillis = endTime - System.currentTimeMillis();

                if (remainingMillis <= 0) {

                    combatPlayers.remove(uuid);

                    BossBar bar = bossBars.remove(uuid);

                    if (bar != null) {
                        bar.removePlayer(player);
                    }

                    continue;
                }

                double remainingSeconds = remainingMillis / 1000.0;

                BossBar bar = bossBars.get(uuid);

                if (bar != null) {

                    double progress = remainingSeconds / combatTime;

                    bar.setProgress(Math.max(0.0, Math.min(1.0, progress)));

                    bar.setTitle(
                            "§cIm Kampf §7| §f"
                                    + Math.ceil(remainingSeconds)
                                    + "s"
                    );
                }
            }

        }, 0L, 20L);
    }

    public void tag(UUID uuid) {

        combatPlayers.put(
                uuid,
                System.currentTimeMillis() + (combatTime * 1000L)
        );

        Player player = Bukkit.getPlayer(uuid);

        if (player == null) {
            return;
        }

        BossBar bar = bossBars.get(uuid);

        if (bar == null) {

            bar = Bukkit.createBossBar(
                    "§cIm Kampf",
                    BarColor.RED,
                    BarStyle.SOLID
            );

            bar.addPlayer(player);

            bossBars.put(uuid, bar);
        }

        bar.setVisible(true);
    }

    public boolean isInCombat(UUID uuid) {

        if (!combatPlayers.containsKey(uuid)) {
            return false;
        }

        if (System.currentTimeMillis() > combatPlayers.get(uuid)) {

            combatPlayers.remove(uuid);

            BossBar bar = bossBars.remove(uuid);

            if (bar != null) {

                Player player = Bukkit.getPlayer(uuid);

                if (player != null) {
                    bar.removePlayer(player);
                }
            }

            return false;
        }

        return true;
    }

    public int getCombatTime() {
        return combatTime;
    }

    public void setCombatTime(int combatTime) {
        this.combatTime = combatTime;
    }
}