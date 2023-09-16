package fr.edminecoreteam.cspaintball.game.spec;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class DefenserSpec
{
    private static Core core = Core.getInstance();

    public void setSpec(Player p)
    {
        p.getInventory().clear();
        p.getEquipment().setHelmet(null);
        p.getEquipment().setChestplate(null);
        p.getEquipment().setLeggings(null);
        p.getEquipment().setBoots(null);

        p.setGameMode(GameMode.SPECTATOR);
    }
}
