package semi02.project.cafe.generateString;

import semi02.project.application.Main;
import semi02.project.product.*;
import semi02.project.utils.Define;

import java.util.*;

public class GenerateCartList {
    private StringBuffer buffer = new StringBuffer();

    public String getReceipt(ArrayList<Product> products) {
        makeHeader();
        makeBody(products);
        makeFooter();
        return buffer.toString();
    }

    private void makeHeader() {
        buffer.append(Define.BOLD_LINE).append("장바구니 목록");
    }

    private void makeBody(ArrayList<Product> products) {
        ArrayList<Product> productList = restructureList(products);

        // (i)] (상품) * (개수) | (가격)원
        for (Product product : productList) {
            buffer.append(Define.CHANGE_LINE)
                    .append((productList.indexOf(product) + 1)).append("] ")
                    .append(product.getFullName())
                    .append(" * ").append(Collections.frequency(products, product))
                    .append(Define.DEVIDER).append(product.getPrice() * Collections.frequency(products, product)).append("원");
        }

        buffer.append(Define.CHANGE_LINE + Define.CHANGE_LINE);
        buffer.append("합계: ").append(Main.calcPrice(products)).append("원").append(Define.CHANGE_LINE);
    }

    private void makeFooter() {
        buffer.append(Define.CHANGE_LINE)
                .append("1.결제하기").append(Define.DEVIDER)
                .append("2.메뉴 더 보기").append(Define.DEVIDER)
                .append("3.장바구니에서 물품 삭제").append(Define.DEVIDER)
                .append("4.주문 취소").append(Define.CHANGE_LINE);
    }

    private ArrayList<Product> restructureList(ArrayList<Product> products) {
        // 중복 요소 제거하여 product 리스트 재구성(상품이름 * 개수 형태로 만들기 위해서)

        ArrayList<Product> productList = new ArrayList<>();

        for (Product product : products) {
            if (!productList.contains(product)) {
                productList.add(product);
            }
        }
        return productList;
    }
}
