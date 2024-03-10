package semi02.project.machine;

import semi02.project.cafe.Staff;
import semi02.project.product.Coffee;
import semi02.project.product.Product;

public interface Machine {
    public int getTimeRemaining();

    public void reduceTimeRemaining();

    public void addWork(Product product);

    public boolean isWorking();

    public void setWorking(boolean working);

    public int getRunTime();
}
