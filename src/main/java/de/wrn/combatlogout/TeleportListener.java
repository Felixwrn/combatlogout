package de.wrn.combatlogout;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    private final CombatManager manager;

    public TeleportListener(CombatManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {

        if (!manager.isInCombat(event.getPlayer().getUniqueId())) {
            return;
        }

        event.setCancelled(true);

        event.getPlayer().sendMessage(
                "§cDu kannst dich während eines Kampfes nicht teleportieren."
        );
    }
}