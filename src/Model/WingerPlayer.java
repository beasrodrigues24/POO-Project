package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WingerPlayer extends Player{

    /**
     * Fields
     */
    private double crossing_ability;

    /**
     * Default constructor
     */
    public WingerPlayer() {
        super();
        this.crossing_ability = 0;
    }

    /**
     * Parameterized constructor
     * @param name Name
     * @param num Number
     * @param veloc Velocity
     * @param resist Resistance
     * @param dext Dexterity
     * @param imp Impulsion
     * @param hg Head game
     * @param gk Goal kick
     * @param pass Pass
     * @param clubs Clubs
     * @param side Side
     * @param crossing_ability Crossing ability
     */
    public WingerPlayer(String name, int num, double veloc, double resist, double dext, double imp, double hg, double gk, double pass, List<String> clubs, int side, double crossing_ability) {
        super(name, num, veloc, resist, dext, imp, hg, gk, pass, clubs, side);
        this.crossing_ability = crossing_ability;
    }

    /**
     * Copy constructor
     * @param w WingerPlayer
     */
    public WingerPlayer(WingerPlayer w) {
        super(w);
        this.crossing_ability = w.getCrossingAbility();
    }

    /**
     * Gets ability to cross
     * @return Ability to cross
     */
    public double getCrossingAbility() {
        return this.crossing_ability;
    }

    /**
     * Sets ability to cross
     * @param crossing_ability Ability to cross
     */
    public void setCrossingAbility(double crossing_ability) {
        this.crossing_ability = crossing_ability;
    }

    /**
     * Clones a WingerPlayer
     * @return Clone
     */
    public Player clone() {
        return new WingerPlayer(this);
    }

    /**
     * Converts the Winger Player information to a string
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
         .append("    ----")
         .append("    ----")
         .append("    ----")
         .append("    ").append(String.format("%.1f", this.abilityCalculus(WP)))
         .append("    ").append(String.format("%.1f", this.abilityCalculus(FP)));

        return s.toString();
    }

    /**
     * Calculates the ability of a winger player
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
        if (pos == WP) specialSkill = this.getCrossingAbility();

        return vel * 0.15 + res * 0.15 + dex * 0.175 + imp * 0.05 + hg * 0.075 + gk * 0.05 + pass * 0.1 + specialSkill * 0.25;
    }

    /**
     * Test if the position is allowed for a Winger Player
     * @param pos Position
     * @return True if it is, False if it isn't
     */
    public boolean getAllowedPos(int pos) {
        return pos == WP || pos == FP;
    }

    /**
     * Parses a string to a WingerPlayer
     * @param input String
     * @return WingerPlayer
     */
    public static WingerPlayer parse(String input){
        String[] campos = input.split(",");
        return new WingerPlayer(campos[0],
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
     * Associates an integer to the class
     * @return Associated integer
     */
    public int getClassKey() {
        return WP;
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
        this.setCrossingAbility(Math.random() * 100);
    }
}
