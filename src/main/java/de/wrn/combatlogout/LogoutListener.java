package de.wrn.combatlogout;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LogoutListener implements Listener {

    private final CombatManager manager;

    public LogoutListener(CombatManager manager) {
        this.manager = manager;
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (!manager.isInCombat(player.getUniqueId())) {
            return;
        }

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        Bukkit.broadcastMessage(
                "§c" + player.getName()
                        + " hat sich während eines Kampfes ausgeloggt!"
        );
    }
}