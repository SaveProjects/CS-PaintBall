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
import org.bukkit.Material;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

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

            Location attackerSpawnNotEye = new Location(Bukkit.getWorld(p.getWorld().getName()),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.x"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.y"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.z"));

            Location defenserSpawn = new Location(Bukkit.getWorld(p.getWorld().getName()),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.x"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.y"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.z"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.f"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.t"));

            Location defenserSpawnNotEye = new Location(Bukkit.getWorld(p.getWorld().getName()),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.x"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.y"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.z"));

            if (core.teams().getAttacker().contains(p))
            {
                if (!p.getLocation().equals(attackerSpawnNotEye))
                {
                    p.teleport(attackerSpawn);
                }
            }
            if (core.teams().getDefenser().contains(p))
            {
                if (!p.getLocation().equals(defenserSpawnNotEye))
                {
                    p.teleport(defenserSpawn);
                }
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
                        if (haveBomb(victim, "§fBombe §c§lC4") != null)
                        {
                            ItemStack bomb = haveBomb(victim, "§fBombe §c§lC4");

                            victim.getWorld().dropItem(victim.getLocation(), bomb);
                            victim.getInventory().remove(bomb);
                        }
                        int[] slotsToRemove = {0, 1, 2};
                        dropItemsFromSlots(victim, slotsToRemove);
                        AttackerSpec spec = new AttackerSpec();
                        spec.setSpec(victim);
                        core.teams().getAttackerDeath().add(victim);
                        victim.spigot().respawn();
                        return;
                    }

                    if (core.teams().getDefenser().contains(victim))
                    {
                        int[] slotsToRemove = {0, 1, 2};
                        dropItemsFromSlots(victim, slotsToRemove);
                        DefenserSpec spec = new DefenserSpec();
                        spec.setSpec(victim);
                        core.teams().getDefenserDeath().add(victim);
                        victim.spigot().respawn();
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
                    int[] slotsToRemove = {0, 1, 2};
                    dropItemsFromSlots(victim, slotsToRemove);
                    AttackerSpec spec = new AttackerSpec();
                    spec.setSpec(victim);
                    core.teams().getAttackerDeath().add(victim);
                    victim.spigot().respawn();
                    return;
                }

                if (core.teams().getDefenser().contains(victim))
                {
                    int[] slotsToRemove = {0, 1, 2};
                    dropItemsFromSlots(victim, slotsToRemove);
                    DefenserSpec spec = new DefenserSpec();
                    spec.setSpec(victim);
                    core.teams().getDefenserDeath().add(victim);
                    victim.spigot().respawn();
                    return;
                }
            }
        }
    }

    public ItemStack haveBomb(Player player, String customName)
    {
        Inventory playerInventory = player.getInventory();
        for (ItemStack item : playerInventory.getContents())
        {
            if (item != null && item.getType() == Material.SKULL_ITEM)
            {
                ItemMeta meta = item.getItemMeta();
                if (meta instanceof SkullMeta)
                {
                    SkullMeta skullMeta = (SkullMeta) meta;
                    if (skullMeta.hasDisplayName() && skullMeta.getDisplayName().equals(customName))
                    {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    public void dropItemsFromSlots(Player player, int[] slotsToDrop)
    {
        for (int slot : slotsToDrop)
        {
            if (slot >= 0 && slot < player.getInventory().getSize())
            {
                ItemStack item = player.getInventory().getItem(slot);

                if (item != null && (isIronSword(item) || isCustomHead(item)))
                {
                    player.getWorld().dropItem(player.getLocation(), item);
                    player.getInventory().setItem(slot, null);
                }
            }
        }
    }

    private boolean isIronSword(ItemStack item)
    {
        return item.getType() == Material.IRON_SWORD;
    }

    private boolean isCustomHead(ItemStack item)
    {
        if (item.getType() == Material.SKULL_ITEM && item.getDurability() == 3)
        {
            ItemMeta meta = item.getItemMeta();
            return meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eBoutique D'Armement");
        }
        return false;
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
