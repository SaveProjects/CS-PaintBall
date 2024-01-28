package fr.edminecoreteam.cspaintball.game.utils;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class LoadHolograms
{
    private static Core core = Core.getInstance();

    float A_x = (float) core.getConfig().getDouble("maps." + core.world + ".a.x");
    float A_y = (float) core.getConfig().getDouble("maps." + core.world + ".a.y") + 3.5f;
    float newAY = A_y - 0.3f;
    float A_z = (float) core.getConfig().getDouble("maps." + core.world + ".a.z");

    float B_x = (float) core.getConfig().getDouble("maps." + core.world + ".b.x");
    float B_y = (float) core.getConfig().getDouble("maps." + core.world + ".b.y") + 3.5f;
    float newBY = B_y - 0.3f;
    float B_z = (float) core.getConfig().getDouble("maps." + core.world + ".b.z");

    public void init()
    {

        Location customlocA = new Location(Bukkit.getWorld("game"), A_x, A_y, A_z);
        ArmorStand armorStandA = (ArmorStand)Bukkit.getWorld("game").spawnEntity(customlocA, EntityType.ARMOR_STAND);
        armorStandA.setVisible(false);
        armorStandA.setSmall(true);
        armorStandA.setCustomName("§8⬇ §fSite §c§lA §8⬇");
        armorStandA.setCustomNameVisible(true);
        armorStandA.setGravity(false);

        Location customlocB = new Location(Bukkit.getWorld("game"), B_x, B_y, B_z);
        ArmorStand armorStandB = (ArmorStand)Bukkit.getWorld("game").spawnEntity(customlocB, EntityType.ARMOR_STAND);
        armorStandB.setVisible(false);
        armorStandB.setSmall(true);
        armorStandB.setCustomName("§8⬇ §fSite §c§lB §8⬇");
        armorStandB.setCustomNameVisible(true);
        armorStandB.setGravity(false);

        Location secondLocA = new Location(Bukkit.getWorld("game"), A_x, newAY, A_z);
        ArmorStand secondArmorStandA = (ArmorStand)Bukkit.getWorld("game").spawnEntity(secondLocA, EntityType.ARMOR_STAND);
        secondArmorStandA.setVisible(false);
        secondArmorStandA.setSmall(true);
        secondArmorStandA.setCustomName("§fAccroupissez-vous pour §dplanter§8/§ddésamorcer§f.");
        secondArmorStandA.setCustomNameVisible(true);
        secondArmorStandA.setGravity(false);

        Location secondLocB = new Location(Bukkit.getWorld("game"), B_x, newBY, B_z);
        ArmorStand secondArmorStandB = (ArmorStand)Bukkit.getWorld("game").spawnEntity(secondLocB, EntityType.ARMOR_STAND);
        secondArmorStandB.setVisible(false);
        secondArmorStandB.setSmall(true);
        secondArmorStandB.setCustomName("§fAccroupissez-vous pour §dplanter§8/§ddésamorcer§f.");
        secondArmorStandB.setCustomNameVisible(true);
        secondArmorStandB.setGravity(false);
    }
}
