package semi02.project.product;

import semi02.project.machine.Machine;

public class Beverage extends Product {

    // 주스 생성자(Machine blender)
    public Beverage(String name, String flavor, int price, boolean isHot, Machine machine) {
        super(name, price, isHot, machine);
        super.setFlavor(flavor);
    }

    public Beverage(String name, String flavor, int price, boolean isHot) {
        super(name, price, isHot);
        super.setFlavor(flavor);
    }

    @Override
    public String toString() {
        String name = super.getName();

        if (name.equals("초코")) {
            if (super.isHot()) return "핫" + name + "(HOT)";
            else return "아이스" + name + "(ICED)";
        }

        if (super.isHot()) return super.getFlavor() + name + "(HOT)";
        else return super.getFlavor() + name + "(ICED)";
    }
}
