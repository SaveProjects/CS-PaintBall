package fr.edminecoreteam.cspaintball.game.weapons;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeaponsList
{
    private final List<Player> usps_refill;
    private final List<Player> usps_wait_for_shoot;
    private final HashMap<Player, Integer> usps_max_bullet_count;
    private final HashMap<Player, Integer> usps_bullet_charger_count;

    public WeaponsList()
    {
        this.usps_refill = new ArrayList<Player>();
        this.usps_wait_for_shoot = new ArrayList<Player>();
        this.usps_max_bullet_count = new HashMap<Player, Integer>();
        this.usps_bullet_charger_count = new HashMap<Player, Integer>();
    }

    public List<Player> getUsps_refill() { return this.usps_refill; }
    public List<Player> getUsps_wait_for_shoot() { return this.usps_wait_for_shoot; }
    public HashMap<Player, Integer> getUsps_max_bullet_count() { return this.usps_max_bullet_count; }
    public HashMap<Player, Integer> getUsps_bullet_charger_count() { return this.usps_bullet_charger_count; }
}
