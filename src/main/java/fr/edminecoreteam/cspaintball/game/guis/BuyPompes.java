package fr.edminecoreteam.cspaintball.game.guis;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.game.weapons.Weapons;
import fr.edminecoreteam.cspaintball.game.weapons.WeaponsList;
import fr.edminecoreteam.cspaintball.utils.SkullNBT;
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

public class BuyPompes implements Listener
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
        if (e.getView().getTopInventory().getTitle().equals("§8Menu d'achat ┃ Fusils A Pompes"))
        {
            if (it.getType() == Material.ARROW && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§l⬇ §7Retour §8§l⬇"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                BuyMenu buyMenu = new BuyMenu();
                buyMenu.gui(p);
                return;
            }

            if (it.getType() == Material.STICK && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fNova"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                Weapons weapons = new Weapons(p);
                weapons.get(WeaponsList.NOVA);
                return;
            }

            if (it.getType() == Material.BLAZE_ROD && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fXM1014"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                Weapons weapons = new Weapons(p);
                weapons.get(WeaponsList.XM1014);
                return;
            }
        }
    }

    public void gui(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 54, "§8Menu d'achat ┃ Fusils A Pompes");
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (!p.getOpenInventory().getTitle().equalsIgnoreCase("§8Menu d'achat ┃ Fusils A Pompes")) { cancel(); }

                ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
                ItemMeta decoM = deco.getItemMeta();
                decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                decoM.setDisplayName("§r");
                deco.setItemMeta(decoM);
                inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
                inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);

                ItemStack nova = new ItemStack(Material.STICK, 1);
                ItemMeta novaM = nova.getItemMeta();
                novaM.setDisplayName("§fNova");
                ArrayList<String> lorenova = new ArrayList<String>();
                lorenova.add("");
                lorenova.add(" §dInformation:");
                lorenova.add(" §f▶ §7Prix: §a1050$");
                lorenova.add("");
                lorenova.add("§8➡ §fCliquez pour acheter.");
                novaM.setLore(lorenova);
                nova.setItemMeta(novaM);
                inv.setItem(21, nova);

                ItemStack xmx1014 = new ItemStack(Material.BLAZE_ROD, 1);
                ItemMeta xmx1014M = xmx1014.getItemMeta();
                xmx1014M.setDisplayName("§fXM1014");
                ArrayList<String> lorexmx1014 = new ArrayList<String>();
                lorexmx1014.add("");
                lorexmx1014.add(" §dInformation:");
                lorexmx1014.add(" §f▶ §7Prix: §a2000$");
                lorexmx1014.add("");
                lorexmx1014.add("§8➡ §fCliquez pour acheter.");
                xmx1014M.setLore(lorexmx1014);
                xmx1014.setItemMeta(xmx1014M);
                inv.setItem(22, xmx1014);

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
