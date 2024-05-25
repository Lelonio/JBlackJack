import Model.BlackJackModel;
import View.BlackJackView;
import Controller.BlackJackController;


public class Main {
    public static void main(String[] args){

        BlackJackModel model = new BlackJackModel();
        BlackJackView view = new BlackJackView();
        BlackJackController controller = new BlackJackController(view,model);

        controller.startGame();
    }
}
