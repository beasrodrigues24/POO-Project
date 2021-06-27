package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class GameHistory implements Serializable {

    /**
     * Fields
     */
    private String clubT1;
    private String clubT2;
    private int resultT1;
    private int resultT2;
    private LocalDate matchDate;
    private List<Integer> listPlayersT1;
    private List<Integer> listPlayersT2;
    private Map<Integer,Integer> subsT1;
    private Map<Integer,Integer> subsT2;

    /**
     * Parameterized constructor
     * @param clubT1 Team 1
     * @param clubT2 Team 2
     * @param resultT1 Result team 1
     * @param resultT2 Result team 2
     * @param matchDate Date of the match
     * @param listPlayersT1 List of players from team 1
     * @param listPlayersT2 List of players from team 2
     * @param subsT1 List of substitutes from team 1
     * @param subsT2 List of substitutes from team 2
     */
    public GameHistory(String clubT1, String clubT2, int resultT1, int resultT2, LocalDate matchDate, List<Integer> listPlayersT1, List<Integer> listPlayersT2, Map<Integer, Integer> subsT1, Map<Integer, Integer> subsT2) {
        this.clubT1 = clubT1;
        this.clubT2 = clubT2;
        this.resultT1 = resultT1;
        this.resultT2 = resultT2;
        this.matchDate = matchDate;
        this.listPlayersT1 = new ArrayList<>(listPlayersT1);
        this.listPlayersT2 = new ArrayList<>(listPlayersT2);
        this.subsT1 = new HashMap<>(subsT1);
        this.subsT2 = new HashMap<>(subsT2);
    }

    /**
     * Parameterized constructor
     * @param g Game
     */
    public GameHistory(Game g){
        this.clubT1 = g.getTeam1().getName();
        this.clubT2 = g.getTeam2().getName();
        this.resultT1 = g.getScoreTeam1();
        this.resultT2 = g.getScoreTeam2();
        this.matchDate = LocalDate.now();
        this.listPlayersT1 = g.getListPlayersT1();
        this.listPlayersT2 = g.getListPlayersT2();
        this.subsT1 = g.getSubsT1();
        this.subsT2 = g.getSubsT2();
    }

    /**
     * Copy constructor
     * @param gh Game History
     */
    public GameHistory(GameHistory gh) {
        this.clubT1 = gh.getClubT1();
        this.clubT2 = gh.getClubT2();
        this.resultT1 = gh.getResultT1();
        this.resultT2 = gh.getResultT2();
        this.matchDate = gh.getMatchDate();
        this.listPlayersT1 = gh.getListPlayersT1();
        this.listPlayersT2 = gh.getListPlayersT2();
        this.subsT1 = gh.getSubsT1();
        this.subsT2 = gh.getSubsT2();
    }

    /**
     * Clones a GameHistory
     * @return GameHistory
     */
    public GameHistory clone() {
        return new GameHistory(this);
    }

    /**
     * Gets the name of team 1
     * @return String
     */
    public String getClubT1() {
        return this.clubT1;
    }

    /**
     * Gets the name of team 2
     * @return String
     */
    public String getClubT2() {
        return this.clubT2;
    }

    /**
     * Gets result of team 1
     * @return Integer
     */
    public int getResultT1() {
        return this.resultT1;
    }

    /**
     * Gets result of team 2
     * @return Integer
     */
    public int getResultT2() {
        return this.resultT2;
    }

    /**
     * Gets date of the match
     * @return Date
     */
    public LocalDate getMatchDate() {
        return this.matchDate;
    }

    /**
     * Gets list of players of team 1
     * @return List of the numbers of the players
     */
    public List<Integer> getListPlayersT1() {
        return new ArrayList<>(this.listPlayersT1);
    }

    /**
     * Gets list of players of team 2
     * @return List of the numbers of the players
     */
    public List<Integer> getListPlayersT2() {
        return new ArrayList<>(this.listPlayersT2);
    }

    /**
     * Gets map of substitutes of team 1
     * @return Map
     */
    public Map<Integer, Integer> getSubsT1() {
        return new HashMap<>(this.subsT1);
    }

    /**
     * Gets map of substitutes of team 2
     * @return Map
     */
    public Map<Integer, Integer> getSubsT2() {
        return new HashMap<>(this.subsT2);
    }

    /**
     * Parses a string to GameHistory
     * @param input String
     * @return GameHistory
     * @throws ImpossibleGameStateException When the game isn't possible
     */
    public static GameHistory parse(String input) throws ImpossibleGameStateException{
        String[] campos = input.split(",");
        String[] data = campos[4].split("-");
        List<Integer> jc = new ArrayList<>();
        List<Integer> jf = new ArrayList<>();
        List<Integer> jaux;
        Map<Integer, Integer> subsC = new HashMap<>();
        Map<Integer, Integer> subsF = new HashMap<>();


        for (int i = 5; i < 16; i++){
            jc.add(Integer.parseInt(campos[i]));
        }

        jaux = new ArrayList<>(jc);
        for (int i = 16; i < 19; i++){
            String[] sub = campos[i].split("->");
            int x = Integer.parseInt(sub[0]), y = Integer.parseInt(sub[1]);
            if (jaux.contains(x)){
                subsC.put(x,y);
                jaux.remove((Integer) x);
                jaux.add(y);
            } else
                throw new ImpossibleGameStateException();
        }

        for (int i = 19; i < 30; i++){
            jf.add(Integer.parseInt(campos[i]));
        }


        jaux = new ArrayList<>(jf);
        for (int i = 30; i < 33; i++){
            String[] sub = campos[i].split("->");
            int x = Integer.parseInt(sub[0]), y = Integer.parseInt(sub[1]);
            if (jaux.contains(x)){
                subsF.put(x,y);
                jaux.remove((Integer) x);
                jaux.add(y);
            } else
                throw new ImpossibleGameStateException();
        }


        return new GameHistory(campos[0], campos[1], Integer.parseInt(campos[2]), Integer.parseInt(campos[3]),
                LocalDate.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])),
                jc, jf, subsC, subsF);
    }

    /**
     * Converts the Game History information to a string
     * @return String
     */
    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append("Game: ").append(this.getClubT1())
         .append(" vs ").append(this.getClubT2())
         .append("     ->  ").append(this.getResultT1())
         .append(" X ").append(this.getResultT2())
         .append("\nDate: ").append(this.getMatchDate().toString())
         .append("\n       Main: ").append(this.getListPlayersT1())
         .append("      Subs:  ");

        for (Map.Entry<Integer,Integer> e : this.getSubsT1().entrySet())
            s.append(e.getKey()).append(" -> ").append(e.getValue()).append("  ");

        s.append("\n       Main: ").append(this.getListPlayersT2())
         .append("      Subs: ");

        for (Map.Entry<Integer,Integer> e : this.getSubsT2().entrySet())
            s.append(e.getKey()).append(" -> ").append(e.getValue()).append("  ");

        return s.toString();
    }
}
