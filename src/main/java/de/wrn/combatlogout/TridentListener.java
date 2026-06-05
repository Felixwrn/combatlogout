package de.wrn.combatlogout;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TridentListener implements Listener {

    private final CombatManager manager;

    public TridentListener(CombatManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {

        ItemStack item = event.getItem();

        if (item == null) {
            return;
        }

        if (item.getType() != Material.TRIDENT) {
            return;
        }

        if (!item.containsEnchantment(Enchantment.RIPTIDE)) {
            return;
        }

        if (!manager.isInCombat(event.getPlayer().getUniqueId())) {
            return;
        }

        event.setCancelled(true);

        String message = manager.getPlugin()
                .getConfig()
                .getString(
                        "messages.riptide-blocked",
                        "&cYou cannot use Riptide while in combat."
                );

        event.getPlayer().sendMessage(
                message.replace("&", "§")
        );
    }
}