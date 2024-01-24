package fr.edminecoreteam.cspaintball.game.tasks;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.game.Game;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.utils.dragonbar.BarUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Preparation extends BukkitRunnable
{
    public int timer;

    private Core core;

    public Preparation(Core core)
    {
        this.core = core;
        this.timer = 15;
    }

    public void run()
    {
        core.timers(timer);
        for (Player pls : core.getServer().getOnlinePlayers())
        {
            BarUtil.updateBar(pls, "§7Temps de préparation: §a" + core.timers, 100);
        }

        for (Player pls : core.getServer().getOnlinePlayers()) {
            pls.setLevel(timer);
            if (timer != 5 && timer != 4 && timer != 3 && timer != 2 && timer != 1) {
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 1.0f);
            }
        }
        if (timer == 5)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);
            }
        }
        if (timer == 4)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 1.0f, 1.2f);
            }
        }
        if (timer == 3)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
            }
        }
        if (timer == 2)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 1.0f, 0.7f);
            }
        }
        if (timer == 1)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 1.0f, 0.5f);
            }
        }
        if (timer == 0)
        {
            core.setRoundState(RoundInfo.START);
            Game game = new Game();
            game.startRound();
            cancel();
        }

        --timer;
    }
}
