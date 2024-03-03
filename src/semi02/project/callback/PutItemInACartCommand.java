package semi02.project.callback;

public class PutItemInACartCommand implements Command {
    @Override
    public void execute(int[] idxs) {
        myCafe.putInACart(
                menuList
                        .get(idxs[0])
                        .get(idxs[1])
                        .get(idxs[2]));
    }
}
