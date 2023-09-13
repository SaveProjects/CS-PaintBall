package fr.edminecoreteam.cspaintball.game.rounds;

public enum RoundInfo
{
    PREPARATION("PREPARATION", 0),
    START("START", 1),
    BOMBPLANTED("BOMBPLANTED", 2),
    BOMBDIFUSE("BOMBDIFUSE", 3);

    private RoundInfo(final String name, final int ordinal) {
    }
}
