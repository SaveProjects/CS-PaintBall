package fr.edminecoreteam.cspaintball.game.weapons;

import fr.edminecoreteam.cspaintball.game.weapons.pistolets.BERETTAS;
import fr.edminecoreteam.cspaintball.game.weapons.pistolets.USPS;
import org.bukkit.entity.Player;

public class Weapons
{
    private Player p;

    public Weapons(Player p)
    {
        this.p = p;
    }

    public void get(WeaponsList weapons)
    {
        if (weapons == WeaponsList.USPS)
        {
            USPS weapon = new USPS();
            weapon.get(p);
        }

        if (weapons == WeaponsList.BERETTAS)
        {
            BERETTAS weapon = new BERETTAS();
            weapon.get(p);
        }
    }

    public void refillMax()
    {
        USPS usps = new USPS();
        usps.refillMax(p);

        BERETTAS berettas = new BERETTAS();
        berettas.refillMax(p);
    }

}
