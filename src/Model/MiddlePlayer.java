package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MiddlePlayer extends Player implements Serializable {

    /**
     * Fields
     */
    private double ball_recovery;

    /**
     * Default constructor
     */
    public MiddlePlayer() {
        super();
        this.ball_recovery = 0;
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
     * @param ball_recovery Ball recovery
     */
    public MiddlePlayer(String name, int num, double veloc, double resist, double dext, double imp, double hg, double gk, double pass, List<String> clubs, int side, double ball_recovery) {
        super(name, num, veloc, resist, dext, imp, hg, gk, pass, clubs, side);
        this.ball_recovery = ball_recovery;
    }

    /**
     * Copy constructor
     * @param mp Middle Player
     */
    public MiddlePlayer(MiddlePlayer mp) {
        super(mp);
        this.ball_recovery = mp.ball_recovery;
    }

    /**
     * Clones a Middle Player
     * @return Middle Player
     */
    public MiddlePlayer clone() {
        return new MiddlePlayer(this);
    }

    /**
     * Gets Ball Recovery ability
     * @return Ability
     */
    public double getBallRecovery() {
        return this.ball_recovery;
    }

    /**
     * Sets Ball Recovery Ability
     * @param ball_recovery Ball Recovery ability
     */
    public void setBall_recovery(double ball_recovery) {
        this.ball_recovery = ball_recovery;
    }

    /**
     * Converts the information of a middle player to a string
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
     * Calculates the ability of a Middle Player
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
        if (pos == MP || (pos == DP && this.getSide() != MIDDLE)) specialSkill = this.getBallRecovery();

        return vel * 0.075 + res * 0.175 + dex * 0.05 + imp * 0.075 + hg * 0.125 + gk * 0.125 + pass * 0.175 + specialSkill * 0.20;
    }

    /**
     * Checks if a position is allowed for a Middle Player
     * @param pos Position
     * @return True it is, False if it isn't
     */
    public boolean getAllowedPos(int pos) {
        return pos == MP || pos == DP;
    }

    /**
     * Parses a string to a player
     * @param input String
     * @return Player
     */
    public static Player parse(String input){
        String[] campos = input.split(",");
        return new MiddlePlayer(campos[0],
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
        return MP;
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
        this.setBall_recovery(Math.random() * 100);
    }
}
