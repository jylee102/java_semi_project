package semi02.project.cafe;

import semi02.project.application.Main;
import semi02.project.machine.*;
import semi02.project.product.Product;

import java.util.*;

public class Order {
    Cafe myCafe = Cafe.getInstance();

    private Customer customer;
    private ArrayList<Product> productList;
    int productionTime = 0; // 예상 소요 시간(남은 시간)

    public Order(Customer customer, ArrayList<Product> products) {
        this.customer = customer;
        this.productList = products;
        this.productionTime = calcProductionTime();

        customer.spendMoney(Main.calcPrice(this.productList)); // 상품들 가격만큼 고객이 가진 돈 차감
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public int getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(int productionTime) {
        this.productionTime = productionTime;

        if (productionTime < 0) {
            myCafe.getOrderList().remove(0);
        }
    }

    public int calcProductionTime() {
        int productionTime = productList.size(); // 대충 메뉴 하나당 1분의 기계 조작 시간 및 준비 시간

        for (Product product : productList) {
            if (product.getMachine() != null) product.getMachine().addWork(product);
            else productionTime++;
        }

        TreeSet<Integer> runTimes = new TreeSet<>();

        for (String k : myCafe.getMachineList().keySet()) {
            Machine[] v = myCafe.getMachineList().get(k);
            for (Machine machine : v) {
                runTimes.add(machine.getRunTime());
            }
        }
        productionTime += runTimes.last();

        return productionTime;
    }
}
