package fr.edminecoreteam.cspaintball.waiting.items;

import fr.edminecoreteam.cspaintball.utils.SkullNBT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsWaiting
{
    private Player p;

    private final ItemStack getSkull(String url) {
        return SkullNBT.getSkull(url);
    }

    public ItemsWaiting(Player p)
    {
        this.p = p;
    }

    public void get()
    {
        ItemStack team = new ItemStack(Material.BANNER, 1, (short)15);
        ItemMeta teamM = team.getItemMeta();
        teamM.setDisplayName("§f§lChoix de votre équipe §7• Clique");
        team.setItemMeta(teamM);
        p.getInventory().setItem(0, team);

        ItemStack skins = getSkull("http://textures.minecraft.net/texture/3121a59f86a723da2ab9ec55ec5d30e67f5c0e8a6843972d15d41bd646847ad5");
        ItemMeta skinsM = skins.getItemMeta();
        skinsM.setDisplayName("§b§lChoix de votre skin §7• Clique");
        skins.setItemMeta(skinsM);
        p.getInventory().setItem(1, skins);

        ItemStack infos = getSkull("http://textures.minecraft.net/texture/718079d5847416abf44e8c2fec2ccd44f08d736ca8e51f95a436d85f643fbc");
        ItemMeta infosM = infos.getItemMeta();
        infosM.setDisplayName("§e§lInformations du jeu §7• Clique");
        infos.setItemMeta(infosM);
        p.getInventory().setItem(6, infos);

        ItemStack leave = new ItemStack(Material.BED, 1);
        ItemMeta leaveM = leave.getItemMeta();
        leaveM.setDisplayName("§c§lQuitter §7• Clique");
        leave.setItemMeta(leaveM);
        p.getInventory().setItem(8, leave);
    }

    public void changeTeam(String teams) {
        if (teams.equalsIgnoreCase("random")) {
            ItemStack team = new ItemStack(Material.BANNER, 1, (short) 15);
            ItemMeta teamM = team.getItemMeta();
            teamM.setDisplayName("§f§lChoix de votre équipe §7• Clique");
            team.setItemMeta(teamM);
            p.getInventory().setItem(0, team);
        }
        if (teams.equalsIgnoreCase("attacker")) {
            ItemStack team = new ItemStack(Material.BANNER, 1, (short) 1);
            ItemMeta teamM = team.getItemMeta();
            teamM.setDisplayName("§c§lChoix de votre équipe §7• Clique");
            team.setItemMeta(teamM);
            p.getInventory().setItem(0, team);
        }
        if (teams.equalsIgnoreCase("defenser")) {
            ItemStack team = new ItemStack(Material.BANNER, 1, (short) 4);
            ItemMeta teamM = team.getItemMeta();
            teamM.setDisplayName("§9§lChoix de votre équipe §7• Clique");
            team.setItemMeta(teamM);
            p.getInventory().setItem(0, team);
        }
    }

    public void clearinv()
    {
        p.getInventory().clear();
    }
}
