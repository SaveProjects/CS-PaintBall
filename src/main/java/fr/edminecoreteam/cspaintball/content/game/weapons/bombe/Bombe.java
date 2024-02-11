package fr.edminecoreteam.cspaintball.content.game.weapons.bombe;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.content.game.Game;
import fr.edminecoreteam.cspaintball.content.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.content.game.tasks.BombPlanted;
import fr.edminecoreteam.cspaintball.utils.minecraft.skull.SkullNBT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bombe implements Listener
{
    private final static Core core = Core.getInstance();
    private ItemStack getSkull(String url) {
        return SkullNBT.getSkull(url);
    }

    private final String bomb_name = "§fBombe §c§lC4";
    private final ItemStack bomb = getSkull("http://textures.minecraft.net/texture/4cee694b5df34f40d5e774bd3046db849e34ff55a482d0731e9d7a7bb74a12");

    private final Location locSiteA = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".a.x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".a.y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".a.y"));

    private final Location locSiteB = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".b.x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".b.y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".b.y"));

    public void getRandom()
    {
        List<Player> attackers = new ArrayList<Player>(core.teams().getAttacker());

        Random random = new Random();

        int randomIndex = random.nextInt(attackers.size());
        Player randomPlayer = attackers.get(randomIndex);
        get(randomPlayer);
    }

    public void get(Player p)
    {
        ItemStack bombItem = bomb;
        ItemMeta bombItemM = bombItem.getItemMeta();
        bombItemM.setDisplayName(bomb_name);
        bombItem.setItemMeta(bombItemM);
        p.getInventory().setItem(8, bombItem);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e)
    {
        Player p = (Player) e.getPlayer();

        if (core.teams().getAttacker().contains(p))
        {
            for (ArmorStand bombGround : Bukkit.getWorld(p.getWorld().getName()).getEntitiesByClass(ArmorStand.class)) {
                if (bombGround.getWorld() == p.getWorld()) {
                    Location loc = bombGround.getLocation();
                    double distanceSquared = p.getLocation().distanceSquared(loc);
                    if (distanceSquared <= 2 * 2)
                    {
                        if (core.isRoundState(RoundInfo.START) || core.isRoundState(RoundInfo.PREPARATION))
                        {
                            if (!bombGround.getItemInHand().getItemMeta().getDisplayName().contains(bomb_name)) { return; }
                            double distanceSquaredd = p.getLocation().distanceSquared(loc);
                            p.sendMessage("§7Vous avez récupéré: " + bomb_name);
                            get(p);
                            bombGround.remove();
                            return;
                        }
                    }
                    if (distanceSquared <= 6 * 6) {
                        if (bombGround.getCustomName().equalsIgnoreCase("§8⬇ §fSite §c§lA §8⬇") || bombGround.getCustomName().equalsIgnoreCase("§8⬇ §fSite §c§lB §8⬇") || bombGround.getCustomName().equalsIgnoreCase("bomb.planted"))
                        {

                            if (p.getInventory().getItemInHand().getType() != Material.AIR && p.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(bomb_name))
                            {
                                int plant_time = core.getConfig().getInt("timers.planting-bomb");
                                new BukkitRunnable() {
                                    int t = plant_time;
                                    public void run() {

                                        if (p.isSneaking())
                                        {
                                            if (p.getInventory().getItemInHand() == null || !p.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(bomb_name)) { cancel(); }

                                            if (bombGround.getCustomName().equalsIgnoreCase("§8⬇ §fSite §c§lA §8⬇") || bombGround.getCustomName().equalsIgnoreCase("§8⬇ §fSite §c§lB §8⬇") || bombGround.getCustomName().equalsIgnoreCase("bomb.planted"))
                                            {
                                                double distanceSquaredd = p.getLocation().distanceSquared(loc);
                                                if (distanceSquaredd <= 6 * 6)
                                                {
                                                    if (!core.isRoundState(RoundInfo.END))
                                                    {
                                                        sendProgressBar(p, "Plantation de la bombe... ", t, plant_time);
                                                        if (t == 0)
                                                        {
                                                            String name = "bomb.planted";
                                                            double newx = p.getLocation().getX();
                                                            double newy = p.getLocation().getY() -1.2;
                                                            double newz = p.getLocation().getZ();
                                                            Location customloc = new Location(Bukkit.getWorld("game"), newx, newy, newz);
                                                            ArmorStand armorStand = (ArmorStand)Bukkit.getWorld("game").spawnEntity(customloc, EntityType.ARMOR_STAND);
                                                            armorStand.setVisible(false);
                                                            armorStand.setSmall(false);
                                                            armorStand.setCanPickupItems(false);
                                                            armorStand.setArms(true);
                                                            armorStand.setCustomName(name);
                                                            armorStand.setCustomNameVisible(false);
                                                            armorStand.setGravity(false);
                                                            armorStand.setBasePlate(false);
                                                            armorStand.setRightArmPose(new EulerAngle(Math.toRadians(180.0), Math.toRadians(0.0), Math.toRadians(90.0)));
                                                            armorStand.setItemInHand(bomb);
                                                            p.getInventory().setItem(8, null);
                                                            core.setRoundState(RoundInfo.BOMBPLANTED);
                                                            BombPlanted bombPlanted = new BombPlanted(core, customloc);
                                                            bombPlanted.runTaskTimer((Plugin) core, 0L, 20L);
                                                            for (Player pls : core.teams().getAttacker())
                                                            {
                                                                pls.playSound(pls.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                                                                pls.sendTitle("", "§dGardez le CAP !");
                                                            }
                                                            for (Player pls : core.teams().getDefenser())
                                                            {
                                                                pls.playSound(pls.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.0f);
                                                                pls.sendTitle("", "§cBombe C4 plantée !");
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        cancel();
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                cancel();
                                            }
                                        }
                                        else
                                        {
                                            cancel();
                                        }

                                        --t;
                                    }
                                }.runTaskTimer((Plugin) core, 0L, 20L);
                            }
                        }
                    }
                }
            }
        }
        if (core.teams().getDefenser().contains(p)) {
            List<String> armorStandList = new ArrayList<String>();

            for (ArmorStand bombGround : Bukkit.getWorld(p.getWorld().getName()).getEntitiesByClass(ArmorStand.class)) {
                if (bombGround.getWorld() == p.getWorld()) {
                    Location loc = bombGround.getLocation();
                    double distanceSquared = p.getLocation().distanceSquared(loc);
                    if (distanceSquared <= 2 * 2)
                    {
                        armorStandList.add(bombGround.getCustomName());
                    }
                }
            }

            if (armorStandList.contains("bomb.planted")) {
                if (core.isRoundState(RoundInfo.BOMBPLANTED)) {
                    int diffuse_time = core.getConfig().getInt("timers.diffuse");
                    new BukkitRunnable() {
                        int t = diffuse_time;

                        public void run() {

                            if (p.isSneaking()) {
                                if (armorStandList.contains("bomb.planted")) {
                                    if (core.isRoundState(RoundInfo.BOMBPLANTED)) {

                                        for (ArmorStand bombGround : Bukkit.getWorld(p.getWorld().getName()).getEntitiesByClass(ArmorStand.class)) {
                                            if (bombGround.getCustomName().equalsIgnoreCase("bomb.planted"))
                                            {
                                                Location loc = bombGround.getLocation();
                                                double distanceSquared = p.getLocation().distanceSquared(loc);
                                                if (distanceSquared > 2 * 2)
                                                {
                                                    cancel();
                                                }
                                            }
                                        }

                                        sendProgressBar(p, "Désamorçage de la bombe... ", t, diffuse_time);
                                        if (t == 0) {
                                            core.pointsManager().addDefenserPoints();
                                            core.setRoundState(RoundInfo.BOMBDIFUSE);
                                            Game game = new Game();
                                            game.endRound();
                                            for (Player pls : core.teams().getAttacker())
                                            {
                                                pls.playSound(pls.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                                                pls.sendTitle("§cOn n'aime pas ça ! §c✖", "§7Vous perdez la manche.");
                                            }
                                            for (Player pls : core.teams().getDefenser())
                                            {
                                                pls.playSound(pls.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
                                                pls.sendTitle("§aBeau travail ! §a✔", "§7Vous remportez la manche.");
                                            }
                                            cancel();
                                        }
                                    } else {
                                        cancel();
                                    }
                                } else {
                                    cancel();
                                }
                            } else {
                                cancel();
                            }

                            --t;
                        }
                    }.runTaskTimer((Plugin) core, 0L, 20L);
                }
            }
        }
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        // Récupérez la position où l'item a été largué

        if (event.getEntity().getItemStack().getType() != Material.SKULL_ITEM) { return; }
        if (event.getEntity().getItemStack().getItemMeta() == null) { return; }
        if (event.getEntity().getItemStack().getItemMeta().getDisplayName() == null || event.getEntity().getItemStack().getItemMeta().getDisplayName() != bomb_name) { return; }
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                ++t;
                if (event.getEntity() != null)
                {
                    if (event.getEntity().isOnGround())
                    {
                        String name = event.getEntity().getItemStack().getItemMeta().getDisplayName();
                        double newx = event.getEntity().getLocation().getX();
                        double newy = event.getEntity().getLocation().getY() -1.2;
                        double newz = event.getEntity().getLocation().getZ();
                        Location customloc = new Location(Bukkit.getWorld(event.getEntity().getWorld().getName()), newx, newy, newz);
                        ArmorStand armorStand = (ArmorStand)Bukkit.getWorld(event.getEntity().getWorld().getName()).spawnEntity(customloc, EntityType.ARMOR_STAND);
                        armorStand.setVisible(false);
                        armorStand.setSmall(false);
                        armorStand.setCanPickupItems(false);
                        armorStand.setArms(true);
                        armorStand.setCustomName(name);
                        armorStand.setCustomNameVisible(true);
                        armorStand.setGravity(false);
                        armorStand.setBasePlate(false);
                        armorStand.setRightArmPose(new EulerAngle(Math.toRadians(180.0), Math.toRadians(0.0), Math.toRadians(90.0)));
                        armorStand.setItemInHand(event.getEntity().getItemStack());
                        event.getEntity().remove();
                        System.out.println("Bombe DROP à la position : X" + newx + ", Y" + newy + ", Z" + newz);
                        cancel();
                    }

                }
                else
                {
                    cancel();
                }

                if (t == 1) {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 1L);
    }

    public boolean isPlayerNearLocation(Player player, Location location, double distance) {
        // Vérifiez si le joueur est à la distance spécifiée de la localisation
        return player.getLocation().distance(location) <= distance;
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
