package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GoalKeeper extends Player {

    /**
     *  Fields
     */
    private double elasticity;

    /**
     * Default constructor
     */
    public GoalKeeper() {
        this.elasticity = 0;
    }

    /**
     * Parametrized constructor
     * @param name Name
     * @param num Number
     * @param veloc Velocity
     * @param resist Resistance
     * @param dext Dexterity
     * @param imp Impulsion
     * @param hg Head game
     * @param gk Goal kick
     * @param pass Pass
     * @param clubs Club history
     * @param side Side
     * @param elast Elasticity
     */
    public GoalKeeper(String name, int num, double veloc, double resist, double dext, double imp, double hg, double gk, double pass, List<String> clubs, int side, double elast) {
        super(name, num, veloc, resist, dext, imp, hg, gk, pass, clubs, side);
        this.elasticity = elast;
    }

    /**
     * Copy constructor
     * @param gk GoalKeeper
     */
    public GoalKeeper(GoalKeeper gk) {
        super(gk);
        this.elasticity = gk.getElasticity();
    }

    /**
     * Clones a GoalKeeper
     * @return GoalKeeper
     */
    public GoalKeeper clone() {
        return new GoalKeeper(this);
    }

    /**
     * Gets elasticity of GoalKeeper
     * @return Elasticity
     */
    public double getElasticity() {
        return this.elasticity;
    }

    /**
     * Sets elasticity of GoalKeeper
     * @param elasticity Elasticity
     */
    public void setElasticity(double elasticity) {
        this.elasticity = elasticity;
    }

    /**
     * Converts the GoalKeeper information to a string
     * @return String
     */
    public String toString() {
        StringBuilder s = new StringBuilder();

        if (this.getNumber() < 10)
            s.append("  ").append(this.getNumber()).append("     ");
        else
            s.append("  ").append(this.getNumber()).append("    ");

        s.append(Player.getStringClass(this.getClassKey()))
         .append(" ")
         .append(Player.getStringSide(this.getSide()))
         .append("    ")
         .append("    ").append(String.format("%.1f", this.abilityCalculus(GK)))
         .append("    ").append(String.format("%.1f", this.abilityCalculus(DP)))
         .append("    ----")
         .append("    ----")
         .append("    ----");

        return s.toString();
    }

    /**
     * Calculates the ability of a GoalKeeper
     * @param pos Position where the player plays
     * @return Ability
     */
    public double abilityCalculus(int pos) {
        double vel = this.getVelocity();
        double res = this.getResistance();
        double dex = this.getDexterity();
        double imp = this.getImpulsion();
        double hg = this.getHeadGame();
        double gk = this.getGoalKick();
        double pass = this.getPass();
        double specialSkill = 0;
        if (pos == GK) specialSkill = this.getElasticity();

        return vel * 0.05 + res * 0.05 + dex * 0.275 + imp * 0.275 + hg * 0.05 + gk * 0.05 + pass * 0.05 + specialSkill * 0.2;
    }

    /**
     * Checks if the position is allowed for the GoalKeeper
     * @param pos Position
     * @return True if it's allowed, False if it isn't
     */
    public boolean getAllowedPos(int pos) {
        return pos == GK || pos == DP;
    }

    /**
     * Parses a Player from a string
     * @param input String
     * @return Player
     */
    public static Player parse(String input) {
        String[] campos = input.split(",");
        return new GoalKeeper(campos[0],
                Integer.parseInt(campos[1]),
                Double.parseDouble(campos[2]),
                Double.parseDouble(campos[3]),
                Double.parseDouble(campos[4]),
                Double.parseDouble(campos[5]),
                Double.parseDouble(campos[6]),
                Double.parseDouble(campos[7]),
                Double.parseDouble(campos[8]),
                new ArrayList<>(), // List<String>clubs
                -1,
                Double.parseDouble(campos[9]));
    }

    /**
     * Gets the a integer that corresponds to the class of the player
     * @return Integer associated
     */
    public int getClassKey() {
        return GK;
    }


    /**
     * Generates a random player
     * @param teamNumbers Number already on the team
     */
    public void newRandomPlayer(Collection<Integer> teamNumbers) {
        int i;
        do
            i = (int) (Math.random() * 100);
        while (teamNumbers.contains(i));

        this.setName("Tom");
        this.setNumber(i);
        this.setVelocity(Math.random() * 100);
        this.setResistance(Math.random() * 100);
        this.setDexterity(Math.random() * 100);
        this.setImpulsion(Math.random() * 100);
        this.setHeadGame(Math.random() * 100);
        this.setGoalKick(Math.random() * 100);
        this.setPass(Math.random() * 100);
        this.setClubs(new ArrayList<>());
        this.setSide(Team.randomSide(this));
        this.setElasticity(Math.random() * 100);

    }
}
