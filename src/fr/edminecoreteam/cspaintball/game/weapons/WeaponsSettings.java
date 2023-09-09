package fr.edminecoreteam.cspaintball.game.weapons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class WeaponsSettings implements Listener
{
    private int knife_damage = 5; //dégats du couteau (en coeurs)
    private Material knife = Material.IRON_SWORD; //materiel du couteau

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            if (event.getDamager() instanceof Player)
            {
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
