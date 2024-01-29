package fr.edminecoreteam.cspaintball.game.weapons;

public enum WeaponsList
{
    //Pistolets
    USPS("USPS", 0),
    GLOCK18("GLOCK18", 1),
    BERETTAS("BERETTAS", 2),
    TEC9("TEC9", 3),
    P250("P250", 4),
    DESERTEAGLE("DESERTEAGLE", 5),

    //Fusils a pompe
    NOVA("NOVA", 6),
    XM1014("XM1014", 7),

    //PMs
    MAC10("MAC10", 8);

    private WeaponsList(final String name, final int ordinal) {
    }
}
