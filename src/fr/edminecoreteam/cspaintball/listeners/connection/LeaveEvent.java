package fr.edminecoreteam.cspaintball.listeners.connection;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener
{
    private final static Core core = Core.getInstance();
    @EventHandler
    public void event(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        if (core.isState(State.WAITING))
        {
            e.setQuitMessage(null);
            int finalcount = core.getServer().getOnlinePlayers().size() - 1;
            core.getServer().broadcastMessage("§e" + p.getName() + "§7 a quitté le jeu. §d" + finalcount + "§d/" + core.getMaxplayers());

            core.teams().leaveTeam(p);
        }
    }
}
