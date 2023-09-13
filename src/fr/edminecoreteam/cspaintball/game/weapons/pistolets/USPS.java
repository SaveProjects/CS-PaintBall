package fr.edminecoreteam.cspaintball.game.weapons.pistolets;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.game.weapons.WeaponsSounds;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class USPS implements Listener
{

    private static final Core core = Core.getInstance();

    private final double recoil = 0.1; //recul de tir
    private final double speed_shoot = 3; //vitesse de tir (max 5)
    private final int bullet_charger = 9; //nombre de balles par chargeur
    private final int max_bullet = 27; //total de munitions
    private final Material weapon = Material.WOOD_HOE; //materiel de l'ame
    private final String weapon_name = "USP-s"; //titre de l'arme
    private final String weapon_id = "usps"; //id de l'arme
    private final int weapon_damage = 3; //dégats de l'arme (en coeurs)
    private final int wait_for_shoot_delay = 7; //temps d'armement (ticks)
    private final int weightslow = 0; //niveau de vitesse (quand l'arme est porté)
    private final int time_refill = 2; //temps de recharge (secondes)
    private final String shoot_sound = "silent"; //Bruit de tir
    private final String refill_sound = "2s"; //Bruit de recharge
    private final String armed_sound = "classic"; //Bruit d'armement




    public void get(Player p)
    {
        if (!p.getInventory().contains(weapon))
        {
            if (!core.weaponsMap().getMap().containsKey(p) || !core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_max_bullet_count") && !core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_bullet_charger_count"))
            {

                if (!core.weaponsMap().getMap().containsKey(p))
                {
                    HashMap<String, Integer> max_bullet_count = new HashMap<>();
                    max_bullet_count.put(weapon_id + "_max_bullet_count", max_bullet);


                    core.weaponsMap().getMap().put(p, max_bullet_count);
                    core.weaponsMap().getMap().get(p).put(weapon_id + "_bullet_charger_count", bullet_charger);
                }
                else
                {
                    core.weaponsMap().getMap().get(p).put(weapon_id + "_max_bullet_count", max_bullet);
                    core.weaponsMap().getMap().get(p).put(weapon_id + "_bullet_charger_count", bullet_charger);
                }

                ItemStack gunStarter = new ItemStack(weapon, core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count"));
                ItemMeta gunStarterM = gunStarter.getItemMeta();
                gunStarterM.setDisplayName("§f" + weapon_name + " §a" + core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") + "§8/§a" + core.weaponsMap().getHashMap(p).get(weapon_id + "_max_bullet_count"));
                gunStarter.setItemMeta((ItemMeta)gunStarterM);
                p.getInventory().addItem(gunStarter);
                weightcheck(p);
                return;
            }
            if (core.weaponsMap().getHashMap(p).get(weapon_id + "_bullet_charger_count") == 0)
            {
                ItemStack gunStarter = new ItemStack(weapon, core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") + 1);
                ItemMeta gunStarterM = gunStarter.getItemMeta();
                gunStarterM.setDisplayName("§f" + weapon_name + " §a" + core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") + "§8/§a" + core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count"));
                gunStarter.setItemMeta((ItemMeta)gunStarterM);
                p.getInventory().addItem(gunStarter);
                weightcheck(p);
                return;
            }
            else
            {
                ItemStack gunStarter = new ItemStack(weapon, core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count"));
                ItemMeta gunStarterM = gunStarter.getItemMeta();
                gunStarterM.setDisplayName("§f" + weapon_name + " §a" + core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") + "§8/§a" + core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count"));
                gunStarter.setItemMeta((ItemMeta)gunStarterM);
                p.getInventory().addItem(gunStarter);
                weightcheck(p);
                return;
            }

        }
        else
        {
            if (core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count") == 0 && core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") == 0)
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
                        core.weaponsMap().getMap().get(p).remove(weapon_id + "_max_bullet_count");
                        core.weaponsMap().getMap().get(p).remove(weapon_id + "_bullet_charger_count");
                        return;
                    }
                }
            }
            else
            {
                ItemStack[] inventory = p.getInventory().getContents();
                for (int slot = 0; slot < inventory.length; slot++) {
                    ItemStack item = inventory[slot];

                    if (item != null && item.getType() == weapon) {
                        ItemStack gunStarter = new ItemStack(weapon, core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count"));
                        ItemMeta gunStarterM = gunStarter.getItemMeta();
                        gunStarterM.setDisplayName("§f" + weapon_name + " §a" + core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") + "§8/§a" + core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count"));
                        gunStarter.setItemMeta((ItemMeta)gunStarterM);
                        p.getInventory().setItem(slot, gunStarter);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            if (event.getDamager() instanceof org.bukkit.entity.Snowball)
            {
                if (((Snowball) event.getDamager()).getShooter() instanceof Player)
                {

                    Player victim = (Player) event.getEntity();
                    Snowball bullet = (Snowball) event.getDamager();

                    // Obtenez la vélocité de la boule de neige pour déterminer la direction
                    Vector direction = bullet.getVelocity().normalize();

                    // Obtenez la direction de la tête du joueur par rapport à la boule de neige
                    Vector relativeDirection = victim.getEyeLocation().toVector().subtract(bullet.getLocation().toVector()).normalize();

                    // Calculez le produit scalaire entre les deux directions
                    double dotProduct = direction.dot(relativeDirection);

                    // Vous pouvez ajuster ces valeurs selon vos besoins pour déterminer la zone d'impact
                    if (dotProduct > 0.99) {
                        int damage = weapon_damage * 4;
                        event.setDamage(damage);
                    } else if (dotProduct < 0.99 && dotProduct > 0.50) {
                        int damage = weapon_damage * 2;
                        event.setDamage(damage);
                    } else if (dotProduct < 0.50) {
                        int damage = weapon_damage;
                        event.setDamage(damage);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e)
    {
        Player p = e.getPlayer();
        int newLocation = e.getNewSlot();
        WeaponsSounds sounds = new WeaponsSounds(p);
        ItemStack itemInHand = p.getInventory().getItem(newLocation);
        if (itemInHand != null && itemInHand.getType() == weapon) {
            if (core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_max_bullet_count") && core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_bullet_charger_count"))
            {
                if (core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") == 0)
                {
                    refill(p);
                    return;
                }
                weightcheck(p);
                sounds.armed(armed_sound);
            }
            else
            {
                weightcheck(p);
                sounds.shoot("nobullet");
            }
        }
    }

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
            WeaponsSounds sound = new WeaponsSounds(p);
            if (core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_max_bullet_count") && core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_bullet_charger_count"))
            {
                e.setCancelled(true);
                ItemStack hand = p.getInventory().getItemInHand();

                if (!core.weaponsMap().getWeapon_refill().containsKey(p) || !core.weaponsMap().getWeapon_refill().get(p).equalsIgnoreCase(weapon_id))
                {
                    if (!core.weaponsMap().getWeapon_wait_for_shoot().containsKey(p) || !core.weaponsMap().getWeapon_wait_for_shoot().get(p).equalsIgnoreCase(weapon_id))
                    {
                        if (core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") > 1)
                        {
                            int get_bullet_charger_count = core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") - 1;
                            core.weaponsMap().getMap().get(p).replace(weapon_id + "_bullet_charger_count", get_bullet_charger_count);
                            get(p);

                            Snowball snowball = p.launchProjectile(Snowball.class);

                            double speed = speed_shoot;
                            snowball.setVelocity(p.getLocation().getDirection().multiply(speed));
                            Vector pushDirection = p.getLocation().getDirection().multiply(-recoil);
                            p.setVelocity(pushDirection);
                            sound.shoot(shoot_sound);
                            core.weaponsMap().getWeapon_wait_for_shoot().put(p, weapon_id);
                            new BukkitRunnable() {
                                int t = 0;
                                int f = 0;
                                public void run() {

                                    ++t;
                                    ++f;
                                    if (f == wait_for_shoot_delay) {
                                        core.weaponsMap().getWeapon_wait_for_shoot().remove(p);
                                        cancel();
                                    }

                                    if (t == 1) {
                                        run();
                                    }
                                }
                            }.runTaskTimer((Plugin) core, 0L, 1L);
                            return;
                        }
                        if (core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") == 1)
                        {
                            int get_bullet_charger_count = core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") - 1;
                            core.weaponsMap().getMap().get(p).replace(weapon_id + "_bullet_charger_count", get_bullet_charger_count);
                            refill(p);

                            Snowball snowball = p.launchProjectile(Snowball.class);

                            double speed = speed_shoot;
                            snowball.setVelocity(p.getLocation().getDirection().multiply(speed));
                            Vector pushDirection = p.getLocation().getDirection().multiply(-recoil);
                            p.setVelocity(pushDirection);
                            sound.shoot(shoot_sound);
                        }
                        if (core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") <= 0 || core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count") <= 0 || !core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_max_bullet_count"))
                        {
                            get(p);
                            sound.shoot("nobullet");
                        }
                    }
                }
                else
                {
                    sound.shoot("nobullet");
                }
            }
            else
            {
                sound.shoot("nobullet");
            }

        }
        if (it.getType() == weapon && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().contains(weapon_name)
                && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK))
        {
            e.setCancelled(true);
            if (!core.weaponsMap().getWeapon_refill().containsKey(p))
            {
                refill(p);
                return;
            }
            get(p);
        }
    }

    public void refill(Player p)
    {

        if (core.weaponsMap().getWeapon_refill().containsKey(p))
        {
            return;
        }

        if (!core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_max_bullet_count") && !core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_bullet_charger_count"))
        {
            return;
        }

        if (core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count") == 0)
        {
            return;
        }

        if (core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") == bullet_charger)
        {
            return;
        }

        core.weaponsMap().getWeapon_refill().put(p, weapon_id);
        WeaponsSounds sound = new WeaponsSounds(p);
        int finaltime_refill = 0;

        for (int i = 0 ; i <= time_refill ; i++)
        {
            finaltime_refill = finaltime_refill + 20;
        }
        int finalTime_refill = finaltime_refill / 2;
        sound.refill(refill_sound);
        new BukkitRunnable() {

            int t = 0;
            int f = 0;
            int m = finalTime_refill;

            public void run() {
                ++t;
                ++f;
                if (p.getItemInHand().getType() != weapon) { core.weaponsMap().getWeapon_refill().remove(p); core.title.sendActionBar(p,""); cancel(); }
                sendProgressBar(p, "Recharge en cours...", f, m);
                get(p);

                if (f == m) {
                    int diff = bullet_charger - core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count");
                    if (core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count") > 0) {
                        if (core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count") >= diff) {
                            int new_max_bullet_count = core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count") - diff;
                            core.weaponsMap().getMap().get(p).replace(weapon_id + "_bullet_charger_count", core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") + diff);
                            core.weaponsMap().getMap().get(p).replace(weapon_id + "_max_bullet_count", new_max_bullet_count);
                            get(p);
                            core.weaponsMap().getWeapon_refill().remove(p);
                            core.title.sendActionBar(p,"");
                            cancel();
                        } else if (core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count") < diff) {
                            int new_max_bullet_count = core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count") - diff;
                            int realdiff = 0;
                            if (new_max_bullet_count < 0)
                            {
                                for (int i = new_max_bullet_count; i < 0; ++i)
                                {
                                    ++realdiff;
                                }
                            }

                            core.weaponsMap().getMap().get(p).replace(weapon_id + "_bullet_charger_count", core.weaponsMap().getMap().get(p).get(weapon_id + "_bullet_charger_count") + diff - realdiff);
                            core.weaponsMap().getMap().get(p).replace(weapon_id + "_max_bullet_count", 0);
                            core.weaponsMap().getWeapon_refill().remove(p);
                            core.title.sendActionBar(p,"");
                            get(p);
                            cancel();
                        }
                    } else if (core.weaponsMap().getMap().get(p).get(weapon_id + "_max_bullet_count") == 0) {
                        sound.shoot("nobullet");
                    }
                }
                if (t == 1) {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 1L);
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
                        if (!p.getActivePotionEffects().contains(potionEffect))
                        {
                            p.addPotionEffect(potionEffect);
                        }
                    }
                    else if (p.getInventory().getItemInHand().getType() != weapon)
                    {
                        for (PotionEffect effect : p.getActivePotionEffects())
                        {
                            p.removePotionEffect(effect.getType());
                        }
                        cancel();
                    }
                }
                else
                {
                    for (PotionEffect effect : p.getActivePotionEffects())
                    {
                        p.removePotionEffect(effect.getType());
                    }
                    if (core.weaponsMap().getMap().containsKey(p))
                    {
                        if (core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_max_bullet_count"))
                        {
                            core.weaponsMap().getMap().get(p).remove(weapon_id + "_max_bullet_count");
                        }
                        if (core.weaponsMap().getMap().get(p).containsKey(weapon_id + "_bullet_charger_count"))
                        {
                            core.weaponsMap().getMap().get(p).remove(weapon_id + "_bullet_charger_count");
                        }
                        if (core.weaponsMap().getWeapon_refill().containsKey(p) && core.weaponsMap().getWeapon_refill().get(p).equalsIgnoreCase(weapon_id))
                        {
                            core.weaponsMap().getWeapon_refill().remove(p, weapon_id);
                        }
                        if (core.weaponsMap().getWeapon_wait_for_shoot().containsKey(p) && core.weaponsMap().getWeapon_wait_for_shoot().get(p).equalsIgnoreCase(weapon_id))
                        {
                            core.weaponsMap().getWeapon_wait_for_shoot().remove(p, weapon_id);
                        }
                    }
                    cancel();
                }

                ++t;
                if (t == 1) {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 2L);
    }

    public void sendProgressBar(Player player, String message, int current, int max) {

        float percentage = (float) current / max;
        int progressBars = Math.round(percentage * 10);
        String progressBarString = "§a";
        for (int i = 0; i < 10; i++) {
            if (i < progressBars) {
                progressBarString += "⬛";
            } else {
                progressBarString += "§7⬛";
            }
        }
        String actionBarMessage = progressBarString + " §6" + message;
        core.title.sendActionBar(player, actionBarMessage);

    }
}
