package fr.edminecoreteam.cspaintball.game.spec;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.game.utils.GameUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class DefenserSpec implements Listener
{
    private static final Core core = Core.getInstance();

    public final HashMap<Player, Player> getView = new HashMap<Player, Player>();

    public void setSpec(Player p)
    {
        p.getInventory().clear();
        p.getEquipment().setHelmet(null);
        p.getEquipment().setChestplate(null);
        p.getEquipment().setLeggings(null);
        p.getEquipment().setBoots(null);

        p.setGameMode(GameMode.SPECTATOR);
        for (Player teams : core.teams().getDefenser())
        {
            if (teams != p)
            {
                if (!core.teams().getDefenserDeath().contains(teams))
                {
                    if (!core.defenserSpec().getView.containsKey(p))
                    {
                        core.defenserSpec().getView.put(p, teams);
                    }
                }
            }
        }
        p.setFlying(true);
        refreshInv(p);

        if (!p.getWorld().getName().equalsIgnoreCase("game"))
        {
            GameUtils utils = new GameUtils();
            p.teleport(utils.getDefenserSpawn());
        }

        new BukkitRunnable() {
            int t = 0;
            int f = 0;
            public void run() {
                ++t;
                ++f;
                if (!core.isState(State.INGAME)) { cancel(); }
                if (core.isRoundState(RoundInfo.PREPARATION)) { cancel(); }
                if (core.teams().getDefenserDeath().contains(core.defenserSpec().getView.get(p))) { getNewViewver(p); }
                if (core.teams().getDefenser().size() != core.teams().getDefenserDeath().size())
                {
                    Location to = core.defenserSpec().getView.get(p).getLocation();
                    p.teleport(to.clone().add(0, 0.5, 0));
                    core.title.sendActionBar(p, "§fVous regardez §9" + core.defenserSpec().getView.get(p).getName() + " §8┃ §bOuvrez votre §b§linv§b. pour naviger.");
                }
                else
                {
                    cancel();
                }

                if (f == 40)
                {
                    refreshInv(p);
                    f = 0;
                }

                if (t == 1)
                {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 1L);
    }

    public void getNewViewver(Player p)
    {
        core.defenserSpec().getView.remove(p);
        for (Player teams : core.teams().getDefenser())
        {
            if (teams != p)
            {
                if (!core.teams().getDefenserDeath().contains(teams))
                {
                    if (!core.defenserSpec().getView.containsKey(p))
                    {
                        core.defenserSpec().getView.put(p, teams);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();
        if (core.teams().getDefenserDeath().contains(p))
        {
            if (e.getCurrentItem() != null)
            {
                ItemStack it = e.getCurrentItem();
                if (it.getType() == Material.BARRIER && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lFermer §7• Clique"))
                {
                    p.closeInventory();
                }
                if (it.getType() == Material.SKULL_ITEM)
                {
                    String pName = it.getItemMeta().getDisplayName();
                    Player pTarget = core.getServer().getPlayer(pName);
                    core.defenserSpec().getView.remove(p);
                    core.defenserSpec().getView.put(p, pTarget);
                    p.closeInventory();
                }
            }
        }
    }

    public void refreshInv(Player p)
    {
        p.getInventory().clear();
        ItemStack leave = new ItemStack(Material.BARRIER, 1);
        ItemMeta leaveM = leave.getItemMeta();
        leaveM.setDisplayName("§c§lFermer §7• Clique");
        leave.setItemMeta(leaveM);

        int slot = 0;
        for (Player pls : core.teams().getDefenser())
        {
            if (!core.teams().getDefenserDeath().contains(pls))
            {
                ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                SkullMeta itemStackM = (SkullMeta) itemStack.getItemMeta();
                itemStackM.setOwner(pls.getName());
                itemStackM.setDisplayName(pls.getName());
                itemStack.setItemMeta(itemStackM);
                p.getInventory().setItem(slot, itemStack);
                slot++;
            }
        }
        p.getInventory().setItem(8, leave);
    }
}
