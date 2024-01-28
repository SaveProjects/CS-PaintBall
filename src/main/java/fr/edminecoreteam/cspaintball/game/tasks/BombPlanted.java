package fr.edminecoreteam.cspaintball.game.tasks;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.game.Game;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
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

        if (!core.isState(State.INGAME)) { cancel(); }
        if (!core.isRoundState(RoundInfo.BOMBPLANTED)) { cancel(); }

        core.timers(timer);

        if (core.teams().getDefenser().size() == core.teams().getDefenserDeath().size())
        {
            for (Player pls : core.teams().getAttacker())
            {
                pls.playSound(pls.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
                pls.sendTitle("§aBien joué ! §a✔", "§7Vous remportez la manche.");
            }
            for (Player pls : core.teams().getDefenser())
            {
                pls.playSound(pls.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                pls.sendTitle("§cReprenez-vous ! §c✖", "§7Vous perdez la manche.");
            }
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

        if (timer < 5 && timer >= 3)
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

        if (timer < 3 && timer > 0)
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
                simuleExplosion(pls, loc);
                explosionDamage(pls, loc);

            }
            for (Player pls : core.teams().getAttacker())
            {
                pls.playSound(pls.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                pls.sendTitle("§aBombe Explosée ! §a✔", "§7Gardez la cadence.");
            }
            for (Player pls : core.teams().getDefenser())
            {
                pls.playSound(pls.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                pls.sendTitle("§cBombe Explosée ! §c⚠", "§7Vous êtes en train de perdre le contrôle !");
            }
            core.setRoundState(RoundInfo.BOMBEXPLODE);
            Game game = new Game();
            game.endRound();
            cancel();
        }

        --timer;
    }

    private void simuleExplosion(Player player, Location loc) {
        int size = 13;

        for (int x = -size; x <= size; x++) {
            for (int y = -size; y <= size; y++) {
                for (int z = -size; z <= size; z++) {
                    double distance = Math.sqrt(x * x + y * y + z * z);

                    if (distance <= size) {
                        Location particleLocation = loc.clone().add(x, y, z);
                        player.getWorld().playEffect(particleLocation, org.bukkit.Effect.EXPLOSION_LARGE, 0);
                    }
                }
            }
        }
    }

    private void explosionDamage(Player player, Location loc) {

        int size = 50;
        int damageMax = 40;

        player.getWorld().createExplosion(loc, 0.0F);

        for (Entity entity : player.getNearbyEntities(size, size, size)) {
            if (entity instanceof Player) {
                Player victim = (Player) entity;
                double distance = loc.distance(victim.getLocation());

                double damage = damageMax * (1 - distance / size);
                victim.damage(damage);
            }
        }
    }
}
