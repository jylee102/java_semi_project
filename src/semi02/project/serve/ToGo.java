package semi02.project.serve;

import semi02.project.product.Product;

import java.util.ArrayList;

public class ToGo implements Take {
    @Override
    public String putIn(ArrayList<Product> products) {
        return "종이컵";
    }
}
