package semi02.project.machine;

import semi02.project.product.Product;

import java.util.ArrayList;

public class Microwave implements Machine {
    private final static int RUN_TIME = 2;
    private final static int MAX_CAPACITY = 3; // 한 전자레인지에 들어갈 수 있는 스낵의 개수

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
    public int getRequiredTime() {
        timeRemaining = (int) Math.ceil(remainingWork.size() / (double) MAX_CAPACITY) * RUN_TIME;
        return getTimeRemaining();
    }
}
