package semi02.project.callback;

import semi02.project.cafe.Cafe;
import semi02.project.cafe.generateString.GenerateMenu;
import semi02.project.product.Product;

import java.util.ArrayList;
import java.util.Scanner;

public interface Command {
    Scanner scanner = new Scanner(System.in);

    Cafe myCafe = Cafe.getInstance();
    ArrayList<ArrayList<ArrayList<Product>>> menuList = myCafe.getMenuList();
    
    GenerateMenu menu = new GenerateMenu();

    public void execute(int[] idxs);
}
