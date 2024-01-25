package fr.edminecoreteam.cspaintball.listeners.connection;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.game.spec.AttackerSpec;
import fr.edminecoreteam.cspaintball.game.spec.DefenserSpec;
import fr.edminecoreteam.cspaintball.game.teams.TeamsKit;
import fr.edminecoreteam.cspaintball.waiting.WaitingListeners;
import fr.edminecoreteam.cspaintball.waiting.tasks.AutoStart;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class JoinEvent implements Listener
{
    private final static Core core = Core.getInstance();

    private final Location spawn = new Location(Bukkit.getWorld(
            core.getConfig().getString("spawn.world")),
            (float) core.getConfig().getDouble("spawn.x"),
            (float) core.getConfig().getDouble("spawn.y"),
            (float) core.getConfig().getDouble("spawn.z"),
            (float) core.getConfig().getDouble("spawn.f"),
            (float) core.getConfig().getDouble("spawn.t"));

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

    @EventHandler
    public void event(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        e.setJoinMessage(null);
        if (core.isState(State.WAITING) || core.isState(State.STARTING))
        {
            if (core.getPlayersInGame().size() == core.getMaxplayers())
            {
                p.kickPlayer("§cPartie pleine...");
                return;
            }

            if (!core.getPlayersInGame().contains(p.getName()))
            {
                core.getPlayersInGame().add(p.getName());
                core.getServer().broadcastMessage("§e" + p.getName() + "§7 a rejoint le jeu. §d" + core.getServer().getOnlinePlayers().size() + "§d/" + core.getMaxplayers());

                p.teleport(spawn);
                get(p);
                p.playSound(p.getLocation(), Sound.NOTE_PLING, 0.5f, 1.0f);

                if (core.getConfig().getString("type").equalsIgnoreCase("ranked"))
                {
                    if (core.getPlayersInGame().size() == core.getMaxplayers())
                    {
                        core.setState(State.STARTING);
                        AutoStart start = new AutoStart(core);
                        start.runTaskTimer((Plugin) core, 0L, 20L);
                    }
                }
                else if (core.getConfig().getString("type").equalsIgnoreCase("unranked"))
                {
                    if (core.getPlayersInGame().size() == core.getConfig().getInt("needtostart"))
                    {
                        core.setState(State.STARTING);
                        AutoStart start = new AutoStart(core);
                        start.runTaskTimer((Plugin) core, 0L, 20L);
                    }
                }
            }
        }
        if (core.isState(State.INGAME))
        {
            if (core.pauses().getAttackerWait().contains(p.getName()))
            {
                core.pauses().desactivePause();
                core.pauses().getAttackerWait().remove(p.getName());
                core.teams().getAttacker().add(p);
                if (core.isRoundState(RoundInfo.PREPARATION))
                {
                    if (core.timers > 15)
                    {
                        core.timers = 15;
                    }
                    p.teleport(attackerSpawn);
                    TeamsKit kit = new TeamsKit();
                    kit.equipDefault(p);
                }
                else
                {
                    core.teams().getAttackerDeath().add(p);
                    AttackerSpec attackerSpec = new AttackerSpec();
                    attackerSpec.setSpec(p);
                }
            }
            if (core.pauses().getDefenserWait().contains(p.getName()))
            {
                core.pauses().desactivePause();
                core.pauses().getDefenserWait().remove(p.getName());
                core.teams().getDefenser().add(p);
                if (core.isRoundState(RoundInfo.PREPARATION))
                {
                    if (core.timers > 15)
                    {
                        core.timers = 15;
                    }
                    p.teleport(defenserSpawn);
                    TeamsKit kit = new TeamsKit();
                    kit.equipDefault(p);
                }
                else
                {
                    core.teams().getDefenserDeath().add(p);
                    DefenserSpec defenserSpec = new DefenserSpec();
                    defenserSpec.setSpec(p);
                }
            }

            if (!core.pauses().getAttackerWait().contains(p.getName()) || !core.pauses().getDefenserWait().contains(p.getName()))
            {
                //a remplir
            }
        }
    }

    private void get(Player p) {
        PlayerInventory pi = p.getInventory();
        WaitingListeners waitingListeners = new WaitingListeners(p);

        pi.setHelmet(null);
        pi.setChestplate(null);
        pi.setLeggings(null);
        pi.setBoots(null);
        p.setAllowFlight(false);
        p.setFlying(false);

        p.getInventory().clear();
        p.setLevel(0);
        p.setFoodLevel(20);
        p.setHealth(20.0);
        p.setGameMode(GameMode.ADVENTURE);
        waitingListeners.getWaitingItems();
        if (core.getConfig().getString("type").equalsIgnoreCase("ranked"))
        {
            if (core.getConfig().getString("time").equalsIgnoreCase("long"))
            {
                p.sendTitle("§e§lPaint-Ball", "§7Mode §6Compétitif §8/ §7Durée §bLongue§8.");
            }
            else if (core.getConfig().getString("time").equalsIgnoreCase("short"))
            {
                p.sendTitle("§e§lPaint-Ball", "§7Mode §6Compétitif §8/ §7Durée §bCourte§8.");
            }
        }
        if (core.getConfig().getString("type").equalsIgnoreCase("unranked"))
        {
            if (core.getConfig().getString("time").equalsIgnoreCase("long"))
            {
                p.sendTitle("§e§lPaint-Ball", "§7Mode §6Non-Compétitif §8/ §7Durée §bLongue§8.");
            }
            else if (core.getConfig().getString("time").equalsIgnoreCase("short"))
            {
                p.sendTitle("§e§lPaint-Ball", "§7Mode §6Non-Compétitif §8/ §7Durée §bCourte§8.");
            }
        }
    }
}
