package fr.edminecoreteam.cspaintball;

public enum State
{
    WAITING("WAITING", 0),
    STARTING("STARTING", 1),
    INGAME("INGAME", 2),
    FINISH("FINISH", 3);

    private State(final String name, final int ordinal) {
    }
}
