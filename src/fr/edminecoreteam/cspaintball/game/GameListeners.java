package fr.edminecoreteam.cspaintball.game;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.game.guis.BuyMenu;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameListeners implements Listener
{
    private static Core core = Core.getInstance();



    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        if (core.isRoundState(RoundInfo.PREPARATION))
        {
            Player p = e.getPlayer();
            if (!core.teams().getAttacker().contains(p) && !core.teams().getDefenser().contains(p))
            {
                return;
            }

            Location attackerSpawn = new Location(Bukkit.getWorld(p.getWorld().getName()),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.x"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.y"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.z"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.f"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".attacker.t"));

            Location defenserSpawn = new Location(Bukkit.getWorld(p.getWorld().getName()),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.x"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.y"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.z"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.f"),
                    (float) core.getConfig().getDouble("maps." + core.world + ".defenser.t"));

            if (core.teams().getAttacker().contains(p))
            {
                p.teleport(attackerSpawn);
            }
            if (core.teams().getDefenser().contains(p))
            {
                p.teleport(defenserSpawn);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (core.isState(State.INGAME) || core.isState(State.FINISH))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void openInventory(InventoryOpenEvent e)
    {
        if (e.getInventory().getType() != InventoryType.CHEST)
        {
            e.setCancelled(true);
            BuyMenu buy = new BuyMenu();
            Player p = (Player) e.getPlayer();
            buy.gui(p);
        }
    }

    @EventHandler
    public void hungerBarChange(FoodLevelChangeEvent e)
    {
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }

        if (core.isState(State.INGAME)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent e) {
        Entity ent = e.getRightClicked();
        Player p = e.getPlayer();
        if (ent instanceof ArmorStand)
        {
            e.setCancelled(true);
            return;
        }
    }

}
