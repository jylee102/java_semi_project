package semi02.project.cafe;

import semi02.project.product.Beverage;
import semi02.project.product.Coffee;
import semi02.project.product.Product;
import semi02.project.product.Snack;

import java.util.ArrayList;
import java.util.List;

public class Cafe {
    private static Cafe instance = null;

    private ArrayList<Staff> staffList = new ArrayList<>();

    // 손님이 보는 주문 리스트. 직원이 일을 완료하고 상품을 내어줄 때 요소 삭제
    private ArrayList<Order> orderList = new ArrayList<>();

    // 직원들이 보는 주문 리스트(준비 중인 오더 삭제해야 다른 직원이 그 일을 하려 하지 않으므로)
    // 직원이 해당 주문에 대하여 일하기 시작할 때 요소 삭제
    private ArrayList<Order> checkList = new ArrayList<>();

    private ArrayList<ArrayList> menuList = new ArrayList<>();
    private ArrayList<Coffee> coffeeMenus = new ArrayList<>();
    private ArrayList<Beverage> beverageMenus = new ArrayList<>();
    private ArrayList<Snack> snackMenus = new ArrayList<>();

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

    public ArrayList<Order> getCheckList() {
        return checkList;
    }

    public void addStaff(Staff[] staffList) {
        this.staffList.addAll(List.of(staffList));
    }

    public ArrayList<Staff> getStaffList() {
        return staffList;
    }

    public void addCoffeeMenu(Coffee coffee) {
        coffeeMenus.add(coffee);
    }

    public ArrayList<Coffee> getCoffeeMenus() {
        return coffeeMenus;
    }

    public void addBeverageMenu(Beverage beverage) {
        beverageMenus.add(beverage);
    }

    public ArrayList<Beverage> getBeverageMenus() {
        return beverageMenus;
    }

    public void addSnackMenu(Snack snack) {
        snackMenus.add(snack);
    }

    public ArrayList<Snack> getSnackMenus() {
        return snackMenus;
    }

    public void makeMenuList() {
        menuList.add(coffeeMenus);
        menuList.add(beverageMenus);
        menuList.add(snackMenus);
    }

    public ArrayList<ArrayList> getMenuList() {
        return menuList;
    }
}
