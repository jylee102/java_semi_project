package semi02.project.cafe;

import semi02.project.cafe.generateString.GenerateMenu;
import semi02.project.machine.Machine;
import semi02.project.product.*;

import java.util.*;

public class Cafe {
    private static Cafe instance = null;

    private final ArrayList<Staff> staffList = new ArrayList<>();
    private final Map<String, Machine[]> machineList = new HashMap<>();

    // 손님이 보는 주문 리스트. 직원이 일을 완료하고 상품을 내어줄 때 요소 삭제
    private final ArrayList<Order> orderList = new ArrayList<>();

    // 직원들이 보는 주문 리스트(준비 중인 오더 삭제해야 다른 직원이 그 일을 하려 하지 않으므로)
    // 직원이 해당 주문에 대하여 일하기 시작할 때 요소 삭제
    private final ArrayList<Order> checkList = new ArrayList<>();

    private final ArrayList<Product> coffeeMenus = new ArrayList<>();
    private final ArrayList<Product> beverageMenus = new ArrayList<>();
    private final ArrayList<Product> snackMenus = new ArrayList<>();

    private final ArrayList<ArrayList<ArrayList<Product>>> menuList = new ArrayList<>();

    private final ArrayList<ArrayList<String>> nameLists = new ArrayList<>(); // 메뉴 이름 중복 제거

    // 장바구니
    private ArrayList<Product> cartProducts = new ArrayList<>();
    // 사용자에게 보이는 중복 제거된 리스트의 인덱스와 제거/변경할 아이템의 인덱스 번호를 일치시키기 위해
    private ArrayList<Product> organizedCart = new ArrayList<>();

    // 고객의 입력값에 따라 출력되는 문자열 모음
    private String menuInfoList;
    private String[] menuInfoList1;
    private String[][] menuInfoList2;

    private int sales = 0; // 매출

    private Cafe() {
    }

    public static Cafe getInstance() {
        if (instance == null) {
            instance = new Cafe();
        }
        return instance;
    }

    /* 초기 세팅 관련 */
    public void addStaff(Staff[] staffList) {
        this.staffList.addAll(List.of(staffList));
    }

    public ArrayList<Staff> getStaffList() {
        return staffList;
    }

    public void installMachines(Machine[] coffeeMachine, Machine[] blender, Machine[] microwave) {
        this.machineList.put("CoffeeMachine", coffeeMachine);
        this.machineList.put("Blender", blender);
        this.machineList.put("Microwave", microwave);
    }

    public Map<String, Machine[]> getMachineList() {
        return machineList;
    }

    /* 주문 리스트 관련 */
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

    /* 장바구니 관련 */
    public ArrayList<Product> getCartProducts() {
        return cartProducts;
    }

    public ArrayList<Product> getOrganizedCart() {
        return organizedCart;
    }

    public void setOrganizedCart() {
        this.organizedCart = restructureList(cartProducts);
    }

    public void putInACart(Product product) {
        cartProducts.add(product);
    }

    public void removeCartProducts(Product target) {
        ArrayList<Product> cartItems = new ArrayList<>();

        for (Product cartProduct : cartProducts) {
            if (!cartProduct.equals(target)) cartItems.add(cartProduct);
        }

        cartProducts = cartItems;
    }

    public void clearCart() {
        cartProducts.clear();
    }

    public void changeNumOfItem(Product product, int num) {
        removeCartProducts(product);

        for (int i = 0; i < num; i++) {
            cartProducts.add(product);
        }
    }

    /* 총 판매액 */
    public int getSales() {
        return sales;
    }

    public void increaseSales(int sales) {
        this.sales += sales;
    }

    /* 메뉴 리스트 관련 */
    public ArrayList<ArrayList<ArrayList<Product>>> getMenuList() {
        return menuList;
    }

    public ArrayList<ArrayList<String>> getNameLists() {
        return nameLists;
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

    public ArrayList<Product> getSnackMenus() {
        return snackMenus;
    }

    public void makeMenuList() {
        nameLists.add(mergeSameName(coffeeMenus));
        nameLists.add(mergeSameName(beverageMenus));
        nameLists.add(mergeSameName(snackMenus));

        classfyByName(coffeeMenus);
        classfyByName(beverageMenus);
        classfyByName(snackMenus);

        int maxSize = nameLists.get(0).size();
        for (ArrayList<String> nameList : nameLists) {
            if (nameList.size() > maxSize) maxSize = nameList.size();
        }

        menuInfoList1 = new String[menuList.size()];
        menuInfoList2 = new String[menuList.size()][maxSize];
        generateMenuInfo();
    }

    /* 메뉴 안내 출력값 관련 */
    public void generateMenuInfo() {
        GenerateMenu generateMenu = new GenerateMenu();

        menuInfoList = generateMenu.getMenu();
        for (int i = 0; i < menuList.size(); i++) {
            menuInfoList1[i] = generateMenu.getMenu(i);
            for (int j = 0; j < menuList.get(i).size(); j++) {
                menuInfoList2[i][j] = generateMenu.getMenu(i, j);
            }
        }
    }

    public void printMenuInfo() {
        System.out.print(menuInfoList);
    }

    public void printMenuInfo(int i) {
        System.out.print(menuInfoList1[i]);
    }

    public void printMenuInfo(int i, int j) {
        System.out.print(menuInfoList2[i][j]);
    }

    /* 기타 */
    // 메뉴 이름으로 제품 분류
    private void classfyByName(ArrayList<Product> products) {
        ArrayList<String> menuNameList = mergeSameName(products);

        ArrayList<ArrayList<Product>> menus = new ArrayList<>();
        for (String s : menuNameList) {
            ArrayList<Product> productList = new ArrayList<>();
            for (Product product : products) {
                if (s.equals(product.getName())) {
                    productList.add(product); // ex) 핫과 아이스 메뉴
                }
            }
            menus.add(productList); // ex) 아메리카노, 카페라떼, ...
        }
        menuList.add(menus); // ex) 커피
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


    public static ArrayList<Product> restructureList(ArrayList<Product> products) {

        // 중복 요소 제거하여 리스트 재구성
        ArrayList<Product> productList = new ArrayList<>();
        for (Product product : products) {
            if (!productList.contains(product)) {
                productList.add(product);
            }
        }

        // ArrayList 안의 객체 빈도수가 많은 순으로 정렬
        Comparator<Product> productFrequencyComparator =
                Comparator.comparingInt(p -> Collections.frequency(products, p));
        productList.sort(productFrequencyComparator.reversed());

        return productList;
    }
}
