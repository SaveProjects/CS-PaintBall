package fr.edminecoreteam.cspaintball.listeners.connection;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.game.Game;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.game.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class LeaveEvent implements Listener
{
    private final static Core core = Core.getInstance();

    private ItemStack haveBomb(Player player, String customName)
    { GameUtils u = new GameUtils(); return u.haveBomb(player, customName); }

    @EventHandler
    public void event(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        e.setQuitMessage(null);
        if (core.isState(State.WAITING))
        {
            if (core.getPlayersInGame().contains(p.getName()))
            {
                core.getPlayersInGame().remove(p.getName());
                int finalcount = core.getServer().getOnlinePlayers().size() - 1;
                core.getServer().broadcastMessage("§e" + p.getName() + "§7 a quitté le jeu. §d" + finalcount + "§d/" + core.getMaxplayers());

                core.teams().leaveTeam(p);
            }
        }
        if (core.isState(State.STARTING))
        {
            if (core.getPlayersInGame().contains(p.getName()))
            {
                core.getPlayersInGame().remove(p.getName());
                int finalcount = core.getServer().getOnlinePlayers().size() - 1;
                core.getServer().broadcastMessage("§e" + p.getName() + "§7 a quitté le jeu. §d" + finalcount + "§d/" + core.getMaxplayers());

                core.teams().leaveTeam(p);
            }
        }
        if (core.isState(State.INGAME))
        {
            int getMaxRound = core.getConfig().getInt("timers.rounds-short") * 2;
            int actualRound = core.roundManager().getRound();
            boolean isLeaveInPreparation = false;
            if (core.isRoundState(RoundInfo.PREPARATION)) { isLeaveInPreparation = true; }

            if (core.teams().getAttacker().contains(p))
            {
                if (haveBomb(p, "§fBombe §c§lC4") != null)
                {
                    ItemStack bomb = haveBomb(p, "§fBombe §c§lC4");

                    p.getWorld().dropItem(p.getLocation(), bomb);
                    p.getInventory().remove(bomb);
                }
                core.getServer().broadcastMessage("§c§lATTAQ. §c" + p.getName() + "§7 a quitté le jeu, il peut revenir à tout moment...");
                core.getServer().broadcastMessage("§6⚠ Un arrêt technique sera appliqué a la prochaine manche si " + p.getName() + " §6ne se reconnecte pas.");
                core.pauses().getAttackerWait().add(p.getName());
                core.teams().getAttacker().remove(p);
                if (core.teams().getAttackerDeath().contains(p))
                {
                    core.teams().getAttackerDeath().remove(p);
                }
            }
            if (core.teams().getDefenser().contains(p))
            {
                core.getServer().broadcastMessage("§9§lDEF. §9" + p.getName() + "§7 a quitté le jeu, il peut revenir à tout moment...");
                core.getServer().broadcastMessage("§6⚠ Un arrêt technique sera appliqué a la prochaine manche si " + p.getName() + " §6ne se reconnecte pas.");
                core.pauses().getDefenserWait().add(p.getName());
                core.teams().getDefenser().remove(p);
                if (core.teams().getDefenserDeath().contains(p))
                {
                    core.teams().getDefenserDeath().remove(p);
                }
            }

            core.weaponsMap().getMap().get(p).remove(p);

            if (core.teams().getAttacker().size() == 0 || core.teams().getDefenser().size() == 0)
            {
                Game game = new Game();
                game.endGame();
                game.endGame();
                return;
            }

            final boolean[] finalIsLeaveInPreparation = {isLeaveInPreparation};
            new BukkitRunnable() {
                int t = 0;
                int f = 0;
                public void run() {

                    ++t;

                    if (core.getServer().getOnlinePlayers().contains(p))
                    {
                        core.getServer().broadcastMessage("§a✔ Arrêt technique annulé, " + p.getName() + " §aest de retour.");
                        cancel();
                    }
                    if (finalIsLeaveInPreparation[0])
                    {
                        if (core.isRoundState(RoundInfo.START))
                        {
                            finalIsLeaveInPreparation[0] = false;
                        }
                    }
                    if (!finalIsLeaveInPreparation[0])
                    {
                        if (core.isRoundState(RoundInfo.PREPARATION))
                        {
                            if (core.timers > 15)
                            {
                                cancel();
                            }
                            else if (core.timers <= 15)
                            {
                                int newtimers = core.timers + 90;
                                core.timers = newtimers;
                                core.getServer().broadcastMessage("§c⚠ Arrêt technique appliqué, en attente de " + p.getName() + "§c.");
                                core.pauses().activePause();
                                cancel();
                            }
                        }
                    }

                    if (t == 1)
                    {
                        run();
                    }
                }
            }.runTaskTimer((Plugin) core, 0L, 20L);
        }
    }
}
