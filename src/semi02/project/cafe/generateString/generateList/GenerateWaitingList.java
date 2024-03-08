package semi02.project.cafe.generateString.generateList;

import semi02.project.cafe.Cafe;
import semi02.project.cafe.Order;
import semi02.project.utils.Define;

public class GenerateWaitingList implements GenerateList {
    @Override
    public String getList() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(Define.LINE).append("대기목록").append(Define.CHANGE_LINE);

        if (myCafe.getOrderList().isEmpty()) buffer.append("(현재 대기가 없습니다.)").append(Define.CHANGE_LINE);
        else {
            for (Order order : myCafe.getOrderList()) {
                buffer.append("\"").append(order.getCustomer().getNickname()).append("\" 고객님의 주문").append(Define.DEVIDER)
                        .append("예상소요 시간: ").append(order.getProductionTime()).append("분").append(Define.CHANGE_LINE);
            }
        }
        buffer.append(Define.LINE.replace("\n", "")).append(Define.CHANGE_LINE);

        return buffer.toString();
    }
}
