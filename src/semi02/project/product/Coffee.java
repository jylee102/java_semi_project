package semi02.project.product;

import semi02.project.machine.Machine;

public class Coffee extends Product {
    public Coffee(String name, int price, boolean isHot, Machine machine) {
        super(name, price, isHot, machine);
    }
}
