package fr.edminecoreteam.cspaintball.game.tasks;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.game.Game;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.game.utils.BarUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Start extends BukkitRunnable
{
    public int timer;

    private final Core core;
    private final int getTimer;

    public Start(Core core)
    {
        this.core = core;
        this.timer = core.getConfig().getInt("timers.round");
        this.getTimer = this.timer;
    }

    public void run()
    {
        core.timers(timer);

        if (!core.isRoundState(RoundInfo.START)) { cancel(); }

        if (core.teams().getDefenser().size() == core.teams().getDefenserDeath().size())
        {
            if (core.isRoundState(RoundInfo.START))
            {
                core.pointsManager().addAttackerPoints();
                core.setRoundState(RoundInfo.END);
                Game game = new Game();
                game.endRound();
                cancel();
            }
        }

        if (core.teams().getAttacker().size() == core.teams().getAttackerDeath().size())
        {
            if (core.isRoundState(RoundInfo.START))
            {
                core.pointsManager().addDefenserPoints();
                core.setRoundState(RoundInfo.END);
                Game game = new Game();
                game.endRound();
                cancel();
            }
        }

        int finalResult = this.getTimer - 5;

        if (timer == finalResult)
        {
            for (Player pls : Bukkit.getOnlinePlayers())
            {
                if (core.teams().getAttacker().contains(pls) || core.teams().getDefenser().contains(pls))
                {
                    pls.getInventory().setItem(3, new ItemStack(0));
                    pls.sendMessage("§6Période d'achat expiré.");
                    pls.closeInventory();
                }
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
            if (core.isRoundState(RoundInfo.START))
            {
                core.setRoundState(RoundInfo.END);
                core.pointsManager().addDefenserPoints();
                Game game = new Game();
                game.endRound();
                cancel();
            }
        }

        --timer;
    }
}
