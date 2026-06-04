package de.wrn.combatlogout;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatManager {

    private final Map<UUID, Long> combatPlayers = new HashMap<>();
    private int combatTime = 15;

    public CombatManager(Combatlogout plugin) {
    }

    public void tag(UUID uuid) {
        combatPlayers.put(uuid,
                System.currentTimeMillis() + (combatTime * 1000L));
    }

    public boolean isInCombat(UUID uuid) {
        if (!combatPlayers.containsKey(uuid)) {
            return false;
        }

        if (System.currentTimeMillis() > combatPlayers.get(uuid)) {
            combatPlayers.remove(uuid);
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