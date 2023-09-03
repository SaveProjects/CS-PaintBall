package fr.edminecoreteam.cspaintball.waiting;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.waiting.guis.ChooseTeam;
import fr.edminecoreteam.cspaintball.waiting.items.ItemsWaiting;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.Action;

public class WaitingListeners implements Listener
{
    private Player p;
    private static Core core = Core.getInstance();

    public WaitingListeners(Player p)
    {
        this.p = p;
    }

    public WaitingListeners()
    {
        //null
    }

    public void getWaitingItems()
    {
        ItemsWaiting waiting = new ItemsWaiting(p);
        waiting.clearinv();
        waiting.get();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) { return; }

        Player p = (Player)e.getWhoClicked();
        ItemStack it = e.getCurrentItem();
        if (it.getType() == Material.BANNER && core.isState(State.WAITING)) {
            e.setCancelled(true);
            ChooseTeam teamGui = new ChooseTeam();
            teamGui.gui(p);
            p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) { return; }

        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack it = e.getItem();
        if (it.getType() == Material.BANNER && core.isState(State.WAITING) && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
            e.setCancelled(true);
            ChooseTeam teamGui = new ChooseTeam();
            teamGui.gui(p);
            p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
        }
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent e) {
        if (core.isState(State.WAITING)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onHungerBarChange(FoodLevelChangeEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }
        if (core.isState(State.WAITING)) {
            e.setCancelled(true);
        }
    }
}
