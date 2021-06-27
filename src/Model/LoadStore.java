package Model;

import java.io.*;
import java.util.List;
import java.util.Map;

public class LoadStore {

    /**
     * Reads a game state from a file
     * @param filepath Filepath to the file to load from
     * @return A pair with the teams and the game history
     * @throws IOException If there was a problem reading the file
     * @throws ClassNotFoundException If class not found
     */
    public static Map.Entry<Map<String, Team>, Map.Entry< List<GameHistory>, Game>> loadState(String filepath) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));
        Map.Entry<Map<String, Team>, Map.Entry< List<GameHistory>, Game>> game_data =  (Map.Entry<Map<String, Team>, Map.Entry< List<GameHistory>, Game>>) ois.readObject();
        ois.close();
        return game_data;
    }

    /**
     * Saves a game state to a file
     * @param filepath Filepath to the file to load to
     * @param obj Object to write in the file
     * @throws IOException If there was a problem writing in the file
     */
    public static void storeState(String filepath, Map.Entry<Map<String, Team>, Map.Entry< List<GameHistory>, Game>> obj) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
        oos.writeObject(obj);
        oos.flush();
        oos.close();
    }
}



