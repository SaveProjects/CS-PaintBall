package fr.edminecoreteam.cspaintball.waiting.guis;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.utils.SkullNBT;
import fr.edminecoreteam.cspaintball.waiting.items.ItemsWaiting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ChooseTeam implements Listener
{
    private final ItemStack getSkull(String url) {
        return SkullNBT.getSkull(url);
    }
    private static Core core = Core.getInstance();

    @EventHandler
    public void chooseTeamInteract(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) { return; }

        Player p = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();
        if (e.getView().getTopInventory().getTitle().equals("§8Choix de votre équipe"))
        {
            if (it.getType() == Material.SKULL_ITEM && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fÉquipe Aléatoire"))
            {
                e.setCancelled(true);
                core.teams().leaveTeam(p);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                ItemsWaiting itemsWaiting = new ItemsWaiting(p);
                itemsWaiting.changeTeam("random");
                return;
            }
            if (it.getType() == Material.BANNER && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cÉquipe Attaquants"))
            {
                e.setCancelled(true);
                if(!core.teams().getAttacker().contains(p))
                {
                    if (core.teams().getAttacker().size() < core.getConfig().getInt("teams.attacker.players"))
                    {
                        core.teams().leaveTeam(p);
                        core.teams().joinTeam(p, "attacker");
                        ItemsWaiting itemsWaiting = new ItemsWaiting(p);
                        itemsWaiting.changeTeam("attacker");
                        return;
                    }
                }
                else
                {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    return;
                }
            }

            if (it.getType() == Material.BANNER && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Équipe Défenseurs"))
            {
                e.setCancelled(true);
                if(!core.teams().getDefenser().contains(p))
                {
                    if (core.teams().getDefenser().size() < core.getConfig().getInt("teams.defenser.players"))
                    {
                        core.teams().leaveTeam(p);
                        core.teams().joinTeam(p, "defenser");
                        ItemsWaiting itemsWaiting = new ItemsWaiting(p);
                        itemsWaiting.changeTeam("defenser");
                        return;
                    }
                }
                else
                {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    return;
                }
            }
        }
    }

    public void gui(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 54, "§8Choix de votre équipe");
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (!p.getOpenInventory().getTitle().equalsIgnoreCase("§8Choix de votre équipe")) { cancel(); }

                    if (!core.teams().getAttacker().contains(p) && !core.teams().getDefenser().contains(p))
                    {
                        ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0);
                        ItemMeta decoM = deco.getItemMeta();
                        decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                        decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                        decoM.setDisplayName("§r");
                        deco.setItemMeta(decoM);
                        inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
                        inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);
                    }

                if (core.teams().getAttacker().contains(p))
                {
                    ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
                    ItemMeta decoM = deco.getItemMeta();
                    decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                    decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                    decoM.setDisplayName("§r");
                    deco.setItemMeta(decoM);
                    inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
                    inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);
                }

                if (core.teams().getDefenser().contains(p))
                {
                    ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
                    ItemMeta decoM = deco.getItemMeta();
                    decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                    decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                    decoM.setDisplayName("§r");
                    deco.setItemMeta(decoM);
                    inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
                    inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);
                }

                ItemStack randomTeam = getSkull("http://textures.minecraft.net/texture/7881cc2747ba72cbcb06c3cc331742cd9de271a5bbffd0ecb14f1c6a8b69bc9e");
                ItemMeta randomTeamM = randomTeam.getItemMeta();
                randomTeamM.setDisplayName("§fÉquipe Aléatoire");
                ArrayList<String> loreRandom = new ArrayList<String>();
                loreRandom.add("");
                loreRandom.add(" §dInformation:");
                loreRandom.add(" §f▶ §7Activez ou non le mode");
                loreRandom.add(" §f▶ §7§lÉquipe Aléatoire§7.");
                loreRandom.add("");
                loreRandom.add("§8➡ §fCliquez pour y accéder.");
                randomTeamM.setLore(loreRandom);
                randomTeam.setItemMeta(randomTeamM);
                inv.setItem(4, randomTeam);




                ItemStack attacker = new ItemStack(Material.BANNER, 1, (byte)1);
                ItemMeta attackerM = attacker.getItemMeta();
                attackerM.setDisplayName("§cÉquipe Attaquants");
                if (core.teams().getAttacker().contains(p)) {
                    attackerM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                    attackerM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                }
                ArrayList<String> loreAttacker = new ArrayList<String>();
                loreAttacker.add("");
                loreAttacker.add(" §dInformation:");
                loreAttacker.add(" §f▶ §7Joueur(s): §a" + core.teams().getAttacker().size() + "§f/" + "§a" + core.getConfig().getInt("teams.attacker.players"));
                loreAttacker.add("");
                loreAttacker.add(" §bEmplacement(s):");
                if (core.teams().getAttacker().size() == 0) {
                    loreAttacker.add(" §f▶ §8Vide...");
                }
                for (Player pls : core.teams().getAttacker()) {
                    loreAttacker.add(" §f▶ §8" + pls.getName());
                }
                if (!core.teams().getAttacker().contains(p))
                {
                    loreAttacker.add("");
                    loreAttacker.add("§8➡ §fCliquez pour rejoindre.");
                }
                else
                {
                    loreAttacker.add("");
                    loreAttacker.add("§8➡ §aÉquipe sélectionnée.");
                }
                attackerM.setLore(loreAttacker);
                attacker.setItemMeta(attackerM);
                inv.setItem(21, attacker);




                ItemStack defenser = new ItemStack(Material.BANNER, 1, (byte)4);
                ItemMeta defenserM = defenser.getItemMeta();
                defenserM.setDisplayName("§9Équipe Défenseurs");
                if (core.teams().getDefenser().contains(p)) {
                    defenserM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                    defenserM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                }
                ArrayList<String> loreDefenser = new ArrayList<String>();
                loreDefenser.add("");
                loreDefenser.add(" §dInformation:");
                loreDefenser.add(" §f▶ §7Joueur(s): §a" + core.teams().getDefenser().size() + "§f/" + "§a" + core.getConfig().getInt("teams.defenser.players"));
                loreDefenser.add("");
                loreDefenser.add(" §bEmplacement(s):");
                if (core.teams().getDefenser().size() == 0) {
                    loreDefenser.add(" §f▶ §8Vide...");
                }
                for (Player pls : core.teams().getDefenser()) {
                    loreDefenser.add(" §f▶ §8" + pls.getName());
                }
                if (!core.teams().getDefenser().contains(p))
                {
                    loreDefenser.add("");
                    loreDefenser.add("§8➡ §fCliquez pour rejoindre.");
                }
                else
                {
                    loreDefenser.add("");
                    loreDefenser.add("§8➡ §aÉquipe sélectionnée.");
                }
                defenserM.setLore(loreDefenser);
                defenser.setItemMeta(defenserM);
                inv.setItem(23, defenser);

                ++t;
                if (t == 10) {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 10L);

        p.openInventory(inv);
    }

}
