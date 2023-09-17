package fr.edminecoreteam.cspaintball.game;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.game.tasks.End;
import fr.edminecoreteam.cspaintball.game.tasks.Preparation;
import fr.edminecoreteam.cspaintball.game.tasks.Start;
import fr.edminecoreteam.cspaintball.game.teams.TeamsKit;
import fr.edminecoreteam.cspaintball.game.utils.BarUtil;
import fr.edminecoreteam.cspaintball.game.utils.LoadHolograms;
import fr.edminecoreteam.cspaintball.game.weapons.bombe.Bombe;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Game
{

    private static Core core = Core.getInstance();

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

    public void preparationRound()
    {
        for (Player attackers : core.teams().getAttacker())
        {
            attackers.teleport(attackerSpawn);
            if (core.teams().getAttackerDeath().contains(attackers) || core.roundManager().getRound() == 1)
            {
                TeamsKit kit = new TeamsKit();
                kit.equipDefault(attackers);
                if (core.teams().getAttackerDeath().contains(attackers))
                {
                    core.teams().getAttackerDeath().remove(attackers);
                }
            }
            if (!core.teams().getAttackerDeath().contains(attackers) && core.roundManager().getRound() != 1)
            {
                TeamsKit kit = new TeamsKit();
                kit.equipNotDeathDefault(attackers);
            }
        }

        for (Player defensers : core.teams().getDefenser())
        {
            defensers.teleport(defenserSpawn);
            if (core.teams().getDefenserDeath().contains(defensers) || core.roundManager().getRound() == 1)
            {
                TeamsKit kit = new TeamsKit();
                kit.equipDefault(defensers);
                if (core.teams().getDefenserDeath().contains(defensers))
                {
                    core.teams().getDefenserDeath().remove(defensers);
                }
            }
            else if (!core.teams().getDefenserDeath().contains(defensers) && core.roundManager().getRound() != 1)
            {
                TeamsKit kit = new TeamsKit();
                kit.equipNotDeathDefault(defensers);
            }
        }
        Bombe bomb = new Bombe();
        bomb.getRandom();
        core.setRoundState(RoundInfo.PREPARATION);
        for (Player pls : core.getServer().getOnlinePlayers())
        {
            BarUtil.sendBar(pls, "", 100);
        }
        Preparation preparation = new Preparation(core);
        preparation.runTaskTimer((Plugin) core, 0L, 20L);
    }

    public void startRound()
    {
        LoadHolograms holograms = new LoadHolograms();
        holograms.init();
        Start start = new Start(core);
        start.runTaskTimer((Plugin) core, 0L, 20L);
    }

    public void endRound()
    {
        if (Math.abs(core.pointsManager().getAttackerPoints() - core.pointsManager().getDefenserPoints()) == 8) { endGame(); return; }
        if (core.getConfig().getString("time").equalsIgnoreCase("short"))
        {
            if (core.roundManager().getRound() == core.getConfig().getInt("timers.rounds-short")) { changeTeam(); return; }
            if (core.roundManager().getRound() == core.getConfig().getInt("timers.rounds-short") * 2) { endGame(); return; }
        }
        if (core.getConfig().getString("time").equalsIgnoreCase("long"))
        {
            if (core.roundManager().getRound() == core.getConfig().getInt("timers.rounds-long")) { changeTeam(); return; }
            if (core.roundManager().getRound() == core.getConfig().getInt("timers.rounds-long") * 2) { endGame(); return; }
        }

        End end = new End(core);
        end.runTaskTimer((Plugin) core, 0L, 20L);
    }

    public void changeTeam()
    {
        List<Player> attackers = new ArrayList<Player>();
        List<Player> defensers = new ArrayList<Player>();
        int attackerScore = core.pointsManager().getAttackerPoints();
        int defenserScore = core.pointsManager().getDefenserPoints();

        for (Player pls : core.teams().getAttacker())
        {
            attackers.add(pls);
        }

        for (Player pls : core.teams().getDefenser())
        {
            defensers.add(pls);
        }

        for (Player pls : core.teams().getAttacker())
        {
            core.teams().getAttacker().remove(pls);
        }

        for (Player pls : core.teams().getDefenser())
        {
            core.teams().getDefenser().remove(pls);
        }

        for (Player pls : attackers)
        {
            core.teams().joinTeam(pls, "defenser");
        }

        for (Player pls : defensers)
        {
            core.teams().joinTeam(pls, "attacker");
        }

        core.pointsManager().setAttackerPoints(defenserScore);
        core.pointsManager().setDefenserPoints(attackerScore);

        End end = new End(core);
        end.runTaskTimer((Plugin) core, 0L, 20L);
    }

    public void endGame()
    {

    }
}
