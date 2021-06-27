package View;

import java.util.*;
import static java.lang.System.in;
import Model.*;

public class View implements ViewInterface {

    /**
     * Fields
     */
    private final Map<Integer, List<String>> actions;

    /**
     * Default constructor
     */
    public View() {
        this.actions = getListActions();
    }

    /**
     * Function that introduces an initial prompt to the program with instructions to the user
     * @return input from the user
     */
    public int initialPrompt(String team_name) {
        System.out.println("\n\n\n-------------------------------------------------------------------------------------\n");
        System.out.println("Welcome to the Sports Manager! Insert one of the following options:");
        if (team_name != null) System.out.println("Team selected: " + team_name);
        System.out.println("    * 1 - Select Team");
        System.out.println("    * 2 - Starts a new game");
        System.out.println("    * 3 - Load game");
        System.out.println("    * 4 - Save game");
        System.out.println("    * 5 - Generate new team");
        System.out.println("    * 6 - Trade players");
        System.out.println("    * 7 - Game history");
        System.out.println("    * 0 - Quits game");
        Scanner scanner = new Scanner(in);
        int next = scanner.nextInt();
        return next;
    }

    /**
     * Function that inform the user that the state was loaded
     */
    public void loadPrompt() {
        System.out.println("State loaded\n");
    }

    /**
     * Function that inform the user that the state was Saved
     */
    public void SavePrompt() {
        System.out.println("State Saved\n");
    }

    /**
     * Function that prints information indicating that the game has started
     */
    public void gamePrompt() {
        System.out.println("\n-------------------------------------------------------------------------------------\n");
        System.out.println("\n----------------------------------");
        System.out.println("Game has started.");
        System.out.println("----------------------------------\n");
    }

    /**
     * Function that prints the current score of the game
     * @param team_1 Score of the first team
     * @param team_2 Score of the second team
     */
    public void printScore(int team_1, int team_2) {
        System.out.println("\n      ->  Score: " + team_1 + " X " + team_2);
    }

    /**
     * Function that indicates the states available where to save a state
     * @return Input from the user
     * @throws InputMismatchException When the input isn't an integer
     */
    public int statesAvailable() throws InputMismatchException{
        System.out.println("\n----------------------------------");
        System.out.println("    * 1 - save 1");
        System.out.println("    * 2 - save 2");
        System.out.println("    * 3 - save 3");
        System.out.println("    * 0 - Return");
        Scanner scanner = new Scanner(in);
        int next = scanner.nextInt();
        return next;
    }

    /**
     * Function that indicated the game was closed
     */
    public void quitMessage() {
        System.out.println("Game Closed");
    }

    /**
     * Function that indicates there was inserted an invalid option
     */
    public void invalidOption() {
        System.out.println("Invalid option.");
    }

    /**
     * Function that says we have to choose a team first
     */
    public void teamNotChosenYet() {
        System.out.println("Invalid option, team not chosen yet.");
    }

    /**
     * Function that indicates there was a problem in the parsing
     */
    public void parseErrorMessage() {
        System.out.println("Error in parse.");
    }

    /**
     * Function that indicates a wrong number of parameters
     */
    public void invalidParameters() {
        System.out.println("Wrong number of parameters.");
    }

    /**
     * Function that indicates the filepath didn't lead to an existing file
     */
    public void fileNotFound() {
        System.out.println("File not found.");
    }

    /**
     * Function where occurs the selection of a team from the catalog of teams
     * @param catalog Contains the teams available
     * @param bool If true then the team is generated randomly, otherwise the user may choose the team
     * @param exclude_team Team to not print in the screen
     * @return The team selected
     */
    public Map.Entry<Integer, Team> availableTeams(Map<Integer, Team> catalog, boolean bool, int exclude_team){

        if (!bool) {
            System.out.println("\n\n\n-------------------------------------------------------------------------------------\n");
            for (Map.Entry<Integer, Team> map : catalog.entrySet()) {
                if (map.getKey() != exclude_team)
                    System.out.println("Team " + map.getKey() + " " + map.getValue().getName());
            }
            if (exclude_team == -1) System.out.print("\n  -> Insert the team: ");
            else System.out.print("\n  -> Insert the team to trade with: ");
        }

        Scanner scanner = new Scanner(in);
        boolean flag = true;
        Team t = null;
        int val = -1;
        // The substitution remains in a loop until the user selects a valid team
        while (flag) {
            if (bool)
                val = (int) (Math.random() * (catalog.size())); // Generates a random key for the team
            else
                val = scanner.nextInt(); // Selects the number of the team that the user wants

            if (catalog.containsKey(val)) {
                t = catalog.get(val).clone();
                flag = false;
            }
            else
                System.out.print("Team doesn't exist. Try again.\n\n  -> Insert the team: ");

        }

        return new AbstractMap.SimpleEntry<>(val,t);
    }

    /**
     * Function that prints the team players
     * @param team Team to print
     */
    public void printTeam(Team team) {
        System.out.println("\n----------------------------------");
        System.out.println("Team " + team.getName() + "\n");
        List<Player> main_player = team.getMain_players().getMpl();
        for (Player p : main_player)
            System.out.println(Player.getStringClass(p.getClassKey()) + " - " + Player.getStringSide(p.getSide()) + ":   " + p.getName() + " " + p.getNumber());
        System.out.println();
    }

    /**
     * Generates several messages to deliver when actions happen in the game
     * @return Catalog of several possible messages
     */
    public Map<Integer, List<String>> getListActions() {
        Map<Integer, List<String>> rS = new HashMap<>();
        List<String> aux = new ArrayList<>();

        // (0) ->  perdeu a bola dentro de campo (print frase bonita)
        aux.add("Poorly executed pass and he lost the ball... ");
        aux.add("He tried his best but he forgot he's lacking talent!");
        aux.add("Excellent deflecting ... He can't even complain !!");
        aux.add("In his dreams he still has the ball but in this game... ");
        aux.add("Nice deflect !!! ");
        rS.put(0, new ArrayList<>(aux));

        // (1) ->  lançamento com perda de bola / passou pela linha de fundo / guarda redes apanhou (print frase bonita) (possiveis substituiçoes)
        aux.clear();
        aux.add("Nice ball kick but it went straight to the goalkeepers hands!!");
        aux.add("The target wasn't there but he almost hit a bird!! ");
        aux.add("He missed, but he deserves credit for this kick... ");
        aux.add("It went through the lateral line and they lost the ball!");
        aux.add("He tried to fool the defense but he lost the ball! The opponents throw the ball.");
        rS.put(1, new ArrayList<>(aux));

        // (2) ->  golo dos atacantes e bola ao centro para os outros (print frase bonita) (possiveis substituiçoes)
        aux.clear();
        aux.add("HUGE GOOOOOOOAL! GREAT GAME!");
        aux.add("And it's in theeeeeeeere, they are being underestimated! ");
        aux.add("3 to 2.... and he passes the defense .... crosses .... And it's theeeere !!!!! GOOOOOOAL ");
        aux.add("GOOOOOOOAL !!! He didn't expect a ball kick like that...");
        aux.add("Only god knows how that went it ... BUT IT'S IN !!! ");
        rS.put(2, new ArrayList<>(aux));

        // (3) ->  lançamento/canto mantendo a bola (print frase bonita) (possiveis substituiçoes)
        aux.clear();
        aux.add("And he crossed out of the camp, but at least the attack was shut down.");
        aux.add("It went through the back line, corner kick.");
        aux.add("Nice deflect from the defense!! Didn't avoid the corner kick, but it stopped the possible goal!");
        aux.add("At least he didn't lose it! Touch line throw!!");
        aux.add("He fooled the defense and won a throw!");
        rS.put(3, new ArrayList<>(aux));

        return rS;
    }

    /**
     * Functions that delivers a semi-random game action message according to a value
     * @param val Value that indicates the type of action
     * @param time Value that indicates the current game time
     * @param whoHasTheBall The team that has the ball
     */
    public void gameAction(int val, double time, String whoHasTheBall) {
        List<String> messages = this.actions.get(val);
        int message_index = (int) (Math.random() * messages.size());
        System.out.println(((int) time) + "':  " + formatString(whoHasTheBall,27) + " :   " + messages.get(message_index));
    }

    /**
     * Signalizes the end of the first half of the game
     */
    public void halfTimeMessage() {
        System.out.println("\n----------------------------------");
        System.out.println("End of first half!!");
        System.out.println("----------------------------------\n");

    }

    /**
     * Signalizes the end of the game, printing the score
     * @param score1 Goals the first team scored
     * @param score2 Goals the second team scored
     */
    public void endGame(int score1, int score2, double time, String winner) {
        System.out.println("\n----------------------------------");
        System.out.print(((int) time) + "':  End of the game!! \n\t Final score: " + score1 + " X " + score2);
        if (winner.equals("")) System.out.println("  !!! Its a draw !!!");
        else System.out.println("  !!! " + winner + " wins !!!");
        System.out.println("----------------------------------\n");
    }

    /**
     * Signalizes there was a error loading file.
     */
    public void loadErrorMessage() {
        System.out.println("Error loading file.");
    }

    /**
     * Signalizes an invalid substitution.
     */
    public void invalidSubstitution() {
        System.out.println("Invalid substitution.\n");
    }

    /**
     * Prints a message with the substitution
     * @param p1 Number of the player leaving
     * @param p2 Number of the player going in
     * @param time Value that indicates the current game time
     */
    public void substitution(int p1, int p2, double time) {
        System.out.println(p1 + " leaves. " + p2 + " gets in\n");
    }

    /**
     * Manages the substitutions, printing a helping guide to select the best and most valid substitutions possible
     * @param t Team of the substitution
     * @return Pair with the number of the player going in and the player going out
     * @throws InputMismatchException When the input wasn't an integer
     */
    public Map.Entry<Integer, Integer> menuSubstitutions(Team t) throws InputMismatchException {
        System.out.println("\n\n----------------------------------");
        System.out.println("Main players   -> (attack: " + String.format("%.1f",t.calculateAttackAbility()) + " ;  defense: " + String.format("%.1f",t.calculateDefenseAbility()) + ")\n");
        List<Player> main_players = t.getMain_players().getMpl();

        for (int i = 0;i<11;i++) {
            Player p = main_players.get(i).clone();
            System.out.print(Player.getStringClass(MainPlayersList.indexToPos(i)) + " " + Player.getStringSide(MainPlayersList.indexToSide(i)) + ":   ");
            System.out.println(Player.getStringClass(p.getClassKey()) + " " + Player.getStringSide(p.getSide()) + "   " + String.format("%.1f",p.abilityCalculus(MainPlayersList.indexToPos(i))) + "     -> " + this.formatString(Integer.toString(p.getNumber()), 4) + p.getName() );
        }

        // Creates a table with ability scores and positions where each player can play
        System.out.println("\n----------------------------------");
        System.out.println("Substitute players\n");
        System.out.println("Player                |   GK  |   DP  |   MP  |   WP  |   FP  |");
        Map<Integer, Player> subs_players = t.getSubstitutes();
        for (Map.Entry<Integer, Player> entry : subs_players.entrySet())
            System.out.println(entry.getValue().toString());

        Scanner scanner = new Scanner(in);
        System.out.print("\nInsert the number of the user to enter:");
        int in = scanner.nextInt();
        System.out.print("Insert the number of the user to leave:");
        int out = scanner.nextInt();
        System.out.println();

        return new AbstractMap.SimpleEntry<>(in, out);
    }

    /**
     * Prints the menu with the trading options
     * @param t1 Team 1
     * @param t2 Team 2
     * @return Pair with the player from team 1 and the players from the team 2 to trade
     * @throws InputMismatchException Input didn't match
     */
    public Map.Entry<Integer, Integer> menuTrading(Team t1, Team t2) throws InputMismatchException{
        System.out.println("\n-----------Team " + t1.getName() + " ----------------------");
        System.out.println("Substitute players team 1\n");
        System.out.println("Player                |   GK  |   DP  |   MP  |   WP  |   FP  |");
        Map<Integer, Player> subs_playersT1 = t1.getSubstitutes();
        for (Map.Entry<Integer, Player> entry : subs_playersT1.entrySet())
            System.out.println(entry.getValue().toString());

        int np1, np2;

        np1 = validate_number(t1.getSubstitutes());
        if (np1 == -1) return null;

        System.out.println("\n-----------Team " + t2.getName() + " ----------------------");
        System.out.println("Substitute players team 2\n");
        System.out.println("Player                |   GK  |   DP  |   MP  |   WP  |   FP  |");
        Map<Integer, Player> subs_playersT2 = t2.getSubstitutes();
        for (Map.Entry<Integer, Player> entry : subs_playersT2.entrySet())
            System.out.println(entry.getValue().toString());

        np2 = validate_number(t2.getSubstitutes());

        if (np2 == -1) return null;

        return new AbstractMap.SimpleEntry<>(np1, np2);
    }

    /**
     * Function that allows a user to choose a player, validating the choice
     * @param substitutes Map of the possibilities of substitutes
     * @return -1 if the user quits or the number of the player when valid
     * @throws NumberFormatException When parseInt fails
     */
    public int validate_number(Map<Integer,Player> substitutes) {
        Scanner scanner = new Scanner(in);
        String input;

        while (true) {
            System.out.print("Insert player number: ");
            input = scanner.nextLine();
            if (input.equals("quit"))
                return -1;
            try {
                System.out.println(input);
                int value = Integer.parseInt(input);
                if (substitutes.containsKey(value))
                    return value;
                else
                    System.out.println("Error in input");
            }
            catch (NumberFormatException e){
                System.out.println("Error in input!!");
            }
        }
    }

    /**
     * Function that clears the screen
     * @param string input String
     * @param formatSize size for the output String
     * @return String with length equal to or greater than the one provided
     */
    public String formatString(String string, int formatSize) {
        StringBuilder s = new StringBuilder();

        s.append(string);

        for (int i = 0; string.length() + i < formatSize; i++)
            s.append(" ");

        return s.toString();
    }

    /**
     * Function that prints the game hitory
     * @param gameHistoryList list of all games played so far
     */
    public void gameHistory(List<GameHistory> gameHistoryList){
        System.out.println("\n Game History:");
        System.out.println("\n---------------------------------------------------------------------------");
        for(GameHistory gh : gameHistoryList){
            System.out.println(gh.toString());
            System.out.println("\n---------------------------------------------------------------------------");
        }
    }

    /**
     * Generates a menu to choose a type of random team
     * @param b If true show menu
     * @return Int
     */
    public int generateRandTeamMenu(boolean b){
        if(b){
            System.out.println("\n----------------------------------");
            System.out.println("\n Random Team:");
            System.out.println("    * 1 - Customize a team");
            System.out.println("    * 2 - TomTeam");
            System.out.println("    * 0 - Quit");
        }
        Scanner scanner = new Scanner(in);
        return scanner.nextInt();
    }

    /**
     * Allows the user to name a player of a team
     * @param bool If true user names a player, if false names a team
     * @return Name
     */
    public String infoGetName(boolean bool){
        Scanner scanner = new Scanner(in);
        if (bool) System.out.print("Insert player name: \n\t ->  ");
        else System.out.print("Insert team name: \n\t ->   ");
        return scanner.nextLine();
    }
}
