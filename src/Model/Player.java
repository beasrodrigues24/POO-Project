package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.Serializable;

public abstract class Player implements Serializable {

    /**
     * Public variables
     */
    // Positions
    public static final int GK = 1;
    public static final int DP = 2;
    public static final int MP = 3;
    public static final int WP = 4;
    public static final int FP = 5;

    // Sides
    public static final int LEFT = 0;
    public static final int MIDDLE = 1;
    public static final int RIGHT = 2;

    /**
     * Fields
     */
    private String name;
    private int number;
    private double velocity;
    private double resistance;
    private double dexterity;
    private double impulsion;
    private double head_game;
    private double goal_kick;
    private double pass;
    private List<String> clubs;
    private int side;

    /**
     * Default constructor
     */
    public Player() {
        this.name = "";
        this.number = 0;
        this.velocity = 0;
        this.resistance = 0;
        this.dexterity = 0;
        this.impulsion = 0;
        this.head_game = 0;
        this.goal_kick = 0;
        this.pass = 0;
        this.clubs = new ArrayList<>();
        this.side = MIDDLE;
    }

    /**
     * Parametrized constructor
     * @param name Name of the player
     * @param num Number of the player
     * @param veloc Velocity of the player
     * @param resist Resistance of the player
     * @param dext Dexterity of the player
     * @param imp Impulsion of the player
     * @param hg Head game of the player
     * @param gk Goal kick of the player
     * @param pass Pass of the player
     * @param clubs Club history of the player
     * @param side Side of the player
     */
    public Player(String name, int num, double veloc, double resist, double dext, double imp, double hg, double gk, double pass, List<String> clubs, int side) {
        this.name = name;
        this.number = num;
        this.velocity = veloc;
        this.resistance = resist;
        this.dexterity = dext;
        this.impulsion = imp;
        this.head_game = hg;
        this.goal_kick = gk;
        this.pass = pass;
        this.clubs = new ArrayList<>(clubs);
        this.side = side;
    }

    /**
     * Copy constructor
     * @param p Player
     */
    public Player(Player p) {
        this.name = p.getName();
        this.number = p.getNumber();
        this.velocity = p.getVelocity();
        this.resistance = p.getResistance();
        this.dexterity = p.getDexterity();
        this.impulsion = p.getImpulsion();
        this.head_game = p.getHeadGame();
        this.goal_kick = p.getGoalKick();
        this.pass = p.getPass();
        this.clubs = new ArrayList<>(p.getClubs());
        this.side = p.getSide();
    }

    /**
     * Gets names of player
     * @return Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets name of player
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets number of player
     * @return Number
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Sets number of player
     * @param number Number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets velocity of player
     * @return Velocity
     */
    public double getVelocity() {
        return this.velocity;
    }

    /**
     * Sets velocity of player
     * @param velocity Velocity
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets resistance of player
     * @return Resistance
     */
    public double getResistance() {
        return this.resistance;
    }

    /**
     * Sets resistance of player
     * @param resistance Resistance
     */
    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    /**
     * Gets dexterity of player
     * @return Dexterity
     */
    public double getDexterity() {
        return this.dexterity;
    }

    /**
     * Sets dexterity of player
     * @param dexterity Dexterity
     */
    public void setDexterity(double dexterity) {
        this.dexterity = dexterity;
    }

    /**
     * Gets impulsion of player
     * @return Impulsion
     */
    public double getImpulsion() {
        return this.impulsion;
    }

    /**
     * Sets impulsion of player
     * @param impulsion Impulsion
     */
    public void setImpulsion(double impulsion) {
        this.impulsion = impulsion;
    }

    /**
     * Gets head game of player
     * @return Head game
     */
    public double getHeadGame() {
        return this.head_game;
    }

    /**
     * Sets head game of player
     * @param head_game Head game
     */
    public void setHeadGame(double head_game) {
        this.head_game = head_game;
    }

    /**
     * Gets goal kick of player
     * @return Goal kick
     */
    public double getGoalKick() {
        return this.goal_kick;
    }

    /**
     * Sets goal kick of player
     * @param goal_kick Goal kick
     */
    public void setGoalKick(double goal_kick) {
        this.goal_kick = goal_kick;
    }

    /**
     * Gets pass of player
     * @return Pass
     */
    public double getPass() {
        return this.pass;
    }

    /**
     * Sets pass of player
     * @param pass Pass
     */
    public void setPass(double pass) {
        this.pass = pass;
    }

    /**
     * Gets club history of player
     * @return Club history
     */
    public List<String> getClubs() {
        return new ArrayList<>(this.clubs);
    }

    /**
     * Sets club history of player
     * @param clubs Club history
     */
    public void setClubs(List<String> clubs) {
        this.clubs = new ArrayList<>(this.clubs);
    }

    /**
     * Gets side of player
     * @return Side
     */
    public int getSide() {
        return this.side;
    }

    /**
     * Sets side of player
     * @param side
     */
    public void setSide(int side) {
        this.side = side;
    }

    /**
     * Checks if the objects are equal
     * @param o Object
     * @return True if they are equal, false if they aren't
     */
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || ! o.getClass().equals(this.getClass())) return false;
        Player v = (Player) o;
        return  this.name.equals(v.getName()) &&
                this.number == v.getNumber() &&
                this.velocity == v.getVelocity() &&
                this.resistance == v.getResistance() &&
                this.dexterity == v.getDexterity() &&
                this.impulsion == v.getImpulsion() &&
                this.head_game == v.getHeadGame() &&
                this.goal_kick == v.getGoalKick() &&
                this.pass == v.getPass() &&
                this.clubs.equals(v.getClubs()) &&
                this.side == v.getSide();
    }

    /**
     * Converts the player to a string
     * @return String
     */
    public abstract String toString();

    /**
     * Clones the player
     * @return Player
     */
    public abstract Player clone();

    /**
     * Calculates the ability of a player
     * @param pos Position where the player plays
     * @return Ability
     */
    public abstract double abilityCalculus(int pos);

    /**
     * Indicates a position is allowed for the player
     * @param pos Position
     * @return True if it's allowed, false if it isn't
     */
    public abstract boolean getAllowedPos(int pos);

    /**
     * Corresponds an integer to a class of player
     * @return Integer
     */
    public abstract int getClassKey();

    /**
     * Creates a string corresponding to the side of a player
     * @return String
     */
    public static String getStringSide (int side){
        String s = null;
        switch (side){
            case LEFT   -> s = "Left  ";
            case MIDDLE -> s = "Middle";
            case RIGHT  -> s = "Right ";
        }
        return s;
    }

    /**
     * Creates a string corresponding to the class of a player
     * @return String
     */
    public static String getStringClass(int classKey) {
        String s = null;
        switch (classKey) {
            case GK -> s = "GK";
            case DP -> s = "DP";
            case MP -> s = "MP";
            case WP -> s = "WP";
            case FP -> s = "FP";
        }
        return s;
    }

    /**
     * Generates a random player
     * @param teamNumbers Number already on the team
     */
    public abstract void newRandomPlayer(Collection<Integer> teamNumbers);
}
