package fr.edminecoreteam.cspaintball.game.weapons;

import fr.edminecoreteam.cspaintball.game.weapons.pistolets.*;
import fr.edminecoreteam.cspaintball.game.weapons.pompes.NOVA;
import fr.edminecoreteam.cspaintball.game.weapons.pompes.XM1014;
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
        //Pistolets
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

        if (weapons == WeaponsList.DESERTEAGLE)
        {
            DESERTEAGLE weapon = new DESERTEAGLE();
            weapon.get(p);
        }

        if (weapons == WeaponsList.TEC9)
        {
            TEC9 weapon = new TEC9();
            weapon.get(p);
        }

        if (weapons == WeaponsList.P250)
        {
            P250 weapon = new P250();
            weapon.get(p);
        }

        //Fusils a pompe
        if (weapons == WeaponsList.NOVA)
        {
            NOVA nova = new NOVA();
            nova.get(p);
        }

        if (weapons == WeaponsList.XM1014)
        {
            XM1014 xm1014 = new XM1014();
            xm1014.get(p);
        }
    }

    public void refillMax()
    {
        //Pistolets
        USPS usps = new USPS();
        usps.refillMax(p);

        BERETTAS berettas = new BERETTAS();
        berettas.refillMax(p);

        DESERTEAGLE deserteagle = new DESERTEAGLE();
        deserteagle.refillMax(p);

        TEC9 tec9 = new TEC9();
        tec9.refillMax(p);

        P250 p250 = new P250();
        p250.refillMax(p);

        //Fusils a pompe
        NOVA nova = new NOVA();
        nova.refillMax(p);

        XM1014 xm1014 = new XM1014();
        xm1014.refillMax(p);
    }

    public void deathCheck()
    {
        //Pistolets
        USPS usps = new USPS();
        usps.deathCheck(p);

        BERETTAS berettas = new BERETTAS();
        berettas.deathCheck(p);

        DESERTEAGLE deserteagle = new DESERTEAGLE();
        deserteagle.deathCheck(p);

        TEC9 tec9 = new TEC9();
        tec9.deathCheck(p);

        P250 p250 = new P250();
        p250.deathCheck(p);

        //Fusils a pompe
        NOVA nova = new NOVA();
        nova.deathCheck(p);

        XM1014 xm1014 = new XM1014();
        xm1014.deathCheck(p);
    }

}
