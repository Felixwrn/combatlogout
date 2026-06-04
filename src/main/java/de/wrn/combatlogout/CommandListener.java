package de.wrn.combatlogout;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    private final CombatManager manager;

    public CommandListener(CombatManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {

        if (!manager.isInCombat(event.getPlayer().getUniqueId())) {
            return;
        }

        String cmd = event.getMessage().toLowerCase();

        if (cmd.startsWith("/msg")
                || cmd.startsWith("/r")
                || cmd.startsWith("/reply")) {
            return;
        }

        event.setCancelled(true);
        event.getPlayer().sendMessage("§cDu kannst während eines Kampfes keine Befehle benutzen.");
    }
}