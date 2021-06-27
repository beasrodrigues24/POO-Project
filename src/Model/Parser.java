package Model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Parser {

    /**
     * Parses a file to a game state
     * @param filepath Path to the file
     * @return A game state with the teams and the game history
     * @throws WrongLineException Wrong line
     * @throws DuplicatedPlayerException When the player occurs more than once
     * @throws ImpossibleGameStateException When the game state isn't possible
     */
    public static Map.Entry<Map<String, Team>, List<GameHistory>> parse(String filepath) throws WrongLineException, DuplicatedPlayerException, ImpossibleGameStateException {

        List<String> lines = readFile(filepath);
        Map<String, Team> teams = new HashMap<>(); // Will associate team name and team
        List <Player> players = new ArrayList<>();
        List <GameHistory> gameHistory = new ArrayList<>();
        Team last = null;
        Player j;
        String[] startingLine;

        for (String line : lines) {

            // Parses to an array of Strings
            startingLine = line.split(":", 2);

            switch(startingLine[0]){
                case "Equipa":
                    // Creates team
                    if (last != null) {
                        last.setPlayers(players);
                        players.clear();
                    }
                    Team e = Team.parse(startingLine[1]);
                    teams.put(e.getName(), e);
                    last = e;
                    break;
                case "Guarda-Redes":
                    // Parses goal keeper and adds him to a list of players
                    j = GoalKeeper.parse(startingLine[1]);
                    players.add(j);
                    if (last == null) throw new WrongLineException(); //we need to insert the player into the team
                    break;
                case "Defesa":
                    // Parses defense player and adds him to a list of players
                    j = DefensePlayer.parse(startingLine[1]);
                    players.add(j);
                    if (last == null) throw new WrongLineException(); //we need to insert the player into the team
                    break;
                case "Medio":
                    // Parses middle player and adds him to a list of players
                    j = MiddlePlayer.parse(startingLine[1]);
                    players.add(j);
                    if (last == null) throw new WrongLineException(); //we need to insert the player into the team
                    break;
                case "Lateral":
                    // Parses winger player and adds him to a list of players
                    j = WingerPlayer.parse(startingLine[1]);
                    players.add(j);
                    if (last == null) throw new WrongLineException(); //we need to insert the player into the team
                    break;
                case "Avancado":
                    // Parses front player and adds him to a list of players
                    j = FrontPlayer.parse(startingLine[1]);
                    players.add(j);
                    if (last == null) throw new WrongLineException(); //we need to insert the player into the team
                    break;
                case "Jogo":
                    // Parses game history
                    if (last != null) {
                        last.setPlayers(players);
                        last = null;
                        players.clear();
                    }
                    GameHistory jo = GameHistory.parse(startingLine[1]);
                    gameHistory.add(jo);
                    break;
                default:
                    throw new WrongLineException();
            }
        }
        return new AbstractMap.SimpleEntry<>(teams, gameHistory);
    }

    /**
     * Reads file to a list of strings
     * @param filepath Path to the file to read from
     * @return List with the strings read
     */
    public static List<String> readFile(String filepath) {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(filepath), StandardCharsets.UTF_8); }
        catch(IOException exc) { lines = new ArrayList<>(); }
        return lines;
    }
}
