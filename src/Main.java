import Model.*;
import Controller.*;
import View.*;

public class Main {

    /**
     * Main function of the program
     * @param args arguments provided to the program
     */
    public static void main(String[] args) {

        ViewInterface view = new View();
        ControllerInterface controller = new Controller(view, null);
        controller.run();

    }
}
