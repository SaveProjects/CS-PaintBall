package fr.edminecoreteam.cspaintball.end;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Listeners
{

    private final static Core core = Core.getInstance();
    private final Location spawn = new Location(Bukkit.getWorld(
            core.getConfig().getString("spawn.world")),
            (float) core.getConfig().getDouble("spawn.x"),
            (float) core.getConfig().getDouble("spawn.y"),
            (float) core.getConfig().getDouble("spawn.z"),
            (float) core.getConfig().getDouble("spawn.f"),
            (float) core.getConfig().getDouble("spawn.t"));

    public void end()
    {
        for (Player pls : core.getServer().getOnlinePlayers())
        {
            pls.getInventory().clear();
            pls.setHealth(20);
            pls.setFoodLevel(20);
            pls.teleport(spawn);
        }
    }

}
