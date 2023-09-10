package fr.edminecoreteam.cspaintball.game.guis;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.game.weapons.Weapons;
import fr.edminecoreteam.cspaintball.game.weapons.WeaponsList;
import fr.edminecoreteam.cspaintball.game.weapons.pistolets.USPS;
import fr.edminecoreteam.cspaintball.utils.SkullNBT;
import org.bukkit.Bukkit;
import org.bukkit.Color;
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
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BuyPistolets implements Listener
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
        if (e.getView().getTopInventory().getTitle().equals("§8Menu d'achat ┃ Pistolets"))
        {
            if (it.getType() == Material.ARROW && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§l⬇ §7Retour §8§l⬇"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                BuyMenu buyMenu = new BuyMenu();
                buyMenu.gui(p);
                return;
            }

            if (it.getType() == Material.WOOD_HOE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fUSP-S"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                Weapons weapons = new Weapons(p);
                weapons.get(WeaponsList.USPS);
                return;
            }

            if (it.getType() == Material.STONE_HOE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fBerettas"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                Weapons weapons = new Weapons(p);
                weapons.get(WeaponsList.BERETTAS);
                return;
            }
        }
    }

    public void gui(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 54, "§8Menu d'achat ┃ Pistolets");
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (!p.getOpenInventory().getTitle().equalsIgnoreCase("§8Menu d'achat ┃ Pistolets")) { cancel(); }

                ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
                ItemMeta decoM = deco.getItemMeta();
                decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                decoM.setDisplayName("§r");
                deco.setItemMeta(decoM);
                inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
                inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);

                ItemStack usps = new ItemStack(Material.WOOD_HOE, 1);
                ItemMeta uspsM = usps.getItemMeta();
                uspsM.setDisplayName("§fUSP-S");
                ArrayList<String> loreusps = new ArrayList<String>();
                loreusps.add("");
                loreusps.add(" §dInformation:");
                loreusps.add(" §f▶ §7Prix: §a200$");
                loreusps.add("");
                loreusps.add("§8➡ §fCliquez pour acheter.");
                uspsM.setLore(loreusps);
                usps.setItemMeta(uspsM);
                inv.setItem(21, usps);

                ItemStack berettas = new ItemStack(Material.STONE_HOE, 1);
                ItemMeta berettasM = berettas.getItemMeta();
                berettasM.setDisplayName("§fBerettas");
                ArrayList<String> loreberettas = new ArrayList<String>();
                loreberettas.add("");
                loreberettas.add(" §dInformation:");
                loreberettas.add(" §f▶ §7Prix: §a500$");
                loreberettas.add("");
                loreberettas.add("§8➡ §fCliquez pour acheter.");
                berettasM.setLore(loreberettas);
                berettas.setItemMeta(berettasM);
                inv.setItem(22, berettas);

                ItemStack p250 = new ItemStack(Material.IRON_HOE, 1);
                ItemMeta p250M = p250.getItemMeta();
                p250M.setDisplayName("§fP-250");
                ArrayList<String> lorep250 = new ArrayList<String>();
                lorep250.add("");
                lorep250.add(" §dInformation:");
                lorep250.add(" §f▶ §7Prix: §a300$");
                lorep250.add("");
                lorep250.add("§8➡ §fCliquez pour acheter.");
                p250M.setLore(lorep250);
                p250.setItemMeta(p250M);
                inv.setItem(23, p250);

                ItemStack tec9 = new ItemStack(Material.GOLD_HOE, 1);
                ItemMeta tec9M = tec9.getItemMeta();
                tec9M.setDisplayName("§fTec-9");
                ArrayList<String> loretec9 = new ArrayList<String>();
                loretec9.add("");
                loretec9.add(" §dInformation:");
                loretec9.add(" §f▶ §7Prix: §a500$");
                loretec9.add("");
                loretec9.add("§8➡ §fCliquez pour acheter.");
                tec9M.setLore(loretec9);
                tec9.setItemMeta(tec9M);
                inv.setItem(30, tec9);

                ItemStack deserteagle = new ItemStack(Material.DIAMOND_HOE, 1);
                ItemMeta deserteagleM = deserteagle.getItemMeta();
                deserteagleM.setDisplayName("§fDesert-Eagle");
                ArrayList<String> loredeserteagle = new ArrayList<String>();
                loredeserteagle.add("");
                loredeserteagle.add(" §dInformation:");
                loredeserteagle.add(" §f▶ §7Prix: §a700$");
                loredeserteagle.add("");
                loredeserteagle.add("§8➡ §fCliquez pour acheter.");
                deserteagleM.setLore(loredeserteagle);
                deserteagle.setItemMeta(deserteagleM);
                inv.setItem(31, deserteagle);

                ItemStack back = new ItemStack(Material.ARROW, 1);
                ItemMeta backM = back.getItemMeta();
                backM.setDisplayName("§8§l⬇ §7Retour §8§l⬇");
                back.setItemMeta(backM);
                inv.setItem(49, back);

                ++t;
                if (t == 10) {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 10L);

        p.openInventory(inv);
    }
}
