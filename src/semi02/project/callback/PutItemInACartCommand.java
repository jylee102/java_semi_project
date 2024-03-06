package semi02.project.callback;

import semi02.project.product.Product;

public class PutItemInACartCommand implements Command {
    @Override
    public void execute(int[] idxs) {
        Product selectedItem = menuList.get(idxs[0]).get(idxs[1]).get(idxs[2]);

        myCafe.putInACart(selectedItem);
        System.out.println("[SYSTEM] " + selectedItem + "이(가) 장바구니에 들어갔습니다.");
    }
}
