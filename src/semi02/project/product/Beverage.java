package semi02.project.product;

import semi02.project.machine.Machine;

public class Beverage extends Product {

    public Beverage(String name, int price, boolean isHot, Machine machine) {
        super(name, price, isHot, machine);
    }

    public Beverage(String name, int price, boolean isHot) {
        super(name, price, isHot);
    }
}
