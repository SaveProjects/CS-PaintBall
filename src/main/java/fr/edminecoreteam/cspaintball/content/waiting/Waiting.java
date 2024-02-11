package fr.edminecoreteam.cspaintball.content.waiting;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.content.waiting.tasks.AutoStart;
import fr.edminecoreteam.cspaintball.listeners.content.waiting.WaitingListeners;
import fr.edminecoreteam.cspaintball.utils.game.GameUtils;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class Waiting
{
    private final static Core core = Core.getInstance();
    private final GameUtils gameUtils = new GameUtils();

    public void wait(Player p)
    {
        core.getPlayersInGame().add(p.getName());
        core.getServer().broadcastMessage("§e" + p.getName() + "§7 a rejoint le jeu. §d" + core.getServer().getOnlinePlayers().size() + "§d/" + core.getMaxplayers());

        p.teleport(gameUtils.getSpawn());
        get(p);
        p.playSound(p.getLocation(), Sound.NOTE_PLING, 0.5f, 1.0f);

        if (core.getConfig().getString("type").equalsIgnoreCase("ranked"))
        {
            if (core.getPlayersInGame().size() == core.getMaxplayers())
            {
                core.setState(State.STARTING);
                AutoStart start = new AutoStart(core);
                start.runTaskTimer((Plugin) core, 0L, 20L);
            }
        }
        else if (core.getConfig().getString("type").equalsIgnoreCase("unranked"))
        {
            if (core.getPlayersInGame().size() == core.getConfig().getInt("needtostart"))
            {
                core.setState(State.STARTING);
                AutoStart start = new AutoStart(core);
                start.runTaskTimer((Plugin) core, 0L, 20L);
            }
        }
    }

    private void get(Player p) {
        PlayerInventory pi = p.getInventory();
        WaitingListeners waitingListeners = new WaitingListeners(p);

        pi.setHelmet(null);
        pi.setChestplate(null);
        pi.setLeggings(null);
        pi.setBoots(null);
        p.setAllowFlight(false);
        p.setFlying(false);

        p.getInventory().clear();
        p.setLevel(0);
        p.setFoodLevel(20);
        p.setHealth(20.0);
        p.setGameMode(GameMode.ADVENTURE);
        waitingListeners.getWaitingItems();
        if (core.getConfig().getString("type").equalsIgnoreCase("ranked"))
        {
            if (core.getConfig().getString("time").equalsIgnoreCase("long"))
            {
                p.sendTitle("§e§lPaint-Ball", "§7Mode §6Compétitif §8/ §7Durée §bLongue§8.");
            }
            else if (core.getConfig().getString("time").equalsIgnoreCase("short"))
            {
                p.sendTitle("§e§lPaint-Ball", "§7Mode §6Compétitif §8/ §7Durée §bCourte§8.");
            }
        }
        if (core.getConfig().getString("type").equalsIgnoreCase("unranked"))
        {
            if (core.getConfig().getString("time").equalsIgnoreCase("long"))
            {
                p.sendTitle("§e§lPaint-Ball", "§7Mode §6Non-Compétitif §8/ §7Durée §bLongue§8.");
            }
            else if (core.getConfig().getString("time").equalsIgnoreCase("short"))
            {
                p.sendTitle("§e§lPaint-Ball", "§7Mode §6Non-Compétitif §8/ §7Durée §bCourte§8.");
            }
        }
    }
}
