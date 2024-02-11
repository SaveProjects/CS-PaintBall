package fr.edminecoreteam.cspaintball.content.game.tasks;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GunOrderChecker
{
    private static Core core = Core.getInstance();

    public void check(Player p)
    {
        new BukkitRunnable() {
            int t = 1;
            public void run() {

                if (!core.isState(State.INGAME)) { return; }

                if (p.getInventory().getItem(0) == null && p.getInventory().getItem(1) != null && p.getInventory().getItem(2) == null)
                {
                    ItemStack itemToMove = p.getInventory().getItem(1);
                    p.getInventory().setItem(1, null);
                    p.getInventory().setItem(0, itemToMove);
                }

                if (p.getInventory().getItem(0) != null && p.getInventory().getItem(1) == null && p.getInventory().getItem(2) != null)
                {
                    ItemStack itemToMove = p.getInventory().getItem(2);
                    p.getInventory().setItem(2, null);
                    p.getInventory().setItem(1, itemToMove);
                }

                if (p.getInventory().getItem(0) == null && p.getInventory().getItem(1) != null && p.getInventory().getItem(2) != null)
                {
                    ItemStack itemToMove = p.getInventory().getItem(2);
                    ItemStack itemToMovee = p.getInventory().getItem(1);
                    p.getInventory().setItem(2, null);
                    p.getInventory().setItem(1, null);
                    p.getInventory().setItem(1, itemToMove);
                    p.getInventory().setItem(0, itemToMovee);
                }

                if (p.getInventory().getItem(0) == null && p.getInventory().getItem(1) == null && p.getInventory().getItem(2) != null)
                {
                    ItemStack itemToMove = p.getInventory().getItem(2);
                    p.getInventory().setItem(2, null);
                    p.getInventory().setItem(0, itemToMove);
                }


                ++t;
            }
        }.runTaskTimer((Plugin) core, 0L, 5L);
    }
}
