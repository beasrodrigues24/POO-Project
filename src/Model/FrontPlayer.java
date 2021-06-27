package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FrontPlayer extends Player{

    /**
     * Field
     */
    private double finishing_ability;

    /**
     * Default constructor
     */
    public FrontPlayer() {
        super();
        this.finishing_ability = 0;
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
     * @param clubs Clubs
     * @param side Side
     * @param finishing_ability Finishing ability
     */
    public FrontPlayer(String name, int num, double veloc, double resist, double dext, double imp, double hg, double gk, double pass, List<String> clubs, int side, double finishing_ability) {
        super(name, num, veloc, resist, dext, imp, hg, gk, pass, clubs, side);
        this.finishing_ability = finishing_ability;
    }

    /**
     * Copy constructor
     * @param fp FrontPlayer
     */
    public FrontPlayer(FrontPlayer fp) {
        super(fp);
        this.finishing_ability = fp.finishing_ability;
    }

    /**
     * Clones a FrontPlayer
     * @return Clone
     */
    public FrontPlayer clone() {
        return new FrontPlayer(this);
    }

    /**
     * Gets finishing ability of FrontPlayer
     * @return finishing ability
     */
    public double getFinishingAbility() {
        return this.finishing_ability;
    }

    /**
     * Sets finishing ability of FrontPlayer
     * @param finishing_ability Finishing ability
     */
    public void setFinishingAbility(double finishing_ability) {
        this.finishing_ability = finishing_ability;
    }

    /**
     * Converts a FrontPlayer's information to a string
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
     * Calculates the ability of a FrontPlayer
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
        if (pos == FP) specialSkill = this.getFinishingAbility();

        return vel * 0.125 + res * 0.05 + dex * 0.125 + imp * 0.125 + hg * 0.175 + gk * 0.175 + pass * 0.05 + specialSkill * 0.175;
    }

    /**
     * Indicates if a position is allowed for a FrontPlayer
     * @param pos Position
     * @return True if it is allowed, False if it isn't
     */
    public boolean getAllowedPos(int pos) {
        return pos == FP || pos == WP;
    }

    /**
     * Parses a string to a Player
     * @param input String
     * @return Player
     */
    public static Player parse(String input){
        String[] campos = input.split(",");
        return new FrontPlayer(campos[0],
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
     * Gets an integer that corresponds to the class of player
     * @return Integer
     */
    public int getClassKey() {
        return FP;
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
        this.setFinishingAbility(Math.random() * 100);
    }
}
