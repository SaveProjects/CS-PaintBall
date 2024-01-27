package fr.edminecoreteam.cspaintball.game.spec;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class DefenserSpec implements Listener
{
    private static final Core core = Core.getInstance();

    public final HashMap<Player, Player> getView = new HashMap<Player, Player>();

    public void setSpec(Player p)
    {
        p.getInventory().clear();
        p.getEquipment().setHelmet(null);
        p.getEquipment().setChestplate(null);
        p.getEquipment().setLeggings(null);
        p.getEquipment().setBoots(null);

        p.setGameMode(GameMode.SPECTATOR);
        for (Player teams : core.teams().getDefenser())
        {
            if (!core.teams().getDefenserDeath().contains(teams))
            {
                if (!core.defenserSpec().getView.containsKey(p))
                {
                    core.defenserSpec().getView.put(p, teams);
                }
            }
        }
        new BukkitRunnable() {
            int t = 0;
            public void run() {
                ++t;
                if (core.isRoundState(RoundInfo.PREPARATION)) { cancel(); }
                Location to = core.defenserSpec().getView.get(p).getLocation();
                p.hidePlayer(core.defenserSpec().getView.get(p));
                p.setFlying(true);
                p.teleport(to.clone().add(0, 0, 0));
                core.title.sendActionBar(p, "§fVous regardez §c" + core.defenserSpec().getView.get(p).getName());

                if (t == 1)
                {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 2L);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (core.teams().getDefenserDeath().contains(p))
        {
            if (!core.isRoundState(RoundInfo.PREPARATION))
            {
                Player target = (Player) core.defenserSpec().getView.get(p);
                int getOrder = core.teams().getDefenser().indexOf(target);
                if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)
                {
                    if (getOrder != -1 && getOrder < core.teams().getDefenser().size())
                    {
                        Player newTarget = core.teams().getDefenser().get(getOrder + 1);
                        p.showPlayer(core.defenserSpec().getView.get(p));
                        core.defenserSpec().getView.remove(p);
                        core.defenserSpec().getView.put(p, newTarget);
                    }
                }

                if (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)
                {
                    if (getOrder > 0)
                    {
                        Player newTarget = core.teams().getDefenser().get(getOrder - 1);
                        p.showPlayer(core.defenserSpec().getView.get(p));
                        core.defenserSpec().getView.remove(p);
                        core.defenserSpec().getView.put(p, newTarget);
                    }
                }
            }
            else
            {
                return;
            }
        }
    }
}
