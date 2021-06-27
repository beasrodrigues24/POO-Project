package View;

import java.util.List;
import java.util.Map;
import java.util.InputMismatchException;
import Model.*;

public interface ViewInterface {
    int initialPrompt(String team_name);
    void gamePrompt();
    void printScore(int team_1, int team_2);
    void loadPrompt();
    int statesAvailable() throws InputMismatchException;
    void quitMessage();
    void invalidOption();
    void parseErrorMessage();
    void invalidParameters();
    void SavePrompt();
    void fileNotFound();
    Map.Entry<Integer, Team> availableTeams(Map<Integer, Team> catalog, boolean bool, int exclude_team);
    void printTeam(Team team);
    void gameAction(int val, double time, String whoHasTheBall);
    Map<Integer, List<String>> getListActions();
    void halfTimeMessage();
    void endGame(int score_team_1, int score_team_2, double time, String winner);
    void loadErrorMessage();
    void invalidSubstitution();
    void substitution(int p1, int p2, double time);
    Map.Entry<Integer, Integer> menuSubstitutions(Team t) throws InputMismatchException;
    Map.Entry<Integer, Integer> menuTrading(Team t1, Team t2) throws InputMismatchException;
    String formatString(String string, int formatSize);
    void gameHistory(List<GameHistory> gameHistoryList);
    void teamNotChosenYet();
    int generateRandTeamMenu(boolean showMenu);
    String infoGetName(boolean bool);
}
