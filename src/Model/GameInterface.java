package Model;

public interface GameInterface {
    double getTime();
    Team getTeam1();
    Team getTeam2();
    int getScoreTeam1();
    int getScoreTeam2();
    int step();
    boolean isFirstHalf();
    boolean isAttackerTeam();
    void sub(int teamNum, int indexIn, int indexOut) throws DuplicatedPlayerException, NullPointerException;
    GameHistory gameHistory();
    Game getGame();
}
