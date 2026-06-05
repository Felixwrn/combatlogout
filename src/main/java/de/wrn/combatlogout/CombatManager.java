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

    private int combatTime;

    public CombatManager(Combatlogout plugin) {

        this.plugin = plugin;
        this.combatTime = plugin.getConfig().getInt("combat-time", 15);

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

                    bar.setProgress(
                            Math.max(
                                    0.0,
                                    Math.min(1.0, progress)
                            )
                    );

                    String title = plugin.getConfig().getString(
                            "bossbar.title",
                            "&cIn Combat &7| &f%time%s"
                    );

                    title = title.replace(
                            "%time%",
                            String.valueOf(
                                    (int) Math.ceil(remainingSeconds)
                            )
                    );

                    bar.setTitle(
                            title.replace("&", "§")
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

            BarColor color;
            BarStyle style;

            try {
                color = BarColor.valueOf(
                        plugin.getConfig()
                                .getString(
                                        "bossbar.color",
                                        "RED"
                                )
                                .toUpperCase()
                );
            } catch (Exception e) {
                color = BarColor.RED;
            }

            try {
                style = BarStyle.valueOf(
                        plugin.getConfig()
                                .getString(
                                        "bossbar.style",
                                        "SOLID"
                                )
                                .toUpperCase()
                );
            } catch (Exception e) {
                style = BarStyle.SOLID;
            }

            bar = Bukkit.createBossBar(
                    "",
                    color,
                    style
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

    public Combatlogout getPlugin() {
        return plugin;
    }

    public int getCombatTime() {
        return combatTime;
    }

    public void setCombatTime(int combatTime) {
        this.combatTime = combatTime;
    }
}