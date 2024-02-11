package fr.edminecoreteam.cspaintball.utils.game;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportUtils
{
    private static final Core core = Core.getInstance();

    public void teleportPlayer(Player p, Location location)
    {
        double randomX = location.getX() + (Math.random() - 0.5) * 3;
        double randomZ = location.getZ() + (Math.random() - 0.5) * 3;

        Location newLocation = new Location(location.getWorld(), randomX, location.getY(), randomZ);

        p.teleport(newLocation);
        core.spawnListeners().setPlayerLocation(p, newLocation);
    }
}
