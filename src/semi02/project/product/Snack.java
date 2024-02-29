package semi02.project.product;

import semi02.project.machine.Machine;

public class Snack extends Product {
    public Snack(String name, int price, boolean isHot, Machine machine) {
        super(name, price, isHot, machine);
    }

    public Snack(String name, int price, boolean isHot) {
        super(name, price, isHot);
    }
}
