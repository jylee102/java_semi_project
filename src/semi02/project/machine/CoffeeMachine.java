package semi02.project.machine;

import semi02.project.product.Product;

import java.util.ArrayList;

public class CoffeeMachine implements Machine {
    private final static int RUN_TIME = 3;

    private boolean isWorking = false;
    private int timeRemaining; // 작동 끝나기까지 남은 시간
    private ArrayList<Product> remainingWork = new ArrayList<>();

    @Override
    public int getTimeRemaining() {
        return timeRemaining;
    }

    @Override
    public void reduceTimeRemaining() {
        timeRemaining--;
    }

    @Override
    public void addWork(Product product) {
        remainingWork.add(product);
    }

    @Override
    public boolean isWorking() {
        return isWorking;
    }

    @Override
    public void setWorking(boolean working) {
        isWorking = working;
    }

    @Override
    public int getRunTime() {
        int time = remainingWork.size() * RUN_TIME;
        this.timeRemaining += time;
        return time;
    }
}
