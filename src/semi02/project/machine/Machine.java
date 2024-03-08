package semi02.project.machine;

public interface Machine {
    int INITIAL_USAGE = 0;

    public boolean isWorking();

    public void setWorking(boolean working);

    public void increaseUsage();

    public void clearUsage();

    public int getRunTime();
}
