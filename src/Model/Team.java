package Model;

import java.util.*;
import java.io.Serializable;
import java.util.stream.Collectors;

public class Team implements Serializable {

    /**
     * Fields
     */
    private String name;
    private Map<Integer, Player> substitutes;
    private MainPlayersList main_players;

    /**
     * Default constructor
     */
    public Team() {
        this.name = "";
        this.substitutes = new HashMap<>();
        this.main_players = new MainPlayersList();
    }

    /**
     * Parametrized constructor
     * @param name Name of the team
     */
    public Team(String name) {
        this.name = name;
        this.substitutes = new HashMap<>();
        this.main_players = new MainPlayersList();
    }

    /**
     * Parametrized constructor
     * @param name   Name of the team
     * @param subs   Map of substitutes
     * @param main_p Main players
     */
    public Team(String name, Map<Integer, Player> subs, MainPlayersList main_p) {
        this.name = name;
        this.substitutes = new HashMap<>();
        for (Map.Entry<Integer, Player> e : subs.entrySet())
            this.substitutes.put(e.getKey(), e.getValue().clone());
        this.main_players = new MainPlayersList(main_p.clone());
    }

    /**
     * Copy constructor
     * @param t Team
     */
    public Team(Team t) {
        this.name = t.getName();
        this.substitutes = new HashMap<>(t.getSubstitutes());
        this.main_players = new MainPlayersList(t.getMain_players());

    }

    /**
     * Gets name of the team
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets name of the team
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the substitute players from a team
     * @return Substitute players
     */
    public Map<Integer, Player> getSubstitutes() {
        Map<Integer, Player> nl = new HashMap<>();
        for (Map.Entry<Integer, Player> e : substitutes.entrySet())
            nl.put(e.getKey(), e.getValue().clone());

        return nl;
    }

    /**
     * Sets the substitute player of a team
     * @param substitutes Substitute players
     */
    public void setSubstitutes(Map<Integer, Player> substitutes) {
        this.substitutes = new HashMap<>();
        for (Map.Entry<Integer, Player> e : substitutes.entrySet())
            this.substitutes.put(e.getKey(), e.getValue().clone());
    }

    /**
     * Gets the main player of a team
     *
     * @return Main Players
     */
    public MainPlayersList getMain_players() {
        return this.main_players.clone();
    }

    /**
     * Sets the main players of a team
     * @param main_players Main Players
     */
    public void setMain_players(MainPlayersList main_players) {
        this.main_players = new MainPlayersList(main_players.clone());
    }

    /**
     * Clones a team
     * @return Team clone
     */
    public Team clone() {
        return new Team(this);
    }

    /**
     * Calculates the attack ability
     * @return Attack ability
     */
    public double calculateAttackAbility() {
        return main_players.getTeamAttackAbility();
    }

    /**
     * Calculates the defense ability
     * @return Defense ability
     */
    public double calculateDefenseAbility() {
        return main_players.getTeamDefenseAbility();
    }

    /**
     * Creates a string with the information in the team
     * @return String
     */
    public String toString() {
        StringBuilder s = new StringBuilder();

        System.out.println("\n------------------------------------------------------------------\n");

        s.append("Team: ").append(this.getName())
         .append("\nPlayer                      |   GK  |   DP  |   MP  |   WP  |   FP  |")
         .append("\nmain_player:");

        for (Player p : main_players.getMpl())
            s.append("\n     ").append(p.toString());

        s.append("\nsubs:");

        for (Player p : substitutes.values())
            s.append("\n     ").append(p.toString());

        s.append("\n\n---------------------------------------------------------------------------");

        return s.toString();
    }

    /**
     * Parses a string into a team
     * @param input String
     * @return Team
     */
    public static Team parse(String input) {
        String[] campos = input.split(",");
        return new Team(campos[0]);
    }

    /**
     * Sets the players in the team
     * @param playersList List of player
     * @throws DuplicatedPlayerException When a player appears twice
     */
    public void setPlayers(List<Player> playersList) throws DuplicatedPlayerException {
        Player pAux;

        for (int i = 0; i < playersList.size(); i++) {
            // Accesses a player
            pAux = playersList.get(i);
            // Selects the first 11 players as main players
            if (i < 11)
                switch (i) {
                    // Sorts the player in the list by GK - DP - MP - WP - FP
                    case 0, 9, 10:
                        pAux.setSide(MainPlayersList.indexToSide(i));
                        this.main_players.add(pAux, i);
                        break;
                    case 2, 3:
                        pAux.setSide(MainPlayersList.indexToSide(i - 1));
                        this.main_players.add(pAux, i - 1);
                        break;
                    case 5, 6, 7, 8:
                        pAux.setSide(MainPlayersList.indexToSide(i - 2));
                        this.main_players.add(pAux, i - 2);
                        break;
                    case 1:
                        pAux.setSide(MainPlayersList.indexToSide(7));
                        this.main_players.add(pAux, 7);
                        break;
                    case 4:
                        pAux.setSide(MainPlayersList.indexToSide(8));
                        this.main_players.add(pAux, 8);
                        break;
                }
            else { // The rest is added as a substitute
                pAux.setSide(randomSide(pAux));
                this.substitutes.put(pAux.getNumber(), pAux);
            }
        }
    }

    /**
     * Generates a random side for the player to play on
     * @param p Player
     * @return Side
     */
    public static int randomSide(Player p) {
        int r = 0;
        switch (p.getClassKey()) {
            case Player.GK, Player.DP -> r = Player.MIDDLE;
            case Player.MP -> r = (int) (Math.random() * 3);
            case Player.WP, Player.FP -> r = (int) (Math.random() * 2) * 2;
        }
        return r;
    }

    /**
     * Switches players between two teams
     * @param teams_catalog Catalog with all the teams
     * @param idTeam1       First team
     * @param idTeam2       Second team
     * @param numPlayer1    Number of player from team 1
     * @param numPlayer2    Number of player from team 2
     */
    public static void switchPlayers(Map<Integer, Team> teams_catalog, int idTeam1, int idTeam2, int numPlayer1, int numPlayer2) {
        Team t1 = teams_catalog.get(idTeam1).clone();
        Team t2 = teams_catalog.get(idTeam2).clone();
        if (t1.getSubstitutes().containsKey(numPlayer1) && t2.getSubstitutes().containsKey(numPlayer2)) {

            Map<Integer, Player> t1_subs = t1.getSubstitutes();
            Map<Integer, Player> t2_subs = t2.getSubstitutes();
            Player auxP1 = t1_subs.get(numPlayer1).clone();
            Player auxP2 = t2_subs.get(numPlayer2).clone();
            t1_subs.remove(auxP1.getNumber());
            t2_subs.remove(auxP2.getNumber());
            auxP1.setNumber(t2.generateNewTeamPlayerNumber());
            auxP2.setNumber(t1.generateNewTeamPlayerNumber());
            auxP1.getClubs().add(t2.getName());
            auxP2.getClubs().add(t1.getName());
            t1_subs.put(auxP2.getNumber(), auxP2);
            t1.setSubstitutes(t1_subs);
            t2_subs.put(auxP1.getNumber(), auxP1);
            t2.setSubstitutes(t2_subs);
            System.out.println(t1);
            System.out.println(t2);
            teams_catalog.put(idTeam1, t1);
            teams_catalog.put(idTeam2, t2);
        }
    }

    /**
     * Swaps two players in main players list
     * @param p Player to put in main players list
     * @param index Index of switch
     * @return Player removed
     * @throws DuplicatedPlayerException Double player
     */
    public Player mainPlayersSub(Player p, int index) throws DuplicatedPlayerException {
        return this.main_players.subs(p,index);
    }

    /**
     * Swaps two players in substitutes list
     * @param pIn Player to put in substitutes list
     * @param indexIn Index to put
     * @param pOut Player to remove from substitutes list
     * @param indexOut Index to remove
     */
    public void substitutesSub(int indexIn, Player pIn, int indexOut, Player pOut){
        this.substitutes.put(indexOut,pOut);
        this.substitutes.remove(indexIn, pIn);
    }

    /**
     * Checks if the objects are equal
     * @param o Object
     * @return True if they are equal, false if they aren't
     */
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || ! o.getClass().equals(this.getClass())) return false;
        Team t = (Team) o;
        return  this.name.equals(t.getName()) &&
                this.substitutes.equals(t.getSubstitutes()) &&
                this.main_players.equals(t.getMain_players());
    }

    /**
     * Generates a number for a player to use in a team, without repeating
     * @return Number
     */
    public int generateNewTeamPlayerNumber(){
        Set<Integer> e = new HashSet<>(this.getMain_players().getPlayerNumbers());
        e.addAll(this.getSubstitutes().values().stream().map(Player::getNumber).collect(Collectors.toList()));
        int newNumb;
        do {
            newNumb = (int)(Math.random() * 100);
            if (!e.contains(newNumb)) return newNumb;
        }while (true);
    }

    /**
     * Generates a random team
     * @param teamName Name of the random team
     */
    public void generateTomTeam (String teamName) {

        Set<Integer> numbers = new HashSet<>();

        GoalKeeper    gk = new GoalKeeper();
        DefensePlayer dp = new DefensePlayer();
        MiddlePlayer  mp = new MiddlePlayer();
        WingerPlayer  wp = new WingerPlayer();
        FrontPlayer   fp = new FrontPlayer();

        try{
            gk.newRandomPlayer(numbers); numbers.add(gk.getNumber()); this.main_players.add(gk.clone(), 0);
            dp.newRandomPlayer(numbers); numbers.add(dp.getNumber()); this.main_players.add(dp.clone(), 1);
            dp.newRandomPlayer(numbers); numbers.add(dp.getNumber()); this.main_players.add(dp.clone(), 2);
            mp.newRandomPlayer(numbers); mp.setSide(Player.LEFT); numbers.add(mp.getNumber()); this.main_players.add(mp.clone(), 3);
            mp.newRandomPlayer(numbers); mp.setSide(Player.MIDDLE); numbers.add(mp.getNumber()); this.main_players.add(mp.clone(), 4);
            mp.newRandomPlayer(numbers); mp.setSide(Player.MIDDLE); numbers.add(mp.getNumber()); this.main_players.add(mp.clone(), 5);
            mp.newRandomPlayer(numbers); mp.setSide(Player.RIGHT); numbers.add(mp.getNumber()); this.main_players.add(mp.clone(), 6);
            wp.newRandomPlayer(numbers); wp.setSide(Player.LEFT); numbers.add(wp.getNumber()); this.main_players.add(wp.clone(), 7);
            wp.newRandomPlayer(numbers); wp.setSide(Player.RIGHT); numbers.add(wp.getNumber()); this.main_players.add(wp.clone(), 8);
            fp.newRandomPlayer(numbers); fp.setSide(Player.LEFT); numbers.add(fp.getNumber()); this.main_players.add(fp.clone(), 9);
            fp.newRandomPlayer(numbers); fp.setSide(Player.RIGHT); numbers.add(fp.getNumber()); this.main_players.add(fp.clone(), 10);
        } catch (DuplicatedPlayerException ignored) {}

        int pos;
        for (int i = 0; i < 9;i++){
            pos = (int) (Math.random() * 5) + 1;
            switch (pos){
                case Player.GK -> {gk.newRandomPlayer(numbers); numbers.add(gk.getNumber()); this.substitutes.put(gk.getNumber(),gk.clone());}
                case Player.DP -> {dp.newRandomPlayer(numbers); numbers.add(dp.getNumber()); this.substitutes.put(dp.getNumber(),dp.clone());}
                case Player.MP -> {mp.newRandomPlayer(numbers); numbers.add(mp.getNumber()); this.substitutes.put(mp.getNumber(),mp.clone());}
                case Player.WP -> {wp.newRandomPlayer(numbers); numbers.add(wp.getNumber()); this.substitutes.put(wp.getNumber(),wp.clone());}
                case Player.FP -> {fp.newRandomPlayer(numbers); numbers.add(fp.getNumber()); this.substitutes.put(fp.getNumber(),fp.clone());}
            }
        }

        this.name = teamName;
    }

    /**
     * Sets the names of the player of a team
     * @param newPlayersNames List of names
     */
    public void setPlayersNames(List<String> newPlayersNames){
        List<Player> nMpl = this.main_players.getMpl();
        int i;
        for (i = 0; i < 11; i++)
            nMpl.get(i).setName(newPlayersNames.get(i));
        for(Map.Entry<Integer,Player> e : this.substitutes.entrySet()){
            i++;
            e.getValue().setName(newPlayersNames.get(i));
        }
    }
}
