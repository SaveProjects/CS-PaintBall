package fr.edminecoreteam.cspaintball.content.game.tasks;

import fr.edminecoreteam.api.EdmineAPISpigot;
import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.content.game.Game;
import fr.edminecoreteam.cspaintball.content.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.utils.game.GameUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Preparation extends BukkitRunnable
{
    public int timer;

    private final Core core;

    private final GameUtils gameUtils;

    public Preparation(Core core)
    {
        this.core = core;
        this.timer = 15;
        this.gameUtils = new GameUtils();
    }

    public void run()
    {
        if (!core.isState(State.INGAME)) { cancel(); }
        core.timers(timer);
        for (Player pls : core.getServer().getOnlinePlayers()) { pls.setLevel(timer); }
        EdmineAPISpigot.getInstance().getBossBarBuilder().setTitle("§fPréparation: §e" + timer + "§es");
        EdmineAPISpigot.getInstance().getBossBarBuilder().setHealth(timer, 15);

        for (Player pls : core.getServer().getOnlinePlayers()) {
            if (timer <= 15 && timer != 5 && timer != 4 && timer != 3 && timer != 2 && timer != 1) {
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 1.0f);
            }
        }
        for (Player pls : core.getServer().getOnlinePlayers()) {
            GameUtils gameUtils = new GameUtils();
            gameUtils.showAllPlayers(pls);
        }
        if (timer == 5)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 1.5f);
            }
        }
        if (timer == 4)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 1.2f);
            }
        }
        if (timer == 3)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 1.0f);
            }
        }
        if (timer == 2)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 0.7f);
            }
        }
        if (timer == 1)
        {
            for (Player pls : core.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.NOTE_STICKS, 1.0f, 0.5f);
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
