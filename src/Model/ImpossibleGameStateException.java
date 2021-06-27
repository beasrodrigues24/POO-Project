package Model;

public class ImpossibleGameStateException extends Exception {

    public ImpossibleGameStateException(){
        super();
    }

    public ImpossibleGameStateException(String s){
        super(s);
    }
}
