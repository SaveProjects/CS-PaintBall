package fr.edminecoreteam.cspaintball.utils.minecraft;

import fr.edminecoreteam.api.EdmineAPISpigot;
import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;

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
        EdmineAPISpigot.getInstance().getHologramBuilder().createBukkitHologram("siteA", textSiteA, locSiteA);

        Location locSiteB = new Location(Bukkit.getWorld("game"), B_x, B_y, B_z);
        List<String> textSiteB = new ArrayList<>();
        textSiteB.add("§8⬇ §fSite §c§lB §8⬇");
        textSiteB.add("§fAccroupissez-vous pour §dplanter§8/§ddésamorcer§f.");
        EdmineAPISpigot.getInstance().getHologramBuilder().createBukkitHologram("siteB", textSiteB, locSiteB);
    }

    public void help()
    {
        Location location = new Location(Bukkit.getWorld("hub"), 10.5, 33.0, 8.5);
        List<String> text = new ArrayList<>();
        text.add("§a§l? §aInformations §a§l¿");
        text.add("§1");
        text.add("§6Accroupissez-vous §fpour §eplanter§8/§edésamorcer §fla §cbombe§f.");
        text.add("§6Accroupissez-vous §fsur une §barme §fpour la récupérer.");
        text.add("§fFaites un §6Clic-Droit §fpour recharger votre §barme§f.");
        text.add("§fFaites un §6Clic-Gauche §fpour tirer avec votre §barme§f.");
        text.add("§fPour les §barmes automatiques§f, cliquez pour §etirer §fou arrêter de §etirer§f.");
        EdmineAPISpigot.getInstance().getHologramBuilder().createBukkitHologram("help", text, location);
    }

    public void rules()
    {
        Location location = new Location(Bukkit.getWorld("hub"), 10.5, 33.0, -7.5);
        List<String> text = new ArrayList<>();
        text.add("§6§l⚠ §6Règles §6§l⚠");
        text.add("§1");
        text.add("§c§lATTAQUANTS§f:");
        text.add("§fDirigez-vous avec votre §béquipe§f sur un §esite §fpour planter la §cbombe§f.");
        text.add("§2");
        text.add("§9§lDÉFENSEURS§f:");
        text.add("§fDispersez-vous avec votre §béquipe §fsur les §esites §fet §dprotégez l'objectif§f.");
        text.add("§3");
        text.add("§fLes dégâts alliés sont activés.");
        EdmineAPISpigot.getInstance().getHologramBuilder().createBukkitHologram("rules", text, location);
    }
}
