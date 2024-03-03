package semi02.project.cafe;

import semi02.project.product.*;

import java.util.*;

public class Cafe {
    private static Cafe instance = null;

    private final ArrayList<Staff> staffList = new ArrayList<>();

    // 손님이 보는 주문 리스트. 직원이 일을 완료하고 상품을 내어줄 때 요소 삭제
    private final ArrayList<Order> orderList = new ArrayList<>();

    // 직원들이 보는 주문 리스트(준비 중인 오더 삭제해야 다른 직원이 그 일을 하려 하지 않으므로)
    // 직원이 해당 주문에 대하여 일하기 시작할 때 요소 삭제
    private final ArrayList<Order> checkList = new ArrayList<>();

    private final ArrayList<Product> coffeeMenus = new ArrayList<>();
    private final ArrayList<Product> beverageMenus = new ArrayList<>();
    private final ArrayList<Product> snackMenus = new ArrayList<>();

    private final ArrayList<ArrayList<ArrayList<Product>>> menuList = new ArrayList<>();

    // 메뉴 이름 중복 제거
    private final ArrayList<ArrayList<String>> nameLists = new ArrayList<>();

    private final ArrayList<Product> cartProducts = new ArrayList<>(); // 장바구니

    // 매출
    private int sales = 0;

    private Cafe() {
    }

    public static Cafe getInstance() {
        if (instance == null) {
            instance = new Cafe();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orderList.add(order);
        checkList.add(order);
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void removeOrder(Order order) {
        orderList.remove(order);
    }

    public ArrayList<Order> getCheckList() {
        return checkList;
    }

    public void addStaff(Staff[] staffList) {
        this.staffList.addAll(List.of(staffList));
    }

    public ArrayList<Staff> getStaffList() {
        return staffList;
    }

    public ArrayList<ArrayList<String>> getNameLists() {
        return nameLists;
    }

    public ArrayList<Product> getCartProducts() {
        return cartProducts;
    }

    public void putInACart(Product product) {
        cartProducts.add(product);
    }

    public void clearCart() {
        cartProducts.clear();
    }

    public int getSales() {
        return sales;
    }

    public void increaseSales(int sales) {
        this.sales += sales;
    }

    public void addCoffeeMenu(Coffee coffee) {
        coffeeMenus.add(coffee);
    }

    public void addBeverageMenu(Beverage beverage) {
        beverageMenus.add(beverage);
    }

    public void addSnackMenu(Snack snack) {
        snackMenus.add(snack);
    }

    public void makeMenuList() {
        nameLists.add(mergeSameName(coffeeMenus));
        nameLists.add(mergeSameName(beverageMenus));
        nameLists.add(mergeSameName(snackMenus));

        classfyByName(coffeeMenus);
        classfyByName(beverageMenus);
        classfyByName(snackMenus);
    }


    public ArrayList<ArrayList<ArrayList<Product>>> getMenuList() {
        return menuList;
    }

    // 메뉴 이름으로 제품 분류
    private void classfyByName(ArrayList<Product> products) {
        ArrayList<String> menuNameList = mergeSameName(products);

        ArrayList<ArrayList<Product>> menus = new ArrayList<>();
        for (String s : menuNameList) {
            ArrayList<Product> productList = new ArrayList<>();
            for (Product product : products) {
                if (s.equals(product.getName())) {
                    productList.add(product); // 핫과 아이스 메뉴
                }
            }
            menus.add(productList); // 아메리카노, 카페라떼, ...
        }
        menuList.add(menus); // 커피
    }

    private ArrayList<String> mergeSameName(ArrayList<Product> products) {

        ArrayList<String> productNameList = new ArrayList<>();
        for (Product product : products) {
            productNameList.add(product.getName());
        }

        ArrayList<String> menuNameList = new ArrayList<>();
        for (String name : productNameList) {
            if (!menuNameList.contains(name)) menuNameList.add(name);
        }

        return menuNameList;
    }
}
