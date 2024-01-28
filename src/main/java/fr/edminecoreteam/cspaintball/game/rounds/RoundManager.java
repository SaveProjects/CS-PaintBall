package fr.edminecoreteam.cspaintball.game.rounds;

public class RoundManager
{
    private int round;

    public RoundManager()
    {
        this.round = 1;
    }

    public int getRound() { return this.round; }
    public void addRound() { this.round = this.round + 1; }
}
