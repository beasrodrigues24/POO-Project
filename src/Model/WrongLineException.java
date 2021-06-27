package Model;

public class WrongLineException extends Exception {

    public WrongLineException(){
        super();
    }

    public WrongLineException(String s){
        super(s);
    }
}