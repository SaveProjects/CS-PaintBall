package fr.edminecoreteam.cspaintball.game.points;

public class PointsManager
{
    private int attackerPoints;
    private int defenserPoints;

    public PointsManager()
    {
        this.attackerPoints = 0;
        this.defenserPoints = 0;
    }

    public int getAttackerPoints() { return this.attackerPoints; }
    public void addAttackerPoints() { this.attackerPoints = this.attackerPoints + 1; }
    public void setAttackerPoints(int i) { this.attackerPoints = i; }

    public int getDefenserPoints() { return this.defenserPoints; }
    public void addDefenserPoints() { this.defenserPoints = this.defenserPoints + 1; }
    public void setDefenserPoints(int i) { this.defenserPoints = i; }
}
