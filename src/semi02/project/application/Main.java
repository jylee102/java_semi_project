package semi02.project.application;

import semi02.project.cafe.*;
import semi02.project.cafe.generateString.*;
import semi02.project.callback.*;
import semi02.project.product.*;
import semi02.project.machine.*;
import semi02.project.utils.Define;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static Cafe myCafe = Cafe.getInstance(); // 카페 생성
    static Machine coffeeMachine, microwave, blender;
    static ArrayList<Customer> customers = new ArrayList<>();

    static GenerateMenu menu = new GenerateMenu();
    static Command removeItem = new RemoveItemCommand();
    static Command selectMenu = new SelectMenuCommand();

    static boolean ordering;

    public static void main(String[] args) {
        Main app = new Main();

        app.readyToOpen();
        int workedTime = 0; // 영업한 시간

        boolean run = true;

        while (run) {

            if (myCafe.getOrderList().isEmpty() && customers.isEmpty()) run = false; // 남은 손님과 주문이 없으면 영업 종료

            app.checkOrder(workedTime); // 주문 확인 및 작업

            System.out.print("> ");
            String input = scanner.nextLine();

            switch (input) {
                case "q":
                    run = false;
                    break;

                case "5":
                    app.waitingStatus();
                    System.out.print(Define.CHANGE_LINE);
                    break;

                case "0", "o":
                    Customer customer = customers.get(0); // 현재 키오스크를 이용하고 있는 고객

                    ordering = true;

                    while (ordering) {

                        System.out.print(Define.BOLD_LINE);
                        System.out.println("주문하기 (취소하려면 0)");
                        System.out.println("1.커피 | 2.논커피 | 3.스낵 | 4.장바구니 | 5.대기 중인 주문");
                        System.out.print(Define.CHANGE_LINE);

                        System.out.print(Define.INPUT_SPACE);
                        int orderInput = app.testScanInt();

                        switch (orderInput) {
                            case Define.LEAVE: // 주문 취소
                                System.out.println(Define.LINE + "- \"" + customers.get(0).getNickname() + "\" 고객님이 떠났습니다." + Define.LINE);
                                app.quitOrder();
                                break;

                            case 1, 2, 3: // 주문
                                app.order(orderInput);
                                break;

                            case 4: // 장바구니 확인
                                int status;

                                do {
                                    status = app.checkCart(customer);

                                    if (status == Define.LEAVE) {
                                        app.quitOrder();
                                        break;
                                    }

                                } while (status == Define.TRY_AGAIN);
                                break;

                            case 5: // 주문대기현황 보기
                                app.waitingStatus();
                                break;
                        }
                    }
                    break;

                default:
                    workedTime++; // 시간의 흐름
                    app.working();
                    break;
            }
        }
        System.out.print(Define.BOLD_LINE);
        System.out.println("\t\t\t\t\t~~영업종료~~");
        System.out.println("\t\t\t\t- 영업한 시간: " + workedTime + "분");
        System.out.println("\t\t\t\t- 총 매출: " + myCafe.getSales() + "원");
    }

    /* 객체 생성 */
    public void readyToOpen() {
        installMachines();
        employStaffs();
        createMenu();
        createCustomers();

        System.out.print(Define.BOLD_LINE);
        System.out.println("\t\t\t\t\t\t~~영업시작~~");
        System.out.print("주문하려면 o 또는 0, 주문 대기현황을 보려면 5, 프로그램을 종료하려면 q 입력");
        System.out.print(Define.BOLD_LINE);
    }

    public void installMachines() {
        coffeeMachine = new CoffeeMachine();
        microwave = new Microwave();
        blender = new Blender();
    }

    public void employStaffs() {
        Staff[] staffList = {new Staff("김자바"), new Staff("박이선")};
        myCafe.addStaff(staffList);
    }

    public void createMenu() {
        // 커피 메뉴
        myCafe.addCoffeeMenu(new Coffee("아메리카노", 1500, true, coffeeMachine));
        myCafe.addCoffeeMenu(new Coffee("아메리카노", 2000, false, coffeeMachine));
        myCafe.addCoffeeMenu(new Coffee("카페라떼", 2700, true, coffeeMachine));
        myCafe.addCoffeeMenu(new Coffee("카페라떼", 2700, false, coffeeMachine));
        myCafe.addCoffeeMenu(new Coffee("카페모카", 3700, true, coffeeMachine));
        myCafe.addCoffeeMenu(new Coffee("카페모카", 3700, false, coffeeMachine));

        // 음료(논커피) 메뉴
        myCafe.addBeverageMenu(new Beverage("초코", "", 3200, true));
        myCafe.addBeverageMenu(new Beverage("초코", "", 3200, false));
        myCafe.addBeverageMenu(new Beverage("아이스티", "복숭아", 3000, false));
        myCafe.addBeverageMenu(new Beverage("아이스티", "레몬", 3000, false));
        myCafe.addBeverageMenu(new Beverage("차", "레몬", 3000, true));
        myCafe.addBeverageMenu(new Beverage("차", "유자", 3000, true));

        myCafe.addBeverageMenu(new Beverage("주스", "딸기", 3800, false, blender));
        myCafe.addBeverageMenu(new Beverage("주스", "키위", 3800, false, blender));

        // 스낵 메뉴
        myCafe.addSnackMenu(new Snack("쿠키", "초코", 2000, true, microwave));
        myCafe.addSnackMenu(new Snack("쿠키", "아몬드", 2000, true, microwave));
        myCafe.addSnackMenu(new Snack("케익", "초코", 6000, false));
        myCafe.addSnackMenu(new Snack("케익", "딸기", 6000, false));
        myCafe.addSnackMenu(new Snack("케익", "당근", 6000, false));

        // 커피 + 음료 + 스낵
        myCafe.makeMenuList();
    }

    public void createCustomers() {
        Customer regularCustomer = new Customer(50000, "단골");
        customers.add(new Customer(10000, "온리아아"));
        customers.add(regularCustomer);
        customers.add(new Customer(30000, "카페인중독"));
        customers.add(new Customer(20000, "헬로월드"));
        customers.add(new Customer(20000, "카공족"));
        customers.add(regularCustomer);
    }

    /* 프로그램 작동에 사용되는 메소드 */
    /* 직원 관련 작업 */
    public void checkOrder(int workedTime) {
        ArrayList<Order> checkList = myCafe.getCheckList(); // 직원 확인용 주문 리스트

        if (!checkList.isEmpty()) {// (직원 확인용) 주문이 쌓여있으면 쉬고 있는 직원이 주문을 받음

            boolean allBusy = true; // 남은 직원이 없는지 확인하는 플래그

            for (Staff staff : myCafe.getStaffList()) {
                if (!staff.isWorking()) {
                    staff.work(checkList, workedTime);
                    allBusy = false;
                    break;
                }
            }
            if (allBusy) System.out.println("- 현재는 모든 직원이 바빠서 주문을 받을 수 없습니다. 잠시만 기다려주세요.");
        }
    }

    public void waitingStatus() {
        System.out.print(Define.LINE);
        if (myCafe.getOrderList().isEmpty()) System.out.println("현재 대기가 없습니다.");
        for (Order order : myCafe.getOrderList()) {
            System.out.println(order.getCustomer().getNickname() + "고객님의 주문" + Define.DEVIDER
                    + "예상 소요 시간: " + order.getProductionTime() + "분");
        }
    }

    public void working() {
        for (Staff staff : myCafe.getStaffList()) {
            if (staff.isWorking()) {
                staff.getOrder().setProductionTime(staff.getOrder().getProductionTime() - 1);
                if (staff.getOrder().getProductionTime() <= 0) {
                    staff.setWorking(false);
                    myCafe.removeOrder(staff.getOrder());
                    System.out.println("- " + staff.getName() + " 직원: \""
                            + staff.getOrder().getCustomer().getNickname() + "\"" + " 고객님, " +
                            "주문하신 상품 나왔습니다.");
                }
            }
        }
    }

    /* 고객 관련 작업 */
    public void order(int orderInput) {
        ArrayList<ArrayList<ArrayList<Product>>> menuList = myCafe.getMenuList();
        int categoryIdx = orderInput - 1;
        if (categoryIdx == Define.GO_BACK) return;

        System.out.print(menu.getMenu(categoryIdx));
        int menuIdx = testScanInt() - 1;
        if (menuIdx == Define.GO_BACK) return;

        excuteIfAvailableIdx(menuList.get(categoryIdx), menuIdx, selectMenu, new int[]{categoryIdx, menuIdx});
    }

    public int checkCart(Customer customer) {
        System.out.print(new GenerateCartList().getReceipt(myCafe.getCartProducts()));
        System.out.println("(현재 \"" + customer.getNickname() + "\" 고객이 소지한 금액: " +
                customer.getMoney() + "원)" + Define.CHANGE_LINE);
        System.out.print(Define.INPUT_SPACE);

        switch (testScanInt()) {
            case 1: // 결제 시도
                if (customer.getMoney() < calcPrice(myCafe.getCartProducts())) {
                    System.out.print(Define.LINE + "- Error: 금액이 부족합니다.");
                    return Define.TRY_AGAIN;
                }
                if (myCafe.getCartProducts().isEmpty()) {
                    System.out.print(Define.LINE + "- Error: 구매할 상품이 없습니다.");
                    return Define.TRY_AGAIN;
                } else { // 결제 및 주문 성공
                    myCafe.addOrder(new Order(customer, myCafe.getCartProducts()));
                    myCafe.increaseSales(calcPrice(myCafe.getCartProducts())); // 매출 증가
                    myCafe.clearCart(); // 장바구니 비우기 (다음 사람을 위해)

                    System.out.print(Define.LINE);
                    System.out.println("- 주문에 성공했습니다");
                    System.out.print("- 손님에게 남은 금액: " + customer.getMoney() + "원");
                    System.out.print(Define.LINE);

                    return Define.LEAVE;
                }

            case 2: // 더 구경하기
                return Define.GO_BACK;

            case 3: // 장바구니에서 아이템 빼기
                System.out.print(Define.LINE);
                System.out.println("몇 번째 상품을 삭제하시겠습니까?");
                System.out.print(Define.INPUT_SPACE);

                int indexToDelete = testScanInt() - 1;
                excuteIfAvailableIdx(myCafe.getCartProducts(), indexToDelete, removeItem, new int[]{indexToDelete});

                return Define.TRY_AGAIN;

            case 4: // 카페 이용하지 않고 손님 떠남
                System.out.println(Define.LINE + "- \"" + customers.get(0).getNickname() + "\" 고객님이 떠났습니다." + Define.LINE);
                return Define.LEAVE;

            default:
                return Define.TRY_AGAIN;
        }
    }

    public void quitOrder() {
        myCafe.clearCart(); // 다음 사람을 위해 카트 비우기
        scanner.nextLine(); // 엔터 소비
        customers.remove(0);
        ordering = false;
    }

    /* 편의를 위한 메소드 */
    // 입력값이 int형이 맞는지 검사, 아니라면 int형을 입력할 때까지 반복
    public int testScanInt() {
        while (!scanner.hasNextInt()) {
            System.out.println(Define.INPUT_SPACE);
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    // 상품들의 가격 계산하는 메소드
    public static int calcPrice(ArrayList<Product> products) {
        int price = 0;
        for (Product product : products) {
            price += product.getPrice();
        }
        return price;
    }

    // IndexOutOfBoundsException 방지 위해서 사용자가 입력한 값이 ArrayList 인덱스 범위 안인지 확인
    // Command라는 인터페이스를 만들어서 자바스크립트의 콜백함수처럼 사용
    public static void excuteIfAvailableIdx(ArrayList arr, int idx, Command command, int[] idxs) {
        if (0 <= idx && idx < arr.size()) {
            command.execute(idxs);
        } else {
            System.out.print(Define.LINE + "- Error: 잘못된 입력값입니다.");
        }
    }
}