package fr.edminecoreteam.cspaintball.game;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.game.guis.BuyMenu;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.game.spec.AttackerSpec;
import fr.edminecoreteam.cspaintball.game.spec.DefenserSpec;
import fr.edminecoreteam.cspaintball.game.teams.TeamsKit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameListeners implements Listener
{
    private static Core core = Core.getInstance();



    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        if (core.isRoundState(RoundInfo.PREPARATION))
        {
            Player p = e.getPlayer();
            if (!core.teams().getAttacker().contains(p) && !core.teams().getDefenser().contains(p))
            {
                return;
            }

            Location attackerSpawn = new Location(Bukkit.getWorld(p.getWorld().getName()),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.x"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.y"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.z"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.f"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.t"));

            Location defenserSpawn = new Location(Bukkit.getWorld(p.getWorld().getName()),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.x"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.y"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.z"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.f"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.t"));

            if (core.teams().getAttacker().contains(p))
            {
                p.teleport(attackerSpawn);
            }
            if (core.teams().getDefenser().contains(p))
            {
                p.teleport(defenserSpawn);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent e)
    {
        if (e.getEntityType() == EntityType.PLAYER)
        {
            if (e.getDamager().getType() == EntityType.SNOWBALL)
            {
                Player victim = (Player) e.getEntity();
                Snowball damage = (Snowball) e.getDamager();
                Player attacker = (Player) damage.getShooter();

                if (core.teams().getTeam(victim).contains(attacker))
                {
                    for(Player pls : core.teams().getTeam(victim))
                    {
                        pls.sendMessage("§c§l" + attacker.getName() + " §ca attaqué un allié...");
                    }
                }
                TeamsKit kit = new TeamsKit();
                kit.reEquip(victim);
            }
            if (e.getDamager().getType() == EntityType.PLAYER)
            {
                Player victim = (Player) e.getEntity();
                Player attacker = (Player) e.getDamager();

                if (core.teams().getTeam(victim).contains(attacker))
                {
                    for(Player pls : core.teams().getTeam(victim))
                    {
                        pls.sendMessage("§c§l" + attacker.getName() + " §ca attaqué un allié...");
                    }
                }
                TeamsKit kit = new TeamsKit();
                kit.reEquip(victim);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e)
    {
        if (e.getEntityType() == EntityType.PLAYER)
        {
            Player victim = (Player) e.getEntity();
            if (victim.getKiller() != null)
            {
                if (victim.getKiller().getType() == EntityType.PLAYER)
                {
                    Player attacker = (Player) victim.getKiller();
                    if (core.teams().getTeam(victim).contains(attacker))
                    {
                        for(Player pls : core.teams().getTeam(victim))
                        {
                            pls.sendMessage("§c§l" + attacker.getName() + " §ca tué un allié...");
                        }
                    }

                    if (core.teams().getAttacker().contains(victim))
                    {
                        victim.spigot().respawn();
                        AttackerSpec spec = new AttackerSpec();
                        spec.setSpec(victim);
                        core.teams().getAttackerDeath().add(victim);
                        return;
                    }

                    if (core.teams().getDefenser().contains(victim))
                    {
                        victim.spigot().respawn();
                        DefenserSpec spec = new DefenserSpec();
                        spec.setSpec(victim);
                        core.teams().getDefenserDeath().add(victim);
                        return;
                    }
                }
            }
            else if (victim.getKiller() == null)
            {
                if (core.teams().getAttacker().contains(victim))
                {
                    victim.spigot().respawn();
                    AttackerSpec spec = new AttackerSpec();
                    spec.setSpec(victim);
                    core.teams().getAttackerDeath().add(victim);
                    return;
                }

                if (core.teams().getDefenser().contains(victim))
                {
                    victim.spigot().respawn();
                    DefenserSpec spec = new DefenserSpec();
                    spec.setSpec(victim);
                    core.teams().getDefenserDeath().add(victim);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDamageEvent e)
    {
        if (e.getEntityType() == EntityType.PLAYER)
        {
            Player victim = (Player) e.getEntity();
            TeamsKit kit = new TeamsKit();
            kit.reEquip(victim);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (core.isState(State.INGAME) || core.isState(State.FINISH))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void openInventory(InventoryOpenEvent e)
    {
        if (e.getInventory().getType() != InventoryType.CHEST)
        {
            e.setCancelled(true);
            BuyMenu buy = new BuyMenu();
            Player p = (Player) e.getPlayer();
            buy.gui(p);
        }
    }

    @EventHandler
    public void hungerBarChange(FoodLevelChangeEvent e)
    {
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }

        if (core.isState(State.INGAME)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent e) {
        Entity ent = e.getRightClicked();
        Player p = e.getPlayer();
        if (ent instanceof ArmorStand)
        {
            e.setCancelled(true);
            return;
        }
    }

}
