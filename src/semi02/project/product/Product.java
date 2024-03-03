package semi02.project.product;

import semi02.project.machine.Machine;

public abstract class Product {
    private String name;
    private int price;
    private boolean isHot;
    private Machine machine;

    private String flavor = "";

    public Product(String name, int price, boolean isHot) {
        this.name = name;
        this.price = price;
        this.isHot = isHot;
    }

    public Product(String name, int price, boolean isHot, Machine machine) {
        this.name = name;
        this.price = price;
        this.isHot = isHot;
        this.machine = machine;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isHot() {
        return isHot;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public Machine getMachine() {
        return machine;
    }

    public abstract String getFullName();
}
