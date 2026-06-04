package de.wrn.combatlogout;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PearlListener implements Listener {

    private final CombatManager manager;

    public PearlListener(CombatManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getItem() == null) {
            return;
        }

        if (event.getItem().getType() != Material.ENDER_PEARL) {
            return;
        }

        if (!manager.isInCombat(event.getPlayer().getUniqueId())) {
            return;
        }

        event.setCancelled(true);

        event.getPlayer().sendMessage(
                "§cEnderperlen sind im Kampf deaktiviert."
        );
    }
}