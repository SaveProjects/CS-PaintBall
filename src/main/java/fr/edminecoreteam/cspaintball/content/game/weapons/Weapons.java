package fr.edminecoreteam.cspaintball.content.game.weapons;

import fr.edminecoreteam.cspaintball.content.game.weapons.lourdes.M249;
import fr.edminecoreteam.cspaintball.content.game.weapons.lourdes.NEGEV;
import fr.edminecoreteam.cspaintball.content.game.weapons.pistolets.*;
import fr.edminecoreteam.cspaintball.content.game.weapons.pm.MP7;
import fr.edminecoreteam.cspaintball.content.game.weapons.pm.MP9;
import fr.edminecoreteam.cspaintball.content.game.weapons.pm.P90;
import fr.edminecoreteam.cspaintball.content.game.weapons.pompes.MAG7;
import fr.edminecoreteam.cspaintball.content.game.weapons.pompes.XM1014;
import fr.edminecoreteam.cspaintball.content.game.weapons.pm.MAC10;
import fr.edminecoreteam.cspaintball.content.game.weapons.pompes.NOVA;
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

        if (weapons == WeaponsList.GLOCK18)
        {
            GLOCK18 weapon = new GLOCK18();
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
            NOVA weapon = new NOVA();
            weapon.get(p);
        }

        if (weapons == WeaponsList.XM1014)
        {
            XM1014 weapon = new XM1014();
            weapon.get(p);
        }

        if (weapons == WeaponsList.MAG7)
        {
            MAG7 weapon = new MAG7();
            weapon.get(p);
        }

        //PMs
        if (weapons == WeaponsList.MAC10)
        {
            MAC10 weapon = new MAC10();
            weapon.get(p);
        }

        if (weapons == WeaponsList.MP9)
        {
            MP9 weapon = new MP9();
            weapon.get(p);
        }

        if (weapons == WeaponsList.MP7)
        {
            MP7 weapon = new MP7();
            weapon.get(p);
        }

        if (weapons == WeaponsList.P90)
        {
            P90 weapon = new P90();
            weapon.get(p);
        }

        //Lourdes
        if (weapons == WeaponsList.M249)
        {
            M249 weapon = new M249();
            weapon.get(p);
        }
        
        if (weapons == WeaponsList.NEGEV)
        {
            NEGEV weapon = new NEGEV();
            weapon.get(p);
        }
    }

    public void refillMax()
    {
        //Pistolets
        USPS usps = new USPS();
        usps.refillMax(p);

        GLOCK18 glock18 = new GLOCK18();
        glock18.refillMax(p);

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

        MAG7 mag7 = new MAG7();
        mag7.refillMax(p);

        //PMs
        MAC10 mac10 = new MAC10();
        mac10.refillMax(p);

        MP9 mp9 = new MP9();
        mp9.refillMax(p);

        MP7 mp7 = new MP7();
        mp7.refillMax(p);

        P90 p90 = new P90();
        p90.refillMax(p);

        //Lourdes
        M249 m249 = new M249();
        m249.refillMax(p);
        
        NEGEV negev = new NEGEV();
        negev.refillMax(p);
    }

    public void deathCheck()
    {
        //Pistolets
        USPS usps = new USPS();
        usps.deathCheck(p);

        GLOCK18 glock18 = new GLOCK18();
        glock18.deathCheck(p);

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

        MAG7 mag7 = new MAG7();
        mag7.deathCheck(p);

        //PMs
        MAC10 mac10 = new MAC10();
        mac10.deathCheck(p);

        MP9 mp9 = new MP9();
        mp9.deathCheck(p);

        MP7 mp7 = new MP7();
        mp7.deathCheck(p);

        P90 p90 = new P90();
        p90.deathCheck(p);

        //Lourdes
        M249 m249 = new M249();
        m249.deathCheck(p);
        
        NEGEV negev = new NEGEV();
        negev.deathCheck(p);
    }

}
