package customer.project;

public class VIPCustomer extends Customer {
    private int agentID; // 상담원 ID
    double saleRatio; // 할인율

    public VIPCustomer(int customerID, String customerName, int agentID) {
        super(customerID, customerName);

        super.customerGrade = "VIP";
        super.bonusRatio = 0.05;
        this.saleRatio = 0.1;
        this.agentID = agentID;
    }

    public int getAgentID() {
        return agentID;
    }

    @Override
    public int calcPrice(int price) {
        bonusPoint += (price * bonusRatio); // 포인트 적립
        return price - (int) (price * saleRatio); // 지불할 금액(할인율 적용)
    }

    @Override
    public String showCustomerInfo() {
        return super.showCustomerInfo()
                + ", 담당 상담원 ID: " + agentID;
    }
}
