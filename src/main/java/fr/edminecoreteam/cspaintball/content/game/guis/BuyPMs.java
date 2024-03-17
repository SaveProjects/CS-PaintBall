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

public class BuyPMs implements Listener
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
        if (e.getView().getTopInventory().getTitle().equals("§8Menu d'achat ┃ PMs"))
        {
            if (it.getType() == Material.ARROW && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8§l⬇ §7Retour §8§l⬇"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                BuyMenu buyMenu = new BuyMenu();
                buyMenu.gui(p);
                return;
            }

            if (it.getType() == Material.WOOD_SPADE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fMAC-10"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                Weapons weapons = new Weapons(p);
                weapons.get(WeaponsList.MAC10);
                return;
            }

            if (it.getType() == Material.STONE_SPADE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fMP9"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                Weapons weapons = new Weapons(p);
                weapons.get(WeaponsList.MP9);
                return;
            }

            if (it.getType() == Material.IRON_SPADE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fMP7"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                Weapons weapons = new Weapons(p);
                weapons.get(WeaponsList.MP7);
                return;
            }

            if (it.getType() == Material.GOLD_SPADE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fP90"))
            {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
                Weapons weapons = new Weapons(p);
                weapons.get(WeaponsList.P90);
                return;
            }
        }
    }

    public void gui(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 54, "§8Menu d'achat ┃ PMs");
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (!p.getOpenInventory().getTitle().equalsIgnoreCase("§8Menu d'achat ┃ PMs")) { cancel(); }

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

                ItemStack mac10 = new ItemStack(Material.WOOD_SPADE, 1);
                ItemMeta mac10M = mac10.getItemMeta();
                mac10M.setDisplayName("§fMAC-10");
                ArrayList<String> loremac10 = new ArrayList<String>();
                loremac10.add("");
                loremac10.add(" §dInformation:");
                loremac10.add(" §f▶ §7Prix: §a1050$");
                loremac10.add(" §f▶ §7Difficulté: §a✯");
                loremac10.add("");
                loremac10.add(" §aDescription:");
                loremac10.add(" §f▶ §7Conçue pour tirer et courir en même temps, cette arme");
                loremac10.add("   §7permet de se frayer un chemin sur les sites de bombe.");
                loremac10.add("");
                loremac10.add(" §f▶ §7A privilégier lors des trois premières manches de la mi-temps.");
                loremac10.add("");
                loremac10.add("§8➡ §fCliquez pour acheter.");
                mac10M.setLore(loremac10);
                mac10.setItemMeta(mac10M);
                inv.setItem(21, mac10);

                ItemStack mp9 = new ItemStack(Material.STONE_SPADE, 1);
                ItemMeta mp9M = mp9.getItemMeta();
                mp9M.setDisplayName("§fMP9");
                ArrayList<String> loremp9 = new ArrayList<String>();
                loremp9.add("");
                loremp9.add(" §dInformation:");
                loremp9.add(" §f▶ §7Prix: §a1250$");
                loremp9.add(" §f▶ §7Difficulté: §a✯");
                loremp9.add("");
                loremp9.add(" §aDescription:");
                loremp9.add(" §f▶ §7Une explosion de dégâts pour prévenir");
                loremp9.add("   §7toute avancée ennemie.");
                loremp9.add("");
                loremp9.add(" §f▶ §7A privilégier lors des trois premières manches de la mi-temps.");
                loremp9.add("");
                loremp9.add("§8➡ §fCliquez pour acheter.");
                mp9M.setLore(loremp9);
                mp9.setItemMeta(mp9M);
                inv.setItem(22, mp9);

                ItemStack mp7 = new ItemStack(Material.IRON_SPADE, 1);
                ItemMeta mp7M = mp7.getItemMeta();
                mp7M.setDisplayName("§fMP7");
                ArrayList<String> loremp7 = new ArrayList<String>();
                loremp7.add("");
                loremp7.add(" §dInformation:");
                loremp7.add(" §f▶ §7Prix: §a1700$");
                loremp7.add(" §f▶ §7Difficulté: §e✯✯");
                loremp7.add("");
                loremp7.add(" §aDescription:");
                loremp7.add(" §f▶ §7Arme facile à contrôler et qui inflige de");
                loremp7.add("   §7bons dégâts à portée intermédiaire.");
                loremp7.add("");
                loremp7.add(" §f▶ §7A privilégier pour les manches en achat forcé");
                loremp7.add("   §7(«force buy» en anglais).");
                loremp7.add("");
                loremp7.add("§8➡ §fCliquez pour acheter.");
                mp7M.setLore(loremp7);
                mp7.setItemMeta(mp7M);
                inv.setItem(23, mp7);

                ItemStack p90 = new ItemStack(Material.GOLD_SPADE, 1);
                ItemMeta p90M = p90.getItemMeta();
                p90M.setDisplayName("§fP90");
                ArrayList<String> lorep90 = new ArrayList<String>();
                lorep90.add("");
                lorep90.add(" §dInformation:");
                lorep90.add(" §f▶ §7Prix: §a2350$");
                lorep90.add(" §f▶ §7Difficulté: §a✯");
                lorep90.add("");
                lorep90.add(" §aDescription:");
                lorep90.add(" §f▶ §7Un jet de balles permanent surpassé");
                lorep90.add("   §7uniquement par les fusils.");
                lorep90.add("");
                lorep90.add(" §f▶ §7A privilégier pour les manches en achat complet");
                lorep90.add("   §7(«full buy» en anglais).");
                lorep90.add("");
                lorep90.add("§8➡ §fCliquez pour acheter.");
                p90M.setLore(lorep90);
                p90.setItemMeta(p90M);
                inv.setItem(30, p90);

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
