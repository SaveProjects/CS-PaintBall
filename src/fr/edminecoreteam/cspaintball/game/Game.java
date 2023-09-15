package fr.edminecoreteam.cspaintball.game;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.game.tasks.Preparation;
import fr.edminecoreteam.cspaintball.game.teams.TeamsKit;
import fr.edminecoreteam.cspaintball.game.utils.BarUtil;
import fr.edminecoreteam.cspaintball.game.utils.Other;
import fr.edminecoreteam.cspaintball.waiting.tasks.AutoStart;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
            }
        }

        for (Player defensers : core.teams().getDefenser())
        {
            defensers.teleport(defenserSpawn);
            if (core.teams().getDefenserDeath().contains(defensers) || core.roundManager().getRound() == 1)
            {
                TeamsKit kit = new TeamsKit();
                kit.equipDefault(defensers);
            }
        }

        core.setState(State.INGAME);
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

    }

    public void endRound()
    {
        if (Math.abs(core.pointsManager().getAttackerPoints() - core.pointsManager().getDefenserPoints()) == 8) { endGame(); return; }
        if (core.getConfig().getString("time").equalsIgnoreCase("short"))
        {
            if (core.roundManager().getRound() == core.getConfig().getInt("rounds-short")) { changeTeam(); return; }
            if (core.roundManager().getRound() == core.getConfig().getInt("rounds-short") * 2) { endGame(); return; }
        }
        else if (core.getConfig().getString("time").equalsIgnoreCase("long"))
        {
            if (core.roundManager().getRound() == core.getConfig().getInt("rounds-long")) { changeTeam(); return; }
            if (core.roundManager().getRound() == core.getConfig().getInt("rounds-long") * 2) { endGame(); return; }
        }



    }

    public void changeTeam()
    {

    }

    public void endGame()
    {

    }
}
