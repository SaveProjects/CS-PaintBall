package fr.edminecoreteam.cspaintball.content.game.rounds;

public enum RoundInfo
{
    PREPARATION("PREPARATION", 0),
    START("START", 1),
    BOMBPLANTED("BOMBPLANTED", 2),
    BOMBEXPLODE("BOMBEXPLODE", 3),
    BOMBDIFUSE("BOMBDIFUSE", 4),
    END("END", 5);

    private RoundInfo(final String name, final int ordinal) {
    }
}
