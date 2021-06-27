package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainPlayersList implements Serializable {

    /**
     * Field
     */
    private List<Player> mpl;

    /**
     * Default constructor
     */
    public MainPlayersList() {
        this.mpl = new ArrayList<>(Collections.nCopies(11, null));
    }

    /**
     * Copy constructor
     * @param mpl Main Players List
     */
    public MainPlayersList(MainPlayersList mpl) {
        this.mpl = new ArrayList<>(mpl.getMpl());
    }

    /**
     * Clones a MainPlayersList
     * @return Clone
     */
    public MainPlayersList clone() {
        return new MainPlayersList(this);
    }

    /**
     * Gets the main players list
     * @return List of players
     */
    public List<Player> getMpl() {
        List<Player> nMpl = new ArrayList<>();
        for (Player p : this.mpl)
            nMpl.add(p.clone());

        return nMpl;
    }

    /**
     * Sets the main players list
     * @param mpl Main Player List
     */
    public void setMpl(List<Player> mpl) {
        this.mpl = new ArrayList<>();
        for (Player p : mpl)
            this.mpl.add(p.clone());
    }

    /**
     * Adds player to the main players list
     * @param p Player
     * @param index Index of the list
     * @throws DuplicatedPlayerException Repeated player
     */
    public boolean add(Player p, int index) throws DuplicatedPlayerException {
        if (!mpl.contains(p))
            if (mpl.get(index) == null && p.getAllowedPos(indexToPos(index)) && p.getSide() == indexToSide(index)) {
                mpl.set(index, p.clone());
                return true;
            } else throw new DuplicatedPlayerException();
        return false;
    }

    /**
     * Swaps two players
     * @param p Player to put in main players list
     * @param index Index of switch
     * @return Player removed
     * @throws DuplicatedPlayerException Repeated player
     */
    public Player subs(Player p, int index) throws DuplicatedPlayerException {
        if (!this.mpl.contains(p))
            if (p.getAllowedPos(indexToPos(index)) && p.getSide() == indexToSide(index)) {
                Player aux = this.mpl.get(index).clone();
                this.mpl.set(index, p);
                return aux;
            } else
                return null;
        else
            throw new DuplicatedPlayerException();
    }

    /**
     * Gets player ability
     * @param index Index where the player is
     * @return Player ability
     */
    public double getPAbility(int index) {
        return mpl.get(index).abilityCalculus(indexToPos(index));
    }

    /**
     * Associates to the indexes a position
     * @param index Index
     * @return Position associated (GK, DP, MP, WP or FP)
     */
    public static int indexToPos(int index) {
        if (index == 0) return Player.GK;
        else if (index <= 2) return Player.DP;
        else if (index <= 6) return Player.MP;
        else if (index <= 8) return Player.WP;
        else return Player.FP;
    }

    /**
     * Associates to the indexes a side
     * @param index Index
     * @return Side associated (Left, Middle or Right)
     */
    public static int indexToSide(int index) {
        int r = -1;
        switch (index) {
            case 3, 7, 9 ->  r = Player.LEFT;
            case 0, 1, 2, 4, 5 ->  r = Player.MIDDLE;
            case 6, 8, 10 ->  r = Player.RIGHT;
        }
        return r;
    }

    /**
     * Calculates the team attack ability
     * @return Attack ability
     */
    public double getTeamAttackAbility() {
        double total = 0;

        for (int index = 0; index < 11; index++) {
            switch (indexToPos(index)) {
                case Player.GK -> total += getPAbility(index) * 0.020;
                case Player.DP -> total += getPAbility(index) * 0.025;
                case Player.MP -> total += getPAbility(index) * 0.075;
                case Player.WP -> total += getPAbility(index) * 0.135;
                case Player.FP -> total += getPAbility(index) * 0.180;
            }
        }
        return total;
    }

    /**
     * Calculates the team defense ability
     * @return Defense ability
     */
    public double getTeamDefenseAbility() {
        double total = 0;

        for (int index = 0; index < 11; index++) {
            switch (indexToPos(index)) {
                case Player.GK -> total += getPAbility(index) * 0.180;
                case Player.DP -> total += getPAbility(index) * 0.175;
                case Player.MP -> total += getPAbility(index) * 0.095;
                case Player.WP -> total += getPAbility(index) * 0.025;
                case Player.FP -> total += getPAbility(index) * 0.020;
            }
        }
        return total;
    }

    /**
     * Collects the players numbers
     * @return List with the numbers
     */
    public List<Integer> getPlayerNumbers(){
        return this.mpl.stream().map(Player::getNumber).collect(Collectors.toList());
    }

    /**
     * Finds the index of a player, through is number
     * @param numOut Number of the player
     * @return Index
     */
    public int findNum(int numOut) {
        int i = 0;
        for(Player p : this.mpl) {
            if (p.getNumber() == numOut) return i;
            i++;
        }
        return -1;
    }


    /**
     * Converts the class to a string
     * @return String
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Player p : this.mpl)
            s.append("\n     ").append(p.toString());
        return s.toString();
    }

    /**
     * Checks if the objects are equal
     * @param o Object
     * @return True if they are equal, false if they aren't
     */
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || ! o.getClass().equals(this.getClass())) return false;
        MainPlayersList pl = (MainPlayersList) o;
        return  this.mpl.equals(pl.getMpl());
    }
}
