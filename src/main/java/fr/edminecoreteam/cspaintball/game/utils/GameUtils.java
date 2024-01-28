package fr.edminecoreteam.cspaintball.game.utils;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class GameUtils
{
    private final static Core core = Core.getInstance();

    public ItemStack haveBomb(Player player, String customName)
    {
        Inventory playerInventory = player.getInventory();
        for (ItemStack item : playerInventory.getContents())
        {
            if (item != null && item.getType() == Material.SKULL_ITEM)
            {
                ItemMeta meta = item.getItemMeta();
                if (meta instanceof SkullMeta)
                {
                    SkullMeta skullMeta = (SkullMeta) meta;
                    if (skullMeta.hasDisplayName() && skullMeta.getDisplayName().equals(customName))
                    {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    public void showAllPlayers(Player player)
    {
        for (Player pls : core.getServer().getOnlinePlayers())
        {
            player.showPlayer(pls);
        }
    }
}
