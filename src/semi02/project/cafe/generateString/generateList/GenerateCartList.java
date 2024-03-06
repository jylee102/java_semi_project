package semi02.project.cafe.generateString.generateList;

import semi02.project.application.Main;
import semi02.project.cafe.Cafe;
import semi02.project.product.*;
import semi02.project.utils.Define;

import java.util.*;

public class GenerateCartList {
    Cafe myCafe = Cafe.getInstance();

    public String getList() {
        StringBuffer buffer = new StringBuffer();

        ArrayList<Product> products = myCafe.getCartProducts();
        ArrayList<Product> productList = myCafe.getOrganizedCart();

        buffer.append(Define.BOLD_LINE).append("장바구니 목록").append(Define.CHANGE_LINE);

        // (i)] (상품) * (개수) | (가격)원
        for (Product product : productList) {
            buffer.append(Define.CHANGE_LINE)
                    .append((productList.indexOf(product) + 1)).append("] ")
                    .append(product).append(" * ").append(Collections.frequency(products, product))
                    .append(Define.DEVIDER).append(product.getPrice() * Collections.frequency(products, product)).append("원");
        }

        buffer.append(Define.CHANGE_LINE + Define.CHANGE_LINE);
        buffer.append("합계: ").append(Main.calcPrice(products)).append("원").append(Define.CHANGE_LINE);

        buffer.append(Define.CHANGE_LINE)
                .append("1.결제하기").append(Define.DEVIDER)
                .append("2.메뉴 더 보기").append(Define.DEVIDER)
                .append("3.장바구니에서 물품 삭제").append(Define.DEVIDER)
                .append("4.물품 수량 변경").append(Define.DEVIDER)
                .append("5.주문 포기").append(Define.CHANGE_LINE);

        return buffer.toString();
    }
}
