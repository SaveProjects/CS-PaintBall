package fr.edminecoreteam.cspaintball.game.guis;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.utils.SkullNBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BuyMenu implements Listener
{
    private final ItemStack getSkull(String url) {
        return SkullNBT.getSkull(url);
    }
    private static Core core = Core.getInstance();

    public void gui(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 54, "§8Menu d'achat");
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (!p.getOpenInventory().getTitle().equalsIgnoreCase("§8Menu d'achat")) { cancel(); }

                ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
                ItemMeta decoM = deco.getItemMeta();
                decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                decoM.setDisplayName("§r");
                deco.setItemMeta(decoM);
                inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
                inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);

                ItemStack pistolets = new ItemStack(Material.IRON_HOE, 1);
                ItemMeta pistoletsM = pistolets.getItemMeta();
                pistoletsM.setDisplayName("§fPistolets");
                ArrayList<String> lorePistolets = new ArrayList<String>();
                lorePistolets.add("");
                lorePistolets.add(" §dInformation:");
                lorePistolets.add(" §f▶ §7Affichez les différents");
                lorePistolets.add(" §f▶ §7pistolets disponible.");
                lorePistolets.add("");
                lorePistolets.add("§8➡ §fCliquez pour y accéder.");
                pistoletsM.setLore(lorePistolets);
                pistolets.setItemMeta(pistoletsM);
                inv.setItem(21, pistolets);

                ItemStack lourdes = new ItemStack(Material.IRON_PICKAXE, 1);
                ItemMeta lourdesM = lourdes.getItemMeta();
                lourdesM.setDisplayName("§fLourdes");
                ArrayList<String> loreLourdes = new ArrayList<String>();
                loreLourdes.add("");
                loreLourdes.add(" §dInformation:");
                loreLourdes.add(" §f▶ §7Affichez les différentes");
                loreLourdes.add(" §f▶ §7armes lourdes disponible.");
                loreLourdes.add("");
                loreLourdes.add("§8➡ §fCliquez pour y accéder.");
                lourdesM.setLore(loreLourdes);
                lourdes.setItemMeta(lourdesM);
                inv.setItem(22, lourdes);

                ItemStack PMs = new ItemStack(Material.IRON_SPADE, 1);
                ItemMeta PMsM = PMs.getItemMeta();
                PMsM.setDisplayName("§fPMs");
                ArrayList<String> lorePMs = new ArrayList<String>();
                lorePMs.add("");
                lorePMs.add(" §dInformation:");
                lorePMs.add(" §f▶ §7Affichez les différentes");
                lorePMs.add(" §f▶ §7mitrailletes disponible.");
                lorePMs.add("");
                lorePMs.add("§8➡ §fCliquez pour y accéder.");
                PMsM.setLore(lorePMs);
                PMs.setItemMeta(PMsM);
                inv.setItem(23, PMs);

                ItemStack grenades = getSkull("http://textures.minecraft.net/texture/7e55c5abce7a79264a4892d68806eaafe370a0e4dd26f56191f7c188f01ed726");
                ItemMeta grenadesM = grenades.getItemMeta();
                grenadesM.setDisplayName("§fGrenades");
                ArrayList<String> loreGrenades = new ArrayList<String>();
                loreGrenades.add("");
                loreGrenades.add(" §dInformation:");
                loreGrenades.add(" §f▶ §7Affichez les différentes");
                loreGrenades.add(" §f▶ §7grenades disponible.");
                loreGrenades.add("");
                loreGrenades.add("§8➡ §fCliquez pour y accéder.");
                grenadesM.setLore(loreGrenades);
                grenades.setItemMeta(grenadesM);
                inv.setItem(30, grenades);

                ItemStack equipements = new ItemStack(Material.IRON_CHESTPLATE, 1);
                ItemMeta equipementsM = equipements.getItemMeta();
                equipementsM.setDisplayName("§fÉquipements");
                ArrayList<String> loreEquipements = new ArrayList<String>();
                loreEquipements.add("");
                loreEquipements.add(" §dInformation:");
                loreEquipements.add(" §f▶ §7Affichez les différents");
                loreEquipements.add(" §f▶ §7équipements disponible.");
                loreEquipements.add("");
                loreEquipements.add("§8➡ §fCliquez pour y accéder.");
                equipementsM.setLore(loreEquipements);
                equipements.setItemMeta(equipementsM);
                inv.setItem(31, equipements);

                ItemStack fusils = new ItemStack(Material.IRON_AXE, 1);
                ItemMeta fusilsM = fusils.getItemMeta();
                fusilsM.setDisplayName("§fFusils");
                ArrayList<String> loreFusils = new ArrayList<String>();
                loreFusils.add("");
                loreFusils.add(" §dInformation:");
                loreFusils.add(" §f▶ §7Affichez les différents");
                loreFusils.add(" §f▶ §7fusils disponible.");
                loreFusils.add("");
                loreFusils.add("§8➡ §fCliquez pour y accéder.");
                fusilsM.setLore(loreFusils);
                fusils.setItemMeta(fusilsM);
                inv.setItem(32, fusils);

                ++t;
                if (t == 10) {
                    run();
                }
            }
        }.runTaskTimer((Plugin) core, 0L, 10L);

        p.openInventory(inv);
    }
}
