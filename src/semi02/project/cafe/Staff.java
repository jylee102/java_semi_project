package semi02.project.cafe;

import java.util.ArrayList;

public class Staff {
    String name; // 직원 이름
    boolean isWorking; // 이 직원이 현재 일하고 있는가?

    Order order; // 처리하고 있는 주문
    int startTime; // 주문 받은 시간(일 시작 시간)

    public Staff(String name) {
        this.name = name;
        this.isWorking = false;
    }

    public String getName() {
        return name;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public Order getOrder() {
        return order;
    }

    public void work(ArrayList<Order> checkList, int startTime) {
        this.isWorking = true;
        this.startTime = startTime;
        this.order = checkList.get(0);

        System.out.println("- " + name + " 직원이 \"" +
                order.getCustomer().getNickname() + "\" 고객의 주문을 받았습니다. " +
                "예상 소요 시간은 " + order.getProductionTime() + "분입니다.");

        checkList.remove(0); // 직원이 맡은 주문은 주문리스트에서 삭제
    }


}
