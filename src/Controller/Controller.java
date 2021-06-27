package Controller;

import java.io.IOException;
import java.util.*;
import View.*;
import Model.*;

import static java.lang.System.in;

public class Controller implements ControllerInterface{

    /**
     * Fields
     */
    private ViewInterface view;
    private GameInterface game_int;
    private Map<Integer, Team> teams_catalog;

    /**
     * Parametrized constructor
     * @param view View Interface
     * @param game Game Interface
     */

    public Controller(ViewInterface view, GameInterface game) {
        this.view = view;
        this.game_int = game;
    }

    /**
     * Generates a filepath to save to or load a state from
     * @return Filepath
     */
    public String getFilepath() {
        String filepath = null;
        while (filepath == null) {
            try {
                int input = this.view.statesAvailable();
                switch (input) {
                    case 1 -> filepath = "savespaces/savespace1.dat";
                    case 2 -> filepath = "savespaces/savespace2.dat";
                    case 3 -> filepath = "savespaces/savespace3.dat";
                    case 0 -> filepath = "0";
                    default -> this.view.invalidOption();
                }
            } catch (InputMismatchException e) {
                this.view.invalidOption();
            }
        }
        return filepath;
    }

    /**
     * Saves a given status to file
     * @param game_data the state to be saved
     */
    public void storeGame (Map.Entry<Map<String, Team>, Map.Entry<List<GameHistory>, Game>> game_data){
        String filepath = getFilepath(); // Gets filepath to store the game state to
        if (!filepath.equals("0")){
            try {
                LoadStore.storeState(filepath, game_data);
            } catch (NullPointerException | IOException e) {
                this.view.fileNotFound();
            }
        }
    }

    /**
     * Creates a catalog that connects an integer to a team
     * @param teams HashMap with teams, where the key is the team name
     */
    public void createsCatalog(Map<String,Team> teams) {
        Map<Integer, Team> catalog = new HashMap<>();
        int i = 0;
        for (Map.Entry<String,Team> entry : teams.entrySet())
            catalog.put(i++, entry.getValue());
        this.teams_catalog = catalog;
    }

    /**
     * Function that runs the game
     */
    public void run() {
        boolean flag, loadFlag = false, quitFlag = false;

        String filepath;
        // t1 is always the player's team
        Team t1 = null, t2;

        // Attempts to read game data from file "logs.txt"
        Map.Entry<Map<String, Team>, Map.Entry< List<GameHistory>, Game>> game_data = null;
        try {
            Map.Entry<Map<String, Team>, List<GameHistory>> auxParser = Parser.parse("logs.txt");
            game_data = new AbstractMap.SimpleEntry<>(auxParser.getKey(), new AbstractMap.SimpleEntry<>(auxParser.getValue(), null));
            flag = true;
        }
        catch (WrongLineException | DuplicatedPlayerException | ImpossibleGameStateException e) {
            this.view.parseErrorMessage();
            flag = false;
        }

        Scanner scanner = new Scanner(in);
        int input;

        // If logs.txt was successfully read and while the user doesn't quit the game, the loop occurs
        while (flag) {

            if (!loadFlag)
                if (t1 != null)
                    input = this.view.initialPrompt(t1.getName());
                else
                    input = this.view.initialPrompt(null);
            else
                input = 2;

            switch (input) {
                case 1 -> {
                    if (this.teams_catalog == null) createsCatalog(game_data.getKey());
                    t1 = this.view.availableTeams(this.teams_catalog, false, -1).getValue();
                }
                case 2 -> {
                    if (t1 != null){
                        // Generates the second team and the game

                        if (!loadFlag) {
                            do t2 = this.view.availableTeams(this.teams_catalog, true, -1).getValue();
                            while (t2.equals(t1));
                            this.game_int = new Game(t1, t2);
                        }
                        else {
                            t2 = this.game_int.getTeam2();
                            loadFlag = false;
                        }

                        // Prints the teams and signalizes the start of the game
                        this.view.printTeam(t1);
                        this.view.printTeam(t2);
                        this.view.gamePrompt();

                        int val;
                        // Passage of time of the game until the 90 min mark is surpassed
                        while ((val = this.game_int.step()) < 90 && !quitFlag) {

                            if (val >= 45 && !this.game_int.isFirstHalf()) // Ends the first half of the game
                                this.view.halfTimeMessage();
                            else {
                                // Executes a game action
                                if (!this.game_int.isAttackerTeam())
                                    this.view.gameAction(val, this.game_int.getTime(), t1.getName());
                                else
                                    this.view.gameAction(val, this.game_int.getTime(), t2.getName());

                                if (val == 2) // If there was a goal, prints the new score
                                    this.view.printScore(this.game_int.getScoreTeam1(), this.game_int.getScoreTeam2());

                                if (val == 1)
                                    System.out.println();
                                else {
                                    // Reads a line and if it corresponded to "s" then the user requested a substitution
                                    String input_line = scanner.nextLine();
                                    switch (input_line) {
                                        case "sub" -> {
                                            try { // Executes the substitution on the user team
                                                Map.Entry<Integer, Integer> subst = this.view.menuSubstitutions(t1); // Returns a par with the one who's in and the one who's out

                                                if (this.game_int.getTeam1().equals(t1))
                                                    this.game_int.sub(1, subst.getKey(), subst.getValue());
                                                else
                                                    this.game_int.sub(2, subst.getKey(), subst.getValue());

                                                t1 = this.game_int.getTeam1().clone();
                                                this.view.substitution(subst.getValue(), subst.getKey(), this.game_int.getTime());
                                            } catch (NullPointerException | DuplicatedPlayerException | InputMismatchException e) {
                                                this.view.invalidSubstitution();
                                            }
                                        }
                                        case "save" -> {
                                            Map.Entry<Map<String, Team>, Map.Entry< List<GameHistory>, Game>> auxSaveGame;
                                            auxSaveGame = new AbstractMap.SimpleEntry<>(game_data.getKey(), new AbstractMap.SimpleEntry<>(game_data.getValue().getKey(),this.game_int.getGame()));
                                            storeGame(auxSaveGame);
                                        }
                                        case "quit" -> quitFlag = true;
                                    }
                                }
                            }
                        }

                        if (!quitFlag){
                            if (this.game_int.getScoreTeam1() > this.game_int.getScoreTeam2())
                                this.view.endGame(this.game_int.getScoreTeam1(), this.game_int.getScoreTeam2(), this.game_int.getTime(), t1.getName());
                            else if (this.game_int.getScoreTeam1() < this.game_int.getScoreTeam2())
                                this.view.endGame(this.game_int.getScoreTeam1(), this.game_int.getScoreTeam2(), this.game_int.getTime(), t2.getName());
                            else
                                this.view.endGame(this.game_int.getScoreTeam1(), this.game_int.getScoreTeam2(), this.game_int.getTime(), "");

                            game_data.getValue().getKey().add(this.game_int.gameHistory());
                        } else
                            quitFlag = false;
                    } else
                        this.view.teamNotChosenYet();
                }
                case 3 -> {
                    filepath = getFilepath(); // Gets filepath to load the game state from
                    if (!filepath.equals("0")){
                        try {
                            game_data = LoadStore.loadState(filepath);
                            if (game_data.getValue().getValue() != null){
                                this.game_int = game_data.getValue().getValue().clone();
                                t1 = this.game_int.getTeam1();
                                loadFlag = true;
                            }
                            // Indicates the load was done
                            this.view.loadPrompt();
                        } catch (IOException | ClassNotFoundException e) {
                            this.view.loadErrorMessage();
                        }
                    }
                }
                case 4 -> storeGame(game_data);
                case 5 -> {
                    if (t1 == null) t1 = new Team();
                    int option = -1;
                    do {
                        option = this.view.generateRandTeamMenu(option == -1);
                        switch (option) {
                            case 1 -> {
                                List<String> newPlayersNames = new ArrayList<>();
                                t1.generateTomTeam(this.view.infoGetName(false));
                                for (int i = 0; i < 20; i++)
                                    newPlayersNames.add(this.view.infoGetName(true));
                                t1.setPlayersNames(newPlayersNames);
                                option = 0;
                            }
                            case 2 -> {
                                t1.generateTomTeam("Tom F. C.");
                                option = 0;
                            }
                        }
                    }while (option != 0);
                    if (this.teams_catalog == null) createsCatalog(game_data.getKey());
                    this.teams_catalog.put(this.teams_catalog.size(), t1);
                }
                case 6 -> {
                    if (t1 != null){
                        Map.Entry<Integer, Team> auxTeamTrading;
                        int idTeam1 = -1;
                        for (Map.Entry<Integer, Team> e : this.teams_catalog.entrySet())
                            if (t1.equals(e.getValue()))
                                idTeam1 = e.getKey();
                        auxTeamTrading = this.view.availableTeams(this.teams_catalog,false, idTeam1);
                        Map.Entry<Integer, Integer> trade = this.view.menuTrading(t1, auxTeamTrading.getValue());
                        if (trade != null)
                            Team.switchPlayers(this.teams_catalog, idTeam1, auxTeamTrading.getKey(), trade.getKey(), trade.getValue());
                    } else
                        this.view.teamNotChosenYet();
                }
                case 7 -> this.view.gameHistory(game_data.getValue().getKey());
                case 0 -> {
                    flag = false;
                    this.view.quitMessage();
                }
                default -> this.view.invalidOption();
            }
        }
        scanner.close();
    }


}
