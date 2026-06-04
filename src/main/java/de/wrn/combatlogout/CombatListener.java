package de.wrn.combatlogout;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener {

    private final CombatManager manager;

    public CombatListener(CombatManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        if (!(event.getDamager() instanceof Player attacker)) {
            return;
        }

        manager.tag(attacker.getUniqueId());
        manager.tag(victim.getUniqueId());
    }
}