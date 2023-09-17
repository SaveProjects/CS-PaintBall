package fr.edminecoreteam.cspaintball.game.spec;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DefenserSpec
{
    private static Core core = Core.getInstance();

    Location specSpawn = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spec.x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spec.y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spec.z"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spec.f"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spec.t"));

    public void setSpec(Player p)
    {
        p.getInventory().clear();
        p.getEquipment().setHelmet(null);
        p.getEquipment().setChestplate(null);
        p.getEquipment().setLeggings(null);
        p.getEquipment().setBoots(null);

        p.setGameMode(GameMode.SPECTATOR);
        p.teleport(specSpawn);
    }
}
