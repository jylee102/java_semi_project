package semi02.project.callback;

import semi02.project.application.Main;
import semi02.project.utils.Define;

public class SelectMenuCommand implements Command {
    PutItemInACartCommand putInCart = new PutItemInACartCommand();

    @Override
    public void execute(int[] idxs) {
        int categoryIdx = idxs[0];
        int menuIdx = idxs[1];

        System.out.print(menu.getMenu(categoryIdx, menuIdx));
        int optionIdx = scanner.nextInt() - 1;

        if (optionIdx == Define.GO_BACK) return;

        Main.excuteIfAvailableIdx(menuList.get(categoryIdx).get(menuIdx), optionIdx,
                putInCart, new int[]{categoryIdx, menuIdx, optionIdx});

    }
}
