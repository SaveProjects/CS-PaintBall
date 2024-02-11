package fr.edminecoreteam.cspaintball.content.game.weapons;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.content.game.rounds.RoundInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class WeaponsSettings implements Listener
{
    private static Core core = Core.getInstance();
    private int knife_damage = 5; //dégats du couteau (en coeurs)
    private Material knife = Material.IRON_SWORD; //materiel du couteau


    @EventHandler
    public void onDropKnife(PlayerDropItemEvent event)
    {
        if (event.getItemDrop().getItemStack().getType() == knife)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            if (event.getDamager() instanceof Player)
            {
                if (core.isRoundState(RoundInfo.PREPARATION)) { event.setCancelled(true); return; }
                Player damager = (Player) event.getDamager();
                if (damager.getItemInHand() == null || damager.getItemInHand().getType() != knife)
                {
                    event.setCancelled(true);
                }
                if (damager.getItemInHand() != null && damager.getItemInHand().getType() == knife)
                {
                    int finaldamage = knife_damage * 2;
                    event.setDamage(finaldamage);
                }
            }
        }
    }
}
