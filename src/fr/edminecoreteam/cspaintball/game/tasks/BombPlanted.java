package fr.edminecoreteam.cspaintball.game.tasks;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.game.Game;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BombPlanted extends BukkitRunnable
{
    public int timer;

    private Core core;

    public BombPlanted(Core core)
    {
        this.core = core;
        this.timer = core.getConfig().getInt("timers.bomb");
    }

    public void run()
    {
        core.timers(timer);

        if (!core.isRoundState(RoundInfo.BOMBPLANTED)) { cancel(); }

        if (core.teams().getDefenser().size() == core.teams().getDefenserDeath().size())
        {
            core.pointsManager().addAttackerPoints();
            core.setRoundState(RoundInfo.END);
            Game game = new Game();
            game.endRound();
            cancel();
        }


        if (timer == 0)
        {
            core.setRoundState(RoundInfo.BOMBEXPLODE);
            core.pointsManager().addAttackerPoints();
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(pls.getLocation(), Sound.EXPLODE, 1.0f, 0.4f);
            }
            Game game = new Game();
            game.endRound();
            cancel();
        }

        --timer;
    }
}
