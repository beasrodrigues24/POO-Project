package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefensePlayer extends Player {

    /**
     * Fields
     */
    private double deflecting_ability;

    /**
     * Default constructor
     */
    public DefensePlayer() {
        super();
        this.deflecting_ability = 0;
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
     * @param deflect Deflecting ability
     */
    public DefensePlayer(String name, int num, double veloc, double resist, double dext, double imp, double hg, double gk, double pass, List<String> clubs, int side, double deflect) {
        super(name, num, veloc, resist, dext, imp, hg, gk, pass, clubs, side);
        this.deflecting_ability = deflect;
    }

    /**
     * Copy constructor
     * @param dp Defense Player
     */
    public DefensePlayer(DefensePlayer dp) {
        super(dp);
        this.deflecting_ability = dp.deflecting_ability;
    }

    /**
     * Clones a Defense Player
     * @return Defense Player
     */
    public DefensePlayer clone() {
        return new DefensePlayer(this);
    }

    /**
     * Gets deflecting ability
     * @return Deflecting ability
     */
    public double getDeflectingAbility() {
        return this.deflecting_ability;
    }

    /**
     * Sets deflecting ability
     * @param deflecting_ability Deflecting ability
     */
    public void setDeflectingAbility(double deflecting_ability) {
        this.deflecting_ability = deflecting_ability;
    }

    /**
     * Converts the Defense Player information to a stringe
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
         .append("    ").append(String.format("%.1f", this.abilityCalculus(DP)))
         .append("    ").append(String.format("%.1f", this.abilityCalculus(MP)))
         .append("    ----")
         .append("    ----");

        return s.toString();
    }

    /**
     * Calculates the ability of a Defense Player
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
        if (pos == DP) specialSkill = this.getDeflectingAbility();

        return vel * 0.075 + res * 0.1 + dex * 0.075 + imp * 0.075 + hg * 0.1 + gk * 0.075 + pass * 0.2 + specialSkill * 0.3;
    }

    /**
     * Checks if a position is allowed for a Defense Player
     * @param pos Position
     * @return True if it is, False if it isn't
     */
    public boolean getAllowedPos(int pos) {
        return pos == DP || pos == MP;
    }

    /**
     * Parses a string to a Player
     * @param input String
     * @return Player
     */
    public static Player parse(String input){
        String[] campos = input.split(",");
        return new DefensePlayer(campos[0],
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
                Math.random() * 100);
    }

    /**
     * Associates an integer to the class
     * @return Associated integer
     */
    public int getClassKey() {
        return DP;
    }

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
        this.setDeflectingAbility(Math.random() * 100);
    }
}
