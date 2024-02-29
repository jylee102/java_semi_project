package semi02.project.cafe.receipt;

import semi02.project.application.Main;
import semi02.project.product.*;

import java.util.*;

public class GenerateReceipt {
    private StringBuffer buffer = new StringBuffer();

    public static final String LINE = "\n----------------------------------";


    public String getReceipt(ArrayList<Product> products) {
        makeHeader();
        makeBody(products);
        makeFooter();
        return buffer.toString();
    }

    public void makeHeader() {
        buffer.append(LINE)
                .append("\n장바구니 목록")
                .append(LINE);
    }

    public void makeBody(ArrayList<Product> products) {
        ArrayList<Product> productList = restructureList(products);

        for (Product product : productList) {
            buffer.append("\n").append((productList.indexOf(product) + 1)).append("] ")
                    .append(product.getName())
                    .append(" * ").append(Collections.frequency(products, product))
                    .append(" | ").append(product.getPrice() * Collections.frequency(products, product)).append("원");
        }
        buffer.append("\n\n합계: ").append(Main.calcPrice(products)).append("원");
        buffer.append(LINE);
    }

    public void makeFooter() {
        buffer.append("\n1.결제하기 | 2.메뉴 더 보기 | 3.장바구니에서 물품 삭제 | 4.주문 취소");
    }

    public ArrayList<Product> restructureList(ArrayList<Product> products) {
        // 중복 요소 제거하여 product 리스트 재구성(상품이름 * 개수 형태)

        ArrayList<Product> productList = new ArrayList<>();

        for (Product product : products) {
            if (!productList.contains(product)) {
                productList.add(product);
            }
        }
        return productList;
    }
}
