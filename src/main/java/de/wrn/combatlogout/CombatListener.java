package de.wrn.combatlogout;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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

        Player attacker = null;

        if (event.getDamager() instanceof Player player) {
            attacker = player;
        }

        if (event.getDamager() instanceof Projectile projectile) {

            if (projectile.getShooter() instanceof Player player) {
                attacker = player;
            }
        }

        if (attacker == null) {
            return;
        }

        manager.tag(attacker.getUniqueId());
        manager.tag(victim.getUniqueId());
    }
}