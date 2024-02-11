package fr.edminecoreteam.cspaintball.content.waiting.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsSpec
{
    private Player p;

    public ItemsSpec(Player p)
    {
        this.p = p;
    }

    public void get() {
        ItemStack map = new ItemStack(Material.COMPASS, 1);
        ItemMeta mapM = map.getItemMeta();
        mapM.setDisplayName("§a§lJoueurs §7• Clique");
        map.setItemMeta(mapM);
        p.getInventory().setItem(0, map);

        ItemStack leave = new ItemStack(Material.BED, 1);
        ItemMeta leaveM = leave.getItemMeta();
        leaveM.setDisplayName("§c§lQuitter §7• Clique");
        leave.setItemMeta(leaveM);
        p.getInventory().setItem(8, leave);
    }
}
