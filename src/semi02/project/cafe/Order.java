package semi02.project.cafe;

import semi02.project.application.Main;
import semi02.project.machine.Machine;
import semi02.project.product.Product;

import java.util.ArrayList;

public class Order {
    private Customer customer;
    private ArrayList<Product> productList;
    int productionTime; // 예상 소요 시간(남은 시간)

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
            Cafe.getInstance().getOrderList().remove(0);
        }
    }

    public Order(Customer customer, ArrayList<Product> products) {
        this.customer = customer;
        this.productList = products;
        this.productionTime = calcProductionTime();

        customer.spendMoney(Main.calcPrice(this.productList)); // 상품들 가격만큼 고객이 가진 돈 차감
    }


    public int calcProductionTime() {
        int productionTime = productList.size(); // 대충 메뉴 하나당 1분

        for (Product product : productList) {
            Machine machine = product.getMachine();
            if (product.getMachine() != null) {
                machine.setWorking(true);
                machine.increaseUsage();
            } else productionTime++;
        }

//        productionTime += Main.coffeeMachine.getRunTime() + Main.microwave.getRunTime() + Main.blender.getRunTime();

        return productionTime;
    }
}
