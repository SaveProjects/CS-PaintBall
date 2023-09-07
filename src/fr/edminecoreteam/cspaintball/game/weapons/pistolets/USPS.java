package fr.edminecoreteam.cspaintball.game.weapons.pistolets;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class USPS implements Listener
{

    private static final Core core = Core.getInstance();

    private double recoil = 0.1;
    private double speed_shoot = 5;
    private int bullet_charger = 9;
    private int max_bullet = 27;
    private Material weapon = Material.WOOD_HOE;
    private String weapon_name = "USP-s";
    private int wait_for_shoot_delay = 10;
    private int weightslow = 0;




    public void get(Player p)
    {
        if (!p.getInventory().contains(weapon))
        {
            if (!core.weaponsList().getUsps_max_bullet_count().containsKey(p) && !core.weaponsList().getUsps_bullet_charger_count().containsKey(p))
            {
                core.weaponsList().getUsps_max_bullet_count().put(p, max_bullet);
                core.weaponsList().getUsps_bullet_charger_count().put(p, bullet_charger);

                ItemStack gunStarter = new ItemStack(weapon, core.weaponsList().getUsps_bullet_charger_count().get(p));
                ItemMeta gunStarterM = gunStarter.getItemMeta();
                gunStarterM.setDisplayName("§f" + weapon_name + " §a" + core.weaponsList().getUsps_bullet_charger_count().get(p) + "§8/§a" + core.weaponsList().getUsps_max_bullet_count().get(p));
                gunStarter.setItemMeta((ItemMeta)gunStarterM);
                p.getInventory().addItem(gunStarter);
                weightcheck(p);
            }
        }
        else
        {
            if (core.weaponsList().getUsps_max_bullet_count().get(p) == 0 && core.weaponsList().getUsps_bullet_charger_count().get(p) == 0)
            {
                ItemStack[] inventory = p.getInventory().getContents();
                for (int slot = 0; slot < inventory.length; slot++) {
                    ItemStack item = inventory[slot];

                    if (item != null && item.getType() == weapon) {
                        ItemStack gunStarter = new ItemStack(weapon, 1);
                        ItemMeta gunStarterM = gunStarter.getItemMeta();
                        gunStarterM.setDisplayName("§f" + weapon_name + " §cPlus de munitions...");
                        gunStarter.setItemMeta((ItemMeta)gunStarterM);
                        p.getInventory().setItem(slot, gunStarter);
                        core.weaponsList().getUsps_max_bullet_count().remove(p);
                        core.weaponsList().getUsps_bullet_charger_count().remove(p);
                    }
                }
            }
            ItemStack[] inventory = p.getInventory().getContents();
            for (int slot = 0; slot < inventory.length; slot++) {
                ItemStack item = inventory[slot];

                if (item != null && item.getType() == weapon) {
                    ItemStack gunStarter = new ItemStack(weapon, core.weaponsList().getUsps_bullet_charger_count().get(p));
                    ItemMeta gunStarterM = gunStarter.getItemMeta();
                    gunStarterM.setDisplayName("§f" + weapon_name + " §a" + core.weaponsList().getUsps_bullet_charger_count().get(p) + "§8/§a" + core.weaponsList().getUsps_max_bullet_count().get(p));
                    gunStarter.setItemMeta((ItemMeta)gunStarterM);
                    p.getInventory().setItem(slot, gunStarter);
                }
            }
        }
    }

    /*@EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (e.getItemDrop().getItemStack().getType() == weapon)
        {
            if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains(weapon_name))
            {
                e.setCancelled(true);
                refill(p);
            }
        }
    }*/

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack it = e.getItem();
        if (it == null) { return; }

        if (it.getType() == weapon && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().contains(weapon_name)
                && (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK))
        {
            if (core.weaponsList().getUsps_max_bullet_count().containsKey(p) && core.weaponsList().getUsps_bullet_charger_count().containsKey(p))
            {
                e.setCancelled(true);
                ItemStack hand = p.getInventory().getItemInHand();

                if (!core.weaponsList().getUsps_refill().contains(p))
                {
                    if (!core.weaponsList().getUsps_wait_for_shoot().contains(p))
                    {
                        if (core.weaponsList().getUsps_bullet_charger_count().get(p) > 1)
                        {
                            int get_bullet_charger_count = core.weaponsList().getUsps_bullet_charger_count().get(p) - 1;
                            core.weaponsList().getUsps_bullet_charger_count().replace(p, get_bullet_charger_count);
                            get(p);

                            Snowball snowball = p.launchProjectile(Snowball.class);

                            double speed = speed_shoot;
                            snowball.setVelocity(p.getLocation().getDirection().multiply(speed));
                            Vector pushDirection = p.getLocation().getDirection().multiply(-recoil);
                            p.setVelocity(pushDirection);
                            for (Player pls : core.getServer().getOnlinePlayers())
                            {
                                pls.playSound(p.getLocation(), Sound.EXPLODE, 0.5f, 2.0f);
                            }
                            return;
                        }
                        if (core.weaponsList().getUsps_bullet_charger_count().get(p) == 1)
                        {
                            int get_bullet_charger_count = core.weaponsList().getUsps_bullet_charger_count().get(p) - 1;
                            core.weaponsList().getUsps_bullet_charger_count().replace(p, get_bullet_charger_count);
                            refill(p);

                            Snowball snowball = p.launchProjectile(Snowball.class);

                            double speed = speed_shoot;
                            snowball.setVelocity(p.getLocation().getDirection().multiply(speed));
                            Vector pushDirection = p.getLocation().getDirection().multiply(-recoil);
                            p.setVelocity(pushDirection);
                            for (Player pls : core.getServer().getOnlinePlayers())
                            {
                                pls.playSound(p.getLocation(), Sound.EXPLODE, 0.5f, 2.0f);
                            }
                        }
                        if (core.weaponsList().getUsps_bullet_charger_count().get(p) <= 0 || core.weaponsList().getUsps_max_bullet_count().get(p) <= 0 || !core.weaponsList().getUsps_max_bullet_count().containsKey(p))
                        {
                            get(p);
                            for (Player pls : core.getServer().getOnlinePlayers())
                            {
                                pls.playSound(p.getLocation(), Sound.CLICK, 0.8f, 1.5f);
                            }
                        }
                    }
                }
                else
                {
                    for (Player pls : core.getServer().getOnlinePlayers())
                    {
                        pls.playSound(p.getLocation(), Sound.CLICK, 0.8f, 1.5f);
                    }
                }
            }
            else
            {
                for (Player pls : core.getServer().getOnlinePlayers())
                {
                    pls.playSound(p.getLocation(), Sound.CLICK, 0.8f, 1.5f);
                }
            }

        }
        if (it.getType() == weapon && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().contains(weapon_name)
                && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK))
        {
            refill(p);
        }
    }

    public void refill(Player p)
    {
        if (core.weaponsList().getUsps_max_bullet_count().containsKey(p) && core.weaponsList().getUsps_bullet_charger_count().containsKey(p))
        {
            int diff = bullet_charger - core.weaponsList().getUsps_bullet_charger_count().get(p);
            if (core.weaponsList().getUsps_max_bullet_count().get(p) > 0)
            {
                if (core.weaponsList().getUsps_max_bullet_count().get(p) >= diff)
                {
                    int new_max_bullet_count = core.weaponsList().getUsps_max_bullet_count().get(p) - diff;
                    core.weaponsList().getUsps_max_bullet_count().replace(p, new_max_bullet_count);
                    core.weaponsList().getUsps_bullet_charger_count().replace(p, 9);
                    get(p);
                    return;
                }
                else if (core.weaponsList().getUsps_max_bullet_count().get(p) < diff)
                {
                    int new_max_bullet_count = core.weaponsList().getUsps_max_bullet_count().get(p) - diff;
                    int real_diff = 0;
                    for (int slot = new_max_bullet_count; slot == 0; slot++)
                    {
                        ++real_diff;
                    }
                    int new_bullet_charger_count = core.weaponsList().getUsps_bullet_charger_count().get(p) + real_diff;
                    core.weaponsList().getUsps_max_bullet_count().replace(p, 0);
                    core.weaponsList().getUsps_bullet_charger_count().replace(p, new_bullet_charger_count + real_diff);
                    get(p);
                    return;
                }
            }
            else if (core.weaponsList().getUsps_max_bullet_count().get(p) == 0)
            {
                for (Player pls : core.getServer().getOnlinePlayers())
                {
                    pls.playSound(p.getLocation(), Sound.CLICK, 0.8f, 1.5f);
                }
            }
        }
        else
        {
            for (Player pls : core.getServer().getOnlinePlayers())
            {
                pls.playSound(p.getLocation(), Sound.CLICK, 0.8f, 1.5f);
            }
        }
    }

    public void weightcheck(Player p)
    {
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 999999999, weightslow, false, false);

        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (p.getInventory().contains(weapon))
                {
                    if (p.getInventory().getItemInHand().getType() == weapon)
                    {
                        p.addPotionEffect(potionEffect);
                    }
                    else
                    {
                        for (PotionEffect effect : p.getActivePotionEffects())
                        {
                            p.removePotionEffect(effect.getType());
                        }
                    }
                }
                else
                {
                    cancel();
                }

                ++t;
                if (t == 1) {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 2L);
    }
}
