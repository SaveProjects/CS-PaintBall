package fr.edminecoreteam.cspaintball.content.game.weapons;

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
    MAG7("MAG7", 8),

    //PMs
    MAC10("MAC10", 9),
    MP9("MP9", 10),
    MP7("MP7", 11),
    P90("P90", 12),

    //Lourdes
    M249("M249", 13);

    private WeaponsList(final String name, final int ordinal) {
    }
}
