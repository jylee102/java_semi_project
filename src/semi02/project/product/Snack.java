package semi02.project.product;

import semi02.project.machine.Machine;

public class Snack extends Product {

    // 쿠키 생성자(Machine microwave)
    public Snack(String name, String flavor, int price, boolean isHot, Machine machine) {
        super(name, price, isHot, machine);
        super.setFlavor(flavor);
    }

    public Snack(String name, String flavor, int price, boolean isHot) {
        super(name, price, isHot);
        super.setFlavor(flavor);
    }

    @Override
    public String getFullName() {
        return super.getFlavor() + super.getName();
    }
}
