package fr.edminecoreteam.cspaintball.game.tasks;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.game.Game;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BombPlanted extends BukkitRunnable
{
    public int timer;

    private final Core core;
    private final Location loc;

    public BombPlanted(Core core, Location loc)
    {
        this.core = core;
        this.timer = core.getConfig().getInt("timers.bomb");
        this.loc = loc;
    }

    public void run()
    {

        if (!core.isRoundState(RoundInfo.BOMBPLANTED)) { cancel(); }

        core.timers(timer);

        if (core.teams().getDefenser().size() == core.teams().getDefenserDeath().size())
        {
            core.pointsManager().addAttackerPoints();
            core.setRoundState(RoundInfo.END);
            Game game = new Game();
            game.endRound();
            cancel();
        }

        if (timer >= 10)
        {
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
            }
        }

        if (timer < 10 && timer >= 5)
        {
            new BukkitRunnable() {
                int t = 0;
                public void run() {

                    ++t;

                    if (t == 1) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 2) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                        cancel();
                    }
                }
            }.runTaskTimer((Plugin) core, 0L, 10L);
        }

        if (timer < 5 && timer >= 2)
        {
            new BukkitRunnable() {
                int t = 0;
                public void run() {

                    ++t;

                    if (t == 1) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 2) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 3) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 4) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                        cancel();
                    }
                }
            }.runTaskTimer((Plugin) core, 0L, 5L);
        }

        if (timer < 2 && timer >= 0)
        {
            new BukkitRunnable() {
                int t = 0;
                public void run() {

                    ++t;

                    if (t == 1) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 2) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 3) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 4) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 5) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 6) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 7) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 8) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 9) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                    if (t == 10) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(loc, Sound.NOTE_PLING, 1.5f, 3.0f);
                        }
                    }
                }
            }.runTaskTimer((Plugin) core, 0L, 2L);
        }

        if (timer == 0)
        {
            core.pointsManager().addAttackerPoints();
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(pls.getLocation(), Sound.EXPLODE, 5.0f, 0.4f);
            }
            core.setRoundState(RoundInfo.BOMBEXPLODE);
            Game game = new Game();
            game.endRound();
            cancel();
        }

        --timer;
    }
}
