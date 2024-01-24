package fr.edminecoreteam.cspaintball.game.weapons;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class WeaponsSounds
{
    private Player p;

    private static Core core = Core.getInstance();

    public WeaponsSounds(Player p)
    {
        this.p = p;
    }

    public void shoot(String sound)
    {
        if (sound.equalsIgnoreCase("nobullet"))
        {
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(p.getLocation(), Sound.CLICK, 0.8f, 1.5f);
            }
        }
        if (sound.equalsIgnoreCase("silent"))
        {
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(p.getLocation(), Sound.EXPLODE, 0.2f, 4.0f);
            }
        }
        if (sound.equalsIgnoreCase("normal"))
        {
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(p.getLocation(), Sound.EXPLODE, 0.5f, 1.6f);
            }
        }
        if (sound.equalsIgnoreCase("noisy"))
        {
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(p.getLocation(), Sound.EXPLODE, 0.6f, 1.2f);
            }
        }
        if (sound.equalsIgnoreCase("extremenoisy"))
        {
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(p.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
            }
        }
        if (sound.equalsIgnoreCase("sniper"))
        {
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(p.getLocation(), Sound.EXPLODE, 4.0f, 0.5f);
            }
        }
    }

    public void refill(String sound)
    {
        if (sound.equalsIgnoreCase("2s"))
        {
            new BukkitRunnable() {
                int t = 0;
                int f = 0;
                public void run() {

                    ++t;
                    ++f;
                    if (f == 1) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_EXTEND, 0.5f, 1.0f);
                        }
                    }

                    if (f == 2) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_RETRACT, 0.5f, 1.0f);
                        }
                        cancel();
                    }

                    if (t == 1) {
                        t = 0;
                    }
                }
            }.runTaskTimer((Plugin) core, 0L, 20L);
        }
        if (sound.equalsIgnoreCase("3s"))
        {
            new BukkitRunnable() {
                int t = 0;
                int f = 0;
                public void run() {

                    ++t;
                    ++f;
                    if (f == 1) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_EXTEND, 0.5f, 1.0f);
                        }
                    }

                    if (f == 2) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_EXTEND, 0.5f, 0.7f);
                        }
                    }

                    if (f == 3) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_RETRACT, 0.5f, 1.0f);
                        }
                        cancel();
                    }

                    if (t == 1) {
                        t = 0;
                    }
                }
            }.runTaskTimer((Plugin) core, 0L, 20L);
        }
        if (sound.equalsIgnoreCase("pompe"))
        {
            new BukkitRunnable() {
                int t = 0;
                int f = 0;
                public void run() {

                    ++t;
                    ++f;
                    if (f == 1) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_EXTEND, 0.5f, 1.0f);
                        }
                    }

                    if (f == 2) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.NOTE_STICKS, 0.5f, 0.9f);
                        }
                    }

                    if (f == 3) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.NOTE_STICKS, 0.5f, 0.9f);
                        }
                    }

                    if (f == 4) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.NOTE_STICKS, 0.5f, 0.9f);
                        }
                    }

                    if (f == 5) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.NOTE_STICKS, 0.5f, 0.9f);
                        }
                    }

                    if (f == 6) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.NOTE_STICKS, 0.5f, 0.9f);
                        }
                    }

                    if (f == 7) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.NOTE_STICKS, 0.5f, 0.9f);
                        }
                    }

                    if (f == 8) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.NOTE_STICKS, 0.5f, 0.9f);
                        }
                    }

                    if (f == 9) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_RETRACT, 0.5f, 1.0f);
                        }
                        cancel();
                    }

                    if (t == 1) {
                        t = 0;
                    }
                }
            }.runTaskTimer((Plugin) core, 0L, 7L);
        }
    }

    public void armed(String sound)
    {
        if (sound.equalsIgnoreCase("classic"))
        {
            new BukkitRunnable() {
                int t = 0;
                int f = 0;
                public void run() {

                    ++t;
                    ++f;
                    if (f == 1) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_EXTEND, 0.5f, 1.5f);
                        }
                    }

                    if (f == 2) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_RETRACT, 0.5f, 1.5f);
                        }
                        cancel();
                    }

                    if (t == 1) {
                        t = 0;
                    }
                }
            }.runTaskTimer((Plugin) core, 0L, 3L);
        }

        if (sound.equalsIgnoreCase("pompe"))
        {
            new BukkitRunnable() {
                int t = 0;
                int f = 0;
                public void run() {

                    ++t;
                    ++f;
                    if (f == 1) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_EXTEND, 0.5f, 1.5f);
                        }
                    }

                    if (f == 2) {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            pls.playSound(p.getLocation(), Sound.PISTON_RETRACT, 0.5f, 1.5f);
                        }
                        cancel();
                    }

                    if (t == 1) {
                        t = 0;
                    }
                }
            }.runTaskTimer((Plugin) core, 0L, 3L);
        }
    }
}
