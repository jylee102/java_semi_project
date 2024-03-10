package semi02.project.machine;

import semi02.project.product.Product;

import java.util.ArrayList;

public class Blender implements Machine {
    private final static int RUN_TIME = 5;

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
        ArrayList<Product> typeOfWork = null;

        // 같은 주스는 한 번에 갈기 // 중복 요소 제거하여 리스트 재구성
        if (!remainingWork.isEmpty()) {
            typeOfWork = new ArrayList<>();

            for (Product work : remainingWork) {
                if (!typeOfWork.contains(work)) {
                    typeOfWork.add(work);
                }
            }
        }

        int time = (!remainingWork.isEmpty()) ? typeOfWork.size() * RUN_TIME : 0;
        this.timeRemaining += time;
        return time;
    }
}
