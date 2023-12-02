package fr.edminecoreteam.cspaintball.game.tasks;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.game.Game;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class End extends BukkitRunnable
{
    public int timer;

    private Core core;

    public End(Core core)
    {
        this.core = core;
        this.timer = 6;
    }

    public void run()
    {
        if (!core.isRoundState(RoundInfo.END) && !core.isRoundState(RoundInfo.BOMBEXPLODE) && !core.isRoundState(RoundInfo.BOMBDIFUSE)) { cancel(); }

        core.timers(timer);


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
            core.roundManager().addRound();
            for (ArmorStand armorStand : Bukkit.getWorld("game").getEntitiesByClass(ArmorStand.class))
            {
                armorStand.remove();
            }
            Game game = new Game();
            game.preparationRound();
            cancel();
        }

        --timer;
    }
}
