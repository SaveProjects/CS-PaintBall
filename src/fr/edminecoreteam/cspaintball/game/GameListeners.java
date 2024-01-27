package fr.edminecoreteam.cspaintball.game;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.game.spec.AttackerSpec;
import fr.edminecoreteam.cspaintball.game.spec.DefenserSpec;
import fr.edminecoreteam.cspaintball.game.teams.TeamsKit;
import fr.edminecoreteam.cspaintball.game.utils.GameUtils;
import fr.edminecoreteam.cspaintball.game.weapons.Weapons;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class GameListeners implements Listener
{
    private static Core core = Core.getInstance();

    private ItemStack haveBomb(Player player, String customName)
    { GameUtils u = new GameUtils(); return u.haveBomb(player, customName); }
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

            Location playerLocation = core.spawnListeners().getPlayerLocation(p);
            Location pLoc = p.getLocation();

            if (playerLocation.getX() != pLoc.getX() || playerLocation.getY() != pLoc.getY() || playerLocation.getZ() != pLoc.getZ())
            {
                p.teleport(playerLocation);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e)
    {
        if (core.isState(State.INGAME))
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
                        attacker.playSound(attacker.getLocation(), Sound.VILLAGER_HIT, 1.0f, 1.0f);
                    }
                    attacker.playSound(attacker.getLocation(), Sound.NOTE_PLING, 1.0f, 5.0f);
                    TeamsKit kit = new TeamsKit();
                    kit.repearArmor(victim);
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
                        attacker.playSound(attacker.getLocation(), Sound.VILLAGER_HIT, 1.0f, 1.0f);
                    }
                    attacker.playSound(attacker.getLocation(), Sound.NOTE_PLING, 1.0f, 5.0f);
                    TeamsKit kit = new TeamsKit();
                    kit.repearArmor(victim);
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e)
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
                        attacker.playSound(attacker.getLocation(), Sound.VILLAGER_DEATH, 1.0f, 1.0f);
                        if (core.teams().getAttacker().contains(attacker))
                        {
                            e.setDeathMessage("§c" + attacker.getName() + " §f➡ ☠ §c" + victim.getName());
                        }
                        if (core.teams().getDefenser().contains(attacker))
                        {
                            e.setDeathMessage("§9" + attacker.getName() + " §f➡ ☠ §9" + victim.getName());
                        }
                    }

                    if (core.teams().getAttacker().contains(victim))
                    {
                        if (haveBomb(victim, "§fBombe §c§lC4") != null)
                        {
                            ItemStack bomb = haveBomb(victim, "§fBombe §c§lC4");

                            victim.getWorld().dropItem(victim.getLocation(), bomb);
                            victim.getInventory().remove(bomb);
                        }
                        Weapons weapons = new Weapons(victim);
                        weapons.deathCheck();
                        AttackerSpec spec = new AttackerSpec();
                        spec.setSpec(victim);
                        core.teams().getAttackerDeath().add(victim);
                        victim.spigot().respawn();
                        e.setDeathMessage("§9" + attacker.getName() + " §f➡ ☠ §c" + victim.getName());
                        attacker.playSound(attacker.getLocation(), Sound.VILLAGER_YES, 1.0f, 5.0f);
                        return;
                    }
                    if (core.teams().getDefenser().contains(victim))
                    {
                        Weapons weapons = new Weapons(victim);
                        weapons.deathCheck();
                        DefenserSpec spec = new DefenserSpec();
                        spec.setSpec(victim);
                        core.teams().getDefenserDeath().add(victim);
                        victim.spigot().respawn();
                        e.setDeathMessage("§c" + attacker.getName() + " §f➡ ☠ §9" + victim.getName());
                        attacker.playSound(attacker.getLocation(), Sound.VILLAGER_YES, 1.0f, 5.0f);
                        return;
                    }
                }
            }
            else if (victim.getKiller() == null)
            {
                if (core.teams().getAttacker().contains(victim))
                {
                    if (haveBomb(victim, "§fBombe §c§lC4") != null)
                    {
                        ItemStack bomb = haveBomb(victim, "§fBombe §c§lC4");

                        victim.getWorld().dropItem(victim.getLocation(), bomb);
                        victim.getInventory().remove(bomb);
                    }
                    Weapons weapons = new Weapons(victim);
                    weapons.deathCheck();
                    AttackerSpec spec = new AttackerSpec();
                    spec.setSpec(victim);
                    core.teams().getAttackerDeath().add(victim);
                    victim.spigot().respawn();
                    e.setDeathMessage("§f☠ §c" + victim.getName());
                    return;
                }
                if (core.teams().getDefenser().contains(victim))
                {
                    Weapons weapons = new Weapons(victim);
                    weapons.deathCheck();
                    DefenserSpec spec = new DefenserSpec();
                    spec.setSpec(victim);
                    core.teams().getDefenserDeath().add(victim);
                    victim.spigot().respawn();
                    e.setDeathMessage("§f☠ §9" + victim.getName());
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (core.isState(State.INGAME))
        {
            e.setCancelled(true);
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
