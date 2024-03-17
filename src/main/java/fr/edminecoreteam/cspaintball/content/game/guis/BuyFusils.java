package fr.edminecoreteam.cspaintball.content.game.guis;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.content.game.weapons.Weapons;
import fr.edminecoreteam.cspaintball.content.game.weapons.WeaponsList;
import fr.edminecoreteam.cspaintball.utils.minecraft.skull.SkullNBT;
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

public class BuyFusils implements Listener
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
        if (e.getView().getTopInventory().getTitle().equals("§8Menu d'achat ┃ Fusils"))
        {
            if (it.getType() == Material.ARROW && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§l⬇ §7Retour §8§l⬇"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                BuyMenu buyMenu = new BuyMenu();
                buyMenu.gui(p);
                return;
            }

            if (it.getType() == Material.WOOD_AXE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fGALIL-AR"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                Weapons weapons = new Weapons(p);
                weapons.get(WeaponsList.GALILAR);
                return;
            }

            if (it.getType() == Material.WOOD_AXE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fFAMAS"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                Weapons weapons = new Weapons(p);
                weapons.get(WeaponsList.FAMAS);
                return;
            }
        }
    }

    public void gui(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 54, "§8Menu d'achat ┃ Fusils");
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (!p.getOpenInventory().getTitle().equalsIgnoreCase("§8Menu d'achat ┃ Fusils")) { cancel(); }

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

                if (core.teams().getDefenser().contains(p))
                {
                    ItemStack famas = new ItemStack(Material.WOOD_AXE, 1);
                    ItemMeta famasM = famas.getItemMeta();
                    famasM.setDisplayName("§fFAMAS");
                    ArrayList<String> lorefamas = new ArrayList<String>();
                    lorefamas.add("");
                    lorefamas.add(" §dInformation:");
                    lorefamas.add(" §f▶ §7Prix: §a2050$");
                    lorefamas.add(" §f▶ §7Difficulté: §6✯✯✯");
                    lorefamas.add("");
                    lorefamas.add(" §aDescription:");
                    lorefamas.add(" §f▶ §7Peu cher et efficace contre les unités");
                    lorefamas.add("   §7ennemies équipées d'une protection");
                    lorefamas.add("");
                    lorefamas.add(" §f▶ §7A privilégier pour les manches en achat forcé");
                    lorefamas.add("   §7(«force buy» en anglais).");
                    lorefamas.add("");
                    lorefamas.add("§8➡ §fCliquez pour acheter.");
                    famasM.setLore(lorefamas);
                    famas.setItemMeta(famasM);
                    inv.setItem(21, famas);
                }
                if (core.teams().getAttacker().contains(p))
                {
                    ItemStack galilar = new ItemStack(Material.WOOD_AXE, 1);
                    ItemMeta galilarM = galilar.getItemMeta();
                    galilarM.setDisplayName("§fGALIL-AR");
                    ArrayList<String> loregalilar = new ArrayList<String>();
                    loregalilar.add("");
                    loregalilar.add(" §dInformation:");
                    loregalilar.add(" §f▶ §7Prix: §a1800$");
                    loregalilar.add(" §f▶ §7Difficulté: §6✯✯✯");
                    loregalilar.add("");
                    loregalilar.add(" §aDescription:");
                    loregalilar.add(" §f▶ §7Peu cher et efficace contre les unités");
                    loregalilar.add("   §7ennemies équipées d'une protection");
                    loregalilar.add("");
                    loregalilar.add(" §f▶ §7A privilégier pour les manches en achat forcé");
                    loregalilar.add("   §7(«force buy» en anglais).");
                    loregalilar.add("");
                    loregalilar.add("§8➡ §fCliquez pour acheter.");
                    galilarM.setLore(loregalilar);
                    galilar.setItemMeta(galilarM);
                    inv.setItem(21, galilar);
                }

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
