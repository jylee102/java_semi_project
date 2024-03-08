package semi02.project.machine;

public class CoffeeMachine implements Machine {
    private int usage = INITIAL_USAGE;
    private final static int RUN_TIME = 3;

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
        int time = usage * RUN_TIME;
        this.timeRemaining += time;
        return time;
    }
}
