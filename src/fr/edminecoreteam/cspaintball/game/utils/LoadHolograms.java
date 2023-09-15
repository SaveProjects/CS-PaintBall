package fr.edminecoreteam.cspaintball.game.utils;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class LoadHolograms
{
    private static Core core = Core.getInstance();
    public void init()
    {
        float A_x = (float) core.getConfig().getDouble("maps." + core.world + ".a.x");
        float A_y = (float) core.getConfig().getDouble("maps." + core.world + ".a.y") + 1;
        float A_z = (float) core.getConfig().getDouble("maps." + core.world + ".a.z");

        double B_x = (float) core.getConfig().getDouble("maps." + core.world + ".b.x");
        double B_y = (float) core.getConfig().getDouble("maps." + core.world + ".b.y") + 1;
        double B_z = (float) core.getConfig().getDouble("maps." + core.world + ".b.z");

        Location customlocA = new Location(Bukkit.getWorld("game"), A_x, A_y, A_z);
        ArmorStand armorStandA = (ArmorStand)Bukkit.getWorld("game").spawnEntity(customlocA, EntityType.ARMOR_STAND);
        armorStandA.setVisible(true);
        armorStandA.setCustomName("§fSite §c§lA");
        armorStandA.setCustomNameVisible(true);

        Location customlocB = new Location(Bukkit.getWorld("game"), B_x, B_y, B_z);
        ArmorStand armorStandB = (ArmorStand)Bukkit.getWorld("game").spawnEntity(customlocB, EntityType.ARMOR_STAND);
        armorStandB.setVisible(true);
        armorStandB.setCustomName("§fSite §c§lB");
        armorStandB.setCustomNameVisible(true);
    }
}
