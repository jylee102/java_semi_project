package semi02.project.machine;

import semi02.project.product.Product;

import java.util.*;

public class Blender implements Machine {
    private final static int RUN_TIME = 4;

    private boolean isWorking = false;

    private int timeRemaining; // 작동 끝나기까지 남은 시간
    private Set<Product> workInAnOrder = new HashSet<>(); // 한 주문에 대한 임시 저장 공간 // 동일 손님 동일 주스메뉴 한꺼번에 처리 = 중복 제거
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
        workInAnOrder.add(product);
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
        remainingWork.addAll(workInAnOrder);

        timeRemaining = remainingWork.size() * RUN_TIME;

        return getTimeRemaining();
    }
}
