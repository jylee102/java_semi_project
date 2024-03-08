package semi02.project.machine;

public class Microwave implements Machine {
    private int usage = INITIAL_USAGE; // 예산 소요 시간 계산 할 때 쓰임. 한 주문에 기계 작동 횟수
    private final static int RUN_TIME = 2;
    private final static int MAX_CAPACITY = 3; // 한 전자레인지에 들어갈 수 있는 스낵의 개수

    private boolean isWorking = false;
    private int timeRemaining; // 작동 끝나기까지 남은 시간

    @Override
    public boolean isWorking() {
        return isWorking;
    }

    @Override
    public void setWorking(boolean working) {
        isWorking = working;
    }

    @Override
    public int getTimeRemaining() {
        return timeRemaining;
    }

    @Override
    public void reduceTimeRemaining() {
        timeRemaining--;
    }

    @Override
    public void increaseUsage() {
        usage++;
    }

    @Override
    public void clearUsage() {
        usage = INITIAL_USAGE;
    }

    @Override
    public int getRunTime() {
        return (int) Math.ceil(usage / (double) MAX_CAPACITY) * RUN_TIME;
    }
}
