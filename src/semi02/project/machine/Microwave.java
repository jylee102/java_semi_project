package semi02.project.machine;

public class Microwave implements Machine {
    private int usage = INITIAL_USAGE;
    private final static int RUN_TIME = 2;
    private final static int MAX_CAPACITY = 3; // 한 전자레인지에 들어갈 수 있는 스낵의 개수

    private boolean isWorking = false;

    @Override
    public boolean isWorking() {
        return isWorking;
    }

    @Override
    public void setWorking(boolean working) {
        isWorking = working;
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
