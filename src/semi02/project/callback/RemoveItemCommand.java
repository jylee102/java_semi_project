package semi02.project.callback;

public class RemoveItemCommand implements Command {

    @Override
    public void execute(int[] idxs) {
        myCafe.getCartProducts().remove(idxs[0]);
    }
}
