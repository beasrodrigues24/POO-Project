package Model;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable, GameInterface{

    /**
     * Fields
     */
    private int score_team_1;
    private int score_team_2;
    private double time;
    private double attack_ability_team_1;
    private double defense_ability_team_1;
    private double attack_ability_team_2;
    private double defense_ability_team_2;
    private Map<Integer, Integer> subsT1;
    private Map<Integer, Integer> subsT2;
    private Map<Integer, Team> teams;
    private final boolean startTeam;
    private boolean attackerTeam;
    private boolean half;
    private List<Integer> listPlayersT1;
    private List<Integer> listPlayersT2;

    /**
     * Default constructor
     */
    public Game() {
        this.score_team_1 = 0;
        this.score_team_2 = 0;
        this.time = 0;
        this.attack_ability_team_1 = 0;
        this.defense_ability_team_1 = 0;
        this.attack_ability_team_2 = 0;
        this.defense_ability_team_2 = 0;
        this.subsT1 = new HashMap<>();
        this.subsT2 = new HashMap<>();
        this.teams = new HashMap<>();
        this.startTeam = ((int)(Math.random() * 2)) == 0;
        this.attackerTeam = startTeam;
        this.half = false;
        this.listPlayersT1 = new ArrayList<>();
        this.listPlayersT2 = new ArrayList<>();
    }

    /**
     * Parametrized constructor
     * @param score_t1 Score of the first team
     * @param score_t2 Score of the second team
     * @param subs_t1 Map of substitutions of the first team
     * @param subs_t2 Map of substitutions of the seconds team
     * @param time Time passed
     * @param t1 Team 1
     * @param t2 Team 2
     * @param startTeam True if first team, false if second team
     * @param attackerTeam True if first team, false if second team
     * @param half True the first half has already passed, false if not
     */
    public Game(int score_t1, int score_t2, Map<Integer, Integer> subs_t1, Map<Integer, Integer> subs_t2, double time, Team t1, Team t2, boolean startTeam, boolean attackerTeam, boolean half, List<Integer> listPlayersT1, List<Integer> listPlayersT2) {
        this.score_team_1 = score_t1;
        this.score_team_2 = score_t2;
        this.time = time;
        this.attack_ability_team_1 = t1.calculateAttackAbility();
        this.defense_ability_team_1 = t1.calculateDefenseAbility();
        this.attack_ability_team_2 = t2.calculateAttackAbility();
        this.defense_ability_team_2 = t2.calculateDefenseAbility();
        this.startTeam = startTeam;
        this.attackerTeam = attackerTeam;
        this.half = half;
        this.subsT1 = new HashMap<>(subs_t1);
        this.subsT2 = new HashMap<>(subs_t2);
        this.teams = new HashMap<>();
        this.teams.put(1, t1.clone());
        this.teams.put(2, t2.clone());
        this.listPlayersT1 = new ArrayList<>(listPlayersT1);
        this.listPlayersT2 = new ArrayList<>(listPlayersT2);
    }

    /**
     * Copy constructor
     * @param g Game
     */
    public Game(Game g) {
        this.score_team_1 = g.getScoreTeam1();
        this.score_team_2 = g.getScoreTeam2();
        this.time = g.getTime();
        this.attack_ability_team_1 = g.getAttackAbilityTeam1();
        this.defense_ability_team_1 = g.getDefenseAbilityTeam1();
        this.attack_ability_team_2 = g.getAttackAbilityTeam2();
        this.defense_ability_team_2 = g.getDefenseAbilityTeam2();
        this.subsT1 = g.getSubsT1();
        this.subsT2 = g.getSubsT2();
        this.teams = new HashMap<>();
        this.teams.put(1, g.getTeam1());
        this.teams.put(2, g.getTeam2());
        this.startTeam = g.isStartTeam();
        this.attackerTeam = g.isAttackerTeam();
        this.half = g.isFirstHalf();
        this.listPlayersT1 = g.getListPlayersT1();
        this.listPlayersT2 = g.getListPlayersT2();
    }

    /**
     * Parametrized constructor
     * @param t1 Team 1
     * @param t2 Team 2
     */
    public Game(Team t1, Team t2) {
        this.score_team_1 = 0;
        this.score_team_2 = 0;
        this.time = 0;
        this.attack_ability_team_1 = t1.calculateAttackAbility();
        this.defense_ability_team_1 = t1.calculateDefenseAbility();
        this.attack_ability_team_2 = t2.calculateAttackAbility();
        this.defense_ability_team_2 = t2.calculateDefenseAbility();
        this.subsT1 = new HashMap<>();
        this.subsT2 = new HashMap<>();
        this.teams = new HashMap<>();
        this.teams.put(1, t1.clone());
        this.teams.put(2, t2.clone());
        this.startTeam = ((int)(Math.random() * 2)) == 0;
        this.attackerTeam = startTeam;
        this.half = false;
        this.listPlayersT1 = t1.getMain_players().getPlayerNumbers();
        this.listPlayersT2 = t2.getMain_players().getPlayerNumbers();
    }

    /**
     * Clones a game
     * @return Game
     */
    public Game clone() {
        return new Game(this);
    }

    /**
     * Gets score of the first team
     * @return Score
     */
    public int getScoreTeam1() {
        return this.score_team_1;
    }

    /**
     * Gets score of the second team
     * @return Score
     */
    public int getScoreTeam2() {
        return this.score_team_2;
    }

    /**
     * Gets time of the game
     * @return
     */
    public double getTime() {
        return this.time;
    }

    /**
     * Gets attack ability of the first team
     * @return Attack ability
     */
    public double getAttackAbilityTeam1() {
        return this.attack_ability_team_1;
    }

    /**
     * Gets defense ability of the first team
     * @return Defense ability
     */
    public double getDefenseAbilityTeam1() {
        return this.defense_ability_team_1;
    }

    /**
     * Gets attack ability of the second team
     * @return Attack ability
     */
    public double getAttackAbilityTeam2() {
        return this.attack_ability_team_2;
    }

    /**
     * Gets defense ability of the second team
     * @return Defense ability
     */
    public double getDefenseAbilityTeam2() {
        return this.defense_ability_team_2;
    }

    /**
     * Gets map of the substitutes of the first team
     * @return Map of the substitutes
     */
    public Map<Integer, Integer> getSubsT1() {
        return new HashMap<>(subsT1);
    }

    /**
     * Gets map of the substitutes of the second team
     * @return Map of the substitutes
     */
    public Map<Integer, Integer> getSubsT2() {
        return new HashMap<>(subsT2);
    }

    /**
     * Gets a list of the numbers of the starting eleven of the first team
     * @return List of players numbers
     */
    public List<Integer> getListPlayersT1() {
        return new ArrayList<>(listPlayersT1);
    }

    /**
     * Gets a list of the numbers of the starting eleven of the second team
     * @return List of players numbers
     */
    public List<Integer> getListPlayersT2() {
        return new ArrayList<>(listPlayersT2);
    }

    /**
     * Gets the first team
     * @return Team 1
     */
    public Team getTeam1() {
        return this.teams.get(1).clone();
    }

    /**
     * Gets the seconds team
     * @return Team 2
     */
    public Team getTeam2() {
        return this.teams.get(2).clone();
    }

    /**
     * Checks if tfirst team is the start team
     * @return True if first team, false if second team
     */
    public boolean isStartTeam() {
        return this.startTeam;
    }

    /**
     * Checks if first team is the attacker team
     * @return True if first team, false if second team
     */
    public boolean isAttackerTeam() {
        return this.attackerTeam;
    }

    /**
     * Checks if it's the first half of the game
     * @return True if it is, False if it isn't
     */
    public boolean isFirstHalf() {
        return !this.half;
    }

    /**
     * Calculates a new state for the game
     * @param attacker Attacker
     * @param defender Defender
     * @return Integer
     */
    private int newState (double attacker, double defender) {
        double rand = Math.random();
        if (rand >= 0.5) return 0; // (0) ->  perdeu a bola dentro de campo
        else if (rand >= 0.375) return 1; // (1) ->  lançamento com perda de bola / passou pela linha de fundo / guarda redes apanhou
        else if (rand >= 0.25) return 3; // (3) ->  lançamento/canto mantendo a bola (print frase bonita)
        else if ((attacker / (defender + attacker) * 0.25) >= rand) return 2; // (2) ->  golo dos atacantes e bola ao centro para os outros
        else return 1;
    }

    /**
     * Function that simulates one play of the game
     * @return Integer
     */
    public int step(){
        if (this.time > 90) // End of the game
            return (int) this.time;
        else if (!this.half && this.time >= 45){ // End of the first part of the game
            int firstHalf = (int) this.time;
            this.time = 45;
            this.attackerTeam = !this.startTeam;
            this.half = true;
            return firstHalf;
        }
        else {
            this.time += Math.random() * 7; // Passes anytime from 0 to 7 minutes, randomly
            int action;

            if (this.attackerTeam) // Executes the action depending on whose turn it is
                action = newState(this.attack_ability_team_1, this.defense_ability_team_2);
            else
                action = newState(this.attack_ability_team_2, this.defense_ability_team_1);

            switch (action) {
                case 0, 1 -> this.attackerTeam = !this.attackerTeam; //  Attacker team loses the ball
                case 2 -> { // Case of goal
                    if (this.attackerTeam) this.score_team_1++;
                    else this.score_team_2++;
                    this.attackerTeam = !this.attackerTeam;
                }
                default -> {
                }
            }
            return action;
        }
    }

    /**
     * Function that does a substitutions
     * @param teamNum Team number
     * @param numIn Number of the player going in
     * @param numOut Number of the player going out
     * @throws DuplicatedPlayerException When the same player appears more than once
     * @throws NullPointerException When pointer is null
     */
    public void sub(int teamNum, int numIn, int numOut) throws DuplicatedPlayerException, NullPointerException {
        // Limits the number of substitutions
        if ((teamNum == 1 && this.subsT1.size() < 3) || (teamNum == 2 && this.subsT2.size() < 3)) {
            Team team = this.teams.get(teamNum).clone(); // Finds team to do the substitution
            int indexOut = team.getMain_players().findNum(numOut); // Finds the index on the list of main players where the player who's leaving is
            Player playerIn = team.getSubstitutes().get(numIn).clone();
            Player playerOut = team.mainPlayersSub(playerIn, indexOut);

            if (playerOut != null){
                // Executes the substitution
                team.substitutesSub(playerIn.getNumber(), playerIn, playerOut.getNumber(), playerOut);

                if (teamNum == 1) {
                    this.subsT1.put(playerOut.getNumber(), playerIn.getNumber());
                    this.attack_ability_team_1 = team.calculateAttackAbility();
                    this.defense_ability_team_1 = team.calculateDefenseAbility();
                } else {
                    this.subsT2.put(playerOut.getNumber(), playerIn.getNumber());
                    this.attack_ability_team_2 = team.calculateAttackAbility();
                    this.defense_ability_team_2 = team.calculateDefenseAbility();
                }
                this.teams.put(teamNum, team);
            } else
                throw new NullPointerException();
        }
        else
            throw new NullPointerException();
    }

    /**
     * Function that creates a new GameHistory based on this game
     * @return New GameHistory from this game
     */
    public GameHistory gameHistory(){
        return new GameHistory(this);
    }

    /**
     * Function that return an image of this game
     * @return this Game
     */
    public Game getGame(){
        return this.clone();
    }
}

