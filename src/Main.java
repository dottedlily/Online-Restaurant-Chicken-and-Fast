import main.java.main.controller.CartModel;
import main.java.main.view.MainFrame;


public class Main {

    public static void main(String[] args) {
        CartModel cartModel = new CartModel();
        MainFrame mainFrame = new MainFrame(cartModel);
        mainFrame.setVisible(true);

    }
}