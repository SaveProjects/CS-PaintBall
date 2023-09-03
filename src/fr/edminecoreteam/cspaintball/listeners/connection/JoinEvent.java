package fr.edminecoreteam.cspaintball.listeners.connection;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.waiting.WaitingListeners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener
{
    private final static Core core = Core.getInstance();

    private final Location spawn = new Location(Bukkit.getWorld(
            core.getConfig().getString("spawn.world")),
            (float) core.getConfig().getDouble("spawn.x"),
            (float) core.getConfig().getDouble("spawn.y"),
            (float) core.getConfig().getDouble("spawn.z"),
            (float) core.getConfig().getDouble("spawn.f"),
            (float) core.getConfig().getDouble("spawn.t"));

    @EventHandler
    public void event(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        WaitingListeners waitingListeners = new WaitingListeners(p);
        if (core.isState(State.WAITING))
        {
            e.setJoinMessage(null);
            core.getServer().broadcastMessage("§e" + p.getName() + "§7 a rejoint le jeu. §d" + core.getServer().getOnlinePlayers().size() + "§d/" + core.getMaxplayers());
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 0.5f, 1.0f);


            p.teleport(spawn);
            waitingListeners.getWaitingItems();
        }
    }
}
