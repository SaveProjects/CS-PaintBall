package fr.edminecoreteam.cspaintball.utils.minecraft;

import fr.edminecoreteam.api.EdmineAPI;
import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class LoadHolograms
{
    private static Core core = Core.getInstance();

    float A_x = (float) core.getConfig().getDouble("maps." + core.world + ".a.x");
    float A_y = (float) core.getConfig().getDouble("maps." + core.world + ".a.y") + 3.5f;
    float A_z = (float) core.getConfig().getDouble("maps." + core.world + ".a.z");

    float B_x = (float) core.getConfig().getDouble("maps." + core.world + ".b.x");
    float B_y = (float) core.getConfig().getDouble("maps." + core.world + ".b.y") + 3.5f;
    float B_z = (float) core.getConfig().getDouble("maps." + core.world + ".b.z");

    public void init()
    {
        Location locSiteA = new Location(Bukkit.getWorld("game"), A_x, A_y, A_z);
        List<String> textSiteA = new ArrayList<>();
        textSiteA.add("§8⬇ §fSite §c§lA §8⬇");
        textSiteA.add("§fAccroupissez-vous pour §dplanter§8/§ddésamorcer§f.");
        EdmineAPI.getInstance().getHologramBuilder().createBukkitHologram("siteA", textSiteA, locSiteA);

        Location locSiteB = new Location(Bukkit.getWorld("game"), B_x, B_y, B_z);
        List<String> textSiteB = new ArrayList<>();
        textSiteB.add("§8⬇ §fSite §c§lB §8⬇");
        textSiteB.add("§fAccroupissez-vous pour §dplanter§8/§ddésamorcer§f.");
        EdmineAPI.getInstance().getHologramBuilder().createBukkitHologram("siteB", textSiteB, locSiteB);
    }
}
