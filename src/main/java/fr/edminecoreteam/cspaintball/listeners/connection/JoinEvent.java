package fr.edminecoreteam.cspaintball.listeners.connection;

import fr.edminecoreteam.api.EdmineAPISpigot;
import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.content.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.content.game.spec.AttackerSpec;
import fr.edminecoreteam.cspaintball.content.game.spec.DefenserSpec;
import fr.edminecoreteam.cspaintball.content.game.teams.TeamsKit;
import fr.edminecoreteam.cspaintball.content.waiting.Waiting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener
{
    private final static Core core = Core.getInstance();

    private final Location attackerSpawn = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".attacker.x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".attacker.y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".attacker.z"),
            (float) core.getConfig().getDouble("maps." + core.world + ".attacker.f"),
            (float) core.getConfig().getDouble("maps." + core.world + ".attacker.t"));

    private final Location defenserSpawn = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".defenser.x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".defenser.y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".defenser.z"),
            (float) core.getConfig().getDouble("maps." + core.world + ".defenser.f"),
            (float) core.getConfig().getDouble("maps." + core.world + ".defenser.t"));

    @EventHandler
    public void event(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        e.setJoinMessage(null);
        EdmineAPISpigot.getInstance().getBossBarBuilder().putPlayer(p);
        p.setMaximumNoDamageTicks(10);
        if (core.isState(State.WAITING) || core.isState(State.STARTING))
        {
            if (core.getPlayersInGame().size() == core.getMaxplayers())
            {
                p.kickPlayer("§cPartie pleine...");
                return;
            }

            if (!core.getPlayersInGame().contains(p.getName()))
            {
                Waiting waiting = new Waiting();
                waiting.wait(p);
            }
        }
        if (core.isState(State.INGAME))
        {
            if (core.pauses().getAttackerWait().contains(p.getName()))
            {
                core.pauses().desactivePause();
                core.pauses().getAttackerWait().remove(p.getName());
                core.teams().getAttacker().add(p);
                if (core.isRoundState(RoundInfo.PREPARATION))
                {
                    if (core.timers > 15)
                    {
                        core.timers = 15;
                    }
                    p.teleport(attackerSpawn);
                    TeamsKit kit = new TeamsKit();
                    kit.equipDefault(p);
                }
                else
                {
                    core.teams().getAttackerDeath().add(p);
                    AttackerSpec attackerSpec = new AttackerSpec();
                    attackerSpec.setSpec(p);
                }
            }
            if (core.pauses().getDefenserWait().contains(p.getName()))
            {
                core.pauses().desactivePause();
                core.pauses().getDefenserWait().remove(p.getName());
                core.teams().getDefenser().add(p);
                if (core.isRoundState(RoundInfo.PREPARATION))
                {
                    if (core.timers > 15)
                    {
                        core.timers = 15;
                    }
                    p.teleport(defenserSpawn);
                    TeamsKit kit = new TeamsKit();
                    kit.equipDefault(p);
                }
                else
                {
                    core.teams().getDefenserDeath().add(p);
                    DefenserSpec defenserSpec = new DefenserSpec();
                    defenserSpec.setSpec(p);
                }
            }

            if (!core.pauses().getAttackerWait().contains(p.getName()) || !core.pauses().getDefenserWait().contains(p.getName()))
            {
                //a remplir
            }
        }
    }
}
