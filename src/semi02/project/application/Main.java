package semi02.project.application;

import semi02.project.cafe.*;
import semi02.project.cafe.generateString.generateList.*;
import semi02.project.callback.*;
import semi02.project.product.*;
import semi02.project.machine.*;
import semi02.project.utils.Define;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static Cafe myCafe = Cafe.getInstance(); // 카페

    public static Machine coffeeMachine1, coffeeMachine2, blender1, blender2, microwave1;
    int machineIdxs[] = new int[] {0, 0, 0};
    Product juiceTemp;

    static ArrayList<Customer> customers = new ArrayList<>();

    static GenerateList cartList = new GenerateCartList();
    static GenerateList waitingList = new GenerateWaitingList();

    static Command selectMenu = new SelectMenuCommand();
    static Command removeItem = new RemoveItemCommand();

    static boolean ordering;

    static int workedTime = 0; // 영업한 시간

    public static void main(String[] args) {
        Main app = new Main();

        app.readyToOpen();

        boolean run = true;

        while (run) {

            if (myCafe.getOrderList().isEmpty() && customers.isEmpty()) run = false; // 남은 손님과 주문이 없으면 영업 종료
            app.checkOrder();

            System.out.print("> ");
            String input = scanner.nextLine();

            switch (input) {
                case "q":
                    run = false;
                    break;

                case "5":
                    System.out.println(waitingList.getList());
                    break;

                case "0", "o":
                    Customer customer = customers.get(0); // 현재 키오스크를 이용하고 있는 고객

                    ordering = true;

                    while (ordering) {
                        myCafe.printMenuInfo();
                        int orderInput = app.getInputUntilInRange(0, 5);

                        switch (orderInput) {
                            case Define.LEAVE: // 주문 취소
                                System.out.println(Define.LINE + "- \"" + customers.get(0).getNickname() + "\" 고객님이 " +
                                        "떠났습니다." + Define.LINE);
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
                                System.out.print(waitingList.getList());
                                break;
                        }
                    }
                    break;

                default:
                    workedTime++; // 시간의 흐름
                    app.working();
                    app.running();
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
        coffeeMachine1 = new CoffeeMachine();
        coffeeMachine2 = new CoffeeMachine();
        blender1 = new Blender();
        blender2 = new Blender();
        microwave1 = new Microwave();

        myCafe.installMachines(new Machine[]{coffeeMachine1, coffeeMachine2},
                new Machine[]{blender1, blender2}
                , new Machine[]{microwave1});
    }

    public void employStaffs() {
        Staff[] staffList = {new Staff("김자바"), new Staff("박이선")};
        myCafe.addStaff(staffList);
    }

    public void createMenu() {
        Machine coffeeMachine = new CoffeeMachine();
        Machine microwave = new Microwave();
        Machine blender = new Blender();

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
        myCafe.addBeverageMenu(new Beverage("차", "레몬", 3300, true));
        myCafe.addBeverageMenu(new Beverage("차", "유자", 3300, true));
        myCafe.addBeverageMenu(new Beverage("차", "사과유자", 3500, true));

        myCafe.addBeverageMenu(new Beverage("주스", "딸기", 3800, false, blender));
        myCafe.addBeverageMenu(new Beverage("주스", "키위", 3800, false, blender));

        // 스낵 메뉴
        myCafe.addSnackMenu(new Snack("쿠키", "초코", 2000, false));
        myCafe.addSnackMenu(new Snack("쿠키", "아몬드", 2000, false));
        myCafe.addSnackMenu(new Snack("쿠키", "초코스모어", 2900, true, microwave));
        myCafe.addSnackMenu(new Snack("쿠키", "말차스모어", 2900, true, microwave));

        myCafe.addSnackMenu(new Snack("케익", "초코", 6000, false));
        myCafe.addSnackMenu(new Snack("케익", "딸기", 6000, false));
        myCafe.addSnackMenu(new Snack("케익", "당근", 6000, false));

        myCafe.addSnackMenu(new Snack("빵", "소금", 3200, true, microwave));
        myCafe.addSnackMenu(new Snack("빵", "피자", 3800, true, microwave));

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
    // (직원 확인용) 주문이 쌓여있으면 쉬고 있는 직원이 주문을 받음
    // 모든 직원이 일하고 있는지 boolean값을 반환
    public boolean checkOrder() {
        ArrayList<Order> checkList = myCafe.getCheckList(); // 직원 확인용 주문 리스트

        if (!checkList.isEmpty()) {
            for (Staff staff : myCafe.getStaffList()) {
                if (!staff.isWorking()) {
                    staff.work(checkList, workedTime);
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void working() {
        for (Staff staff : myCafe.getStaffList()) {
            if (staff.isWorking()) {
                Order order = staff.getOrder();
                order.setProductionTime(order.getProductionTime() - 1);

                if (order.getProductionTime() <= 0) { // 주문받은 일 완료
                    System.out.println(getCallMessage(staff));
                    staff.setWorking(false);
                    myCafe.removeOrder(order);
                }
            }
        }
    }

    public String getCallMessage(Staff staff) {
        Order order = staff.getOrder();
        ArrayList<Product> orderedProducts = Cafe.restructureList(order.getProductList());
        int numOfKind = orderedProducts.size();

        StringBuilder productStr = new StringBuilder();

        productStr.append("- ").append(staff.getName()).append(" 직원: \"")
                .append(order.getCustomer().getNickname()).append("\"").append(" 고객님, ")
                .append("주문하신 ");

        if (1 < numOfKind && numOfKind < 4) {
            for (int i = 0; i < numOfKind; i++) {
                Product product = orderedProducts.get(i);

                productStr.append(product).append(" ")
                        .append(Collections.frequency(order.getProductList(), product))
                        .append(myCafe.getSnackMenus().contains(product) ? "개" : "잔");
                ;

                if (i < numOfKind - 1) {
                    productStr.append(", ");
                }
            }
        } else {
            Product product = orderedProducts.get(0);

            productStr.append(product).append(" ")
                    .append(Collections.frequency(order.getProductList(), product))
                    .append(myCafe.getSnackMenus().contains(product) ? "개" : "잔");

            if (numOfKind > 3) productStr.append(" 등");
        }
        productStr.append(" 나왔습니다.");

        return productStr.toString();
    }

    /* 기계 관련 작업 */
    public void running() {
        Set<Map.Entry<String, Machine[]>> entrySet = myCafe.getMachineList().entrySet();

        for (Map.Entry<String, Machine[]> entry : entrySet) {
            for (Machine machine : entry.getValue()) {
                if (machine.isWorking()) machine.reduceTimeRemaining();

                if (machine.getTimeRemaining() <= 0) { // 맡은 일 완료
                    machine.setWorking(false);
                }
            }
        }
    }

    /* 고객 관련 작업 */
    public void order(int orderInput) {
        int categoryIdx = orderInput - 1;
        if (categoryIdx == Define.GO_BACK) return;

        myCafe.printMenuInfo(categoryIdx);
        int menuIdx = testScanInt() - 1;
        if (menuIdx == Define.GO_BACK) return;

        executeIfAvailableIdx(selectMenu, new int[]{categoryIdx, menuIdx});
    }

    public int checkCart(Customer customer) {
        myCafe.setOrganizedCart();
        ArrayList<Product> cartProducts = myCafe.getCartProducts();

        System.out.print(cartList.getList());
        System.out.println("(현재 \"" + customer.getNickname() + "\" 고객이 소지한 금액: " +
                customer.getMoney() + "원)" + Define.CHANGE_LINE);
        System.out.print(Define.INPUT_SPACE);

        int selectionInput = getInputUntilInRange(0, 5);

        switch (selectionInput) {
            case 1: // 결제 시도
                if (customer.getMoney() < calcPrice(cartProducts)) {
                    System.out.print(Define.LINE + "[Error] 금액이 부족합니다.");
                    return Define.TRY_AGAIN;
                }
                if (cartProducts.isEmpty()) {
                    System.out.print(Define.LINE + "[Error] 구매할 상품이 없습니다.");
                    return Define.TRY_AGAIN;

                } else { // 결제 및 주문 성공
                    for (Product cartProduct : cartProducts) {
                        Machine machine = cartProduct.getMachine();

                        if (machine instanceof CoffeeMachine || machine instanceof Blender || machine instanceof Microwave) {
                            String machineType = machine.getClass().getSimpleName();
                            Machine[] machines = myCafe.getMachineList().get(machineType);
                            int index = getIndex(machineType, cartProduct);

                            cartProduct.setMachine(machines[index]);
                        }
                    }

                    // 얕은 복사. cartProducts 자체를 넣으면 장바구니 비울 때 주문된 리스트들도 함께 지워짐
                    myCafe.addOrder(new Order(customer, (ArrayList<Product>) cartProducts.clone()));
                    myCafe.increaseSales(calcPrice(cartProducts)); // 매출 증가
                    myCafe.clearCart(); // 장바구니 비우기 (다음 사람을 위해)


                    System.out.print(Define.LINE);
                    System.out.println("- 주문에 성공했습니다");
                    System.out.print("- " + customer.getNickname() + " 고객에게 남은 금액: " + customer.getMoney() + "원");
                    System.out.print(Define.LINE);

                    if (checkOrder()) System.out.println("- 모든 직원이 바빠서 주문이 밀려 있습니다.");

                    return Define.LEAVE;
                }

            case 2: // 더 구경하기
                return Define.GO_BACK;

            case 3: // 장바구니에서 아이템 빼기
                System.out.print(Define.LINE);
                System.out.println("몇 번째 상품을 삭제하시겠습니까? (뒤로 가려면 0)");
                System.out.print(Define.INPUT_SPACE);
                int indexToDelete = testScanInt() - 1;

                if (indexToDelete == Define.GO_BACK) return Define.TRY_AGAIN;
                executeIfAvailableIdx(removeItem, new int[]{indexToDelete});

                return Define.TRY_AGAIN;

            case 4:
                System.out.print(Define.LINE);
                System.out.println("몇 번째 상품의 수량을 바꾸시겠습니까? (뒤로 가려면 0)");
                System.out.print(Define.INPUT_SPACE);

                int targetIndex = testScanInt() - 1;
                Product targetProduct;

                if (targetIndex == Define.GO_BACK) return Define.TRY_AGAIN;

                try {
                    targetProduct = myCafe.getOrganizedCart().get(targetIndex);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("[Error] 잘못된 입력값입니다.");
                    return Define.TRY_AGAIN;
                }

                System.out.println(Define.CHANGE_LINE + "구매할 상품의 수량을 입력하세요.");
                System.out.print(Define.INPUT_SPACE);
                myCafe.changeNumOfItem(targetProduct, testScanInt());

                return Define.TRY_AGAIN;

            case 5: // 카페 이용하지 않고 손님 떠남
                System.out.println(Define.LINE + "- \"" + customers.get(0).getNickname() + "\" 고객님이 떠났습니다." + Define.LINE);
                return Define.LEAVE;

            default:
                return Define.TRY_AGAIN;
        }
    }

    public void quitOrder() {
        myCafe.clearCart(); // 다음 사람을 위해 카트 비우기
        customers.remove(0);
        ordering = false;
    }

    /* 편의를 위한 메소드 */
    // 범위 안의 숫자값을 받을 때까지 입력받기
    public int getInputUntilInRange(int min, int max) {
        int input;

        while (true) {
            input = testScanInt();

            if (input < min || input > max) {
                System.out.println("[Error] 잘못된 입력값입니다.");
                System.out.print(Define.INPUT_SPACE);
            } else break;
        }

        return input;
    }

    // 입력값이 숫자형이 맞는지 검사, 아니라면 숫자형을 입력할 때까지 반복
    public static int testScanInt() {
        String input = scanner.nextLine();
        while (!isNumeric(input) || input.isBlank()) {
            if (!input.isBlank()) System.out.println("[Error] 잘못된 입력값입니다.");
            System.out.print(Define.INPUT_SPACE);
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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
    public static void executeIfAvailableIdx(Command command, int[] idxs) {

        ArrayList arr = null;
        int idx = idxs[idxs.length - 1];

        arr = switch (idxs.length) {
            case 1 -> myCafe.getCartProducts();
            case 2 -> myCafe.getMenuList().get(idxs[0]);
            case 3 -> myCafe.getMenuList().get(idxs[0]).get(idxs[1]);
            default -> arr;
        };

        if (0 <= idx && idx < arr.size()) {
            command.execute(idxs);
        } else {
            System.out.println("[Error] 잘못된 입력값입니다.");
            System.out.print(Define.INPUT_SPACE);
            idxs[idxs.length - 1] = testScanInt() - 1;
            executeIfAvailableIdx(command, idxs); // 재귀함수
        }
    }

    private int getIndex(String machineType, Product product) {
        int index;

        switch (machineType) {
            case "CoffeeMachine":
                index = machineIdxs[Define.COFFEE_MACHINE];
                machineIdxs[Define.COFFEE_MACHINE] = index + 1;
                if (machineIdxs[Define.COFFEE_MACHINE] >= myCafe.getMachineList().get(machineType).length) machineIdxs[Define.COFFEE_MACHINE] = 0;
                break;

            case "Blender":
                index = machineIdxs[Define.BLENDER];
                if (!product.equals(juiceTemp)) machineIdxs[Define.BLENDER] = index + 1;
                if (machineIdxs[Define.BLENDER] >= myCafe.getMachineList().get(machineType).length) machineIdxs[Define.BLENDER] = 0;
                juiceTemp = product;
                break;
            case "Microwave":
                index = machineIdxs[Define.MICROWAVE];
                machineIdxs[Define.MICROWAVE] = index + 1;
                if (machineIdxs[Define.MICROWAVE] >= myCafe.getMachineList().get(machineType).length) machineIdxs[Define.MICROWAVE] = 0;
                break;
            default:
                throw new IllegalArgumentException("Unknown machine type: " + machineType);
        }

        return index;
    }
}