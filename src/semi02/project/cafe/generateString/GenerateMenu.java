package semi02.project.cafe.generateString;

import semi02.project.cafe.Cafe;
import semi02.project.product.Product;
import semi02.project.utils.Define;

import java.util.ArrayList;

public class GenerateMenu {
    ArrayList<ArrayList<ArrayList<Product>>> menuList = Cafe.getInstance().getMenuList();

    public String getMenu(int i) {
        StringBuffer buffer = new StringBuffer();

        ArrayList<ArrayList<Product>> category = menuList.get(i); // 커피, 논커피, 스낵
        ArrayList<String> menuNameList = Cafe.getInstance().getNameLists().get(i);

        buffer.append(Define.LINE);
        buffer.append("0.처음 주문 화면으로").append(Define.CHANGE_LINE);

        for (String menuName : menuNameList) {
            StringBuilder option = new StringBuilder(); // 구분되는 메뉴 특성(HOT/ICED 또는 flavor)
            StringBuilder prices = new StringBuilder(); // HOT/ICED 또는 맛별로 가격이 다른 경우를 고려하기 위해

            buffer.append(menuNameList.indexOf(menuName) + 1).append(".").append(menuName);

            option.append("(");
            for (ArrayList<Product> menu : category) {
                for (Product product : menu) {
                    if (menuName.equals(product.getName())) {
                        // HOT/ICED 기준으로 나눌지, 맛별로 나눌지
                        if (product.getFlavor().isEmpty()) {
                            option.append("/");
                            option.append(product.isHot() ? "HOT" : "ICED");
                        } else {
                            option.append(product.getFlavor());
                            option.append("/");
                        }

                        prices.append(product.getPrice()).append("원/");
                    }
                }
            }
            buffer.append(option.toString().replace("(/", "("));
            buffer.append(")");

            prices.append("/");
            String priceResult = allSameArr(prices.toString());
            buffer.append(Define.DEVIDER)
                    .append(priceResult);
            buffer.append(Define.CHANGE_LINE);
        }
        buffer.append(Define.CHANGE_LINE).append(Define.INPUT_SPACE);
        return buffer.toString();
    }

    public String getMenu(int i, int j) {
        StringBuffer buffer = new StringBuffer();

        ArrayList<Product> selectedMenu = menuList.get(i).get(j);

        buffer.append(Define.LINE);
        buffer.append("0.처음 주문 화면으로").append(Define.CHANGE_LINE);

        for (Product product : selectedMenu) {
            buffer.append((selectedMenu.indexOf(product) + 1)).append(".")
                    .append(product.getFullName()).append(Define.DEVIDER)
                    .append(product.getPrice()).append("원").append(Define.CHANGE_LINE);
        }
        buffer.append(Define.CHANGE_LINE).append(Define.INPUT_SPACE);
        return buffer.toString();
    }

    // 매개변수로 들어가는 배열의 값이 모두 같은지 확인하고 사용할 price값 리턴
    private String allSameArr(String str) {
        String prices = str.replace("//", "");
        String[] arr = prices.split("/");
        ArrayList<String> strArr = new ArrayList<>();

        for (String price : arr) {
            if (!strArr.contains(price)) strArr.add(price);
        }
        if (strArr.size() == 1) return strArr.get(0); // 모두 동일한 값이면 한 번만 출력
        else return prices;
    }
}
