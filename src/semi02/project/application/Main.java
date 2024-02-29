package semi02.project.application;

import semi02.project.cafe.*;
import semi02.project.cafe.receipt.GenerateReceipt;
import semi02.project.product.*;
import semi02.project.machine.*;
import semi02.project.serve.*;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Cafe myCafe = Cafe.getInstance();

        ArrayList<Order> orderList = myCafe.getOrderList();
        ArrayList<Order> checkList = myCafe.getCheckList(); // 직원 확인용 주문 리스트

        // 기계 설치
        Machine coffeeMachine = new CoffeeMachine();
        Machine microwave = new Microwave();
        Machine blender = new Blender();

        // 직원 고용
        Staff[] staffList = {new Staff("김자바"), new Staff("박이선")};
        myCafe.addStaff(staffList);

        // 메뉴
        Product americano = new Coffee("아메리카노", 1500, true, coffeeMachine);
        Product icedAmericano = new Coffee("아메리카노", 2000, false, coffeeMachine);
        Product latte = new Coffee("카페라떼", 2700, true, coffeeMachine);
        Product icedLatte = new Coffee("라떼", 2700, false, coffeeMachine);
        Product mocha = new Coffee("카페모카", 3700, true, coffeeMachine);
        Product icedMocha = new Coffee("모카", 3700, false, coffeeMachine);

        Product hotChoco = new Beverage("핫초코", 3200, true);
        Product icedChoco = new Beverage("아이스초코", 3200, false);
        Product icedTeaPeach = new Beverage("복숭아아이스티", 3000, false);
        Product icedTeaLemon = new Beverage("레몬아이스티", 3000, false);
        Product teaLemon = new Beverage("레몬차", 3000, true);
        Product teaYuja = new Beverage("유자차", 3000, true);

        Product juiceStrawberry = new Beverage("딸기주스", 3800, false, blender);
        Product juiceKiwi = new Beverage("키위주스", 3800, false, blender);

        Product cookieChocolate = new Snack("초코쿠키", 2000, true, microwave);
        Product cookieAlmond = new Snack("아몬드쿠키", 2000, true, microwave);
        Product cakeChocolate = new Snack("초코케익", 6000, false);
        Product cakeStrawberry = new Snack("딸기케익", 6000, false);
        Product cakeCarrot = new Snack("당근케익", 6000, false);

        // 손님
        ArrayList<Customer> customers = new ArrayList<>();
        Customer regularCustomer = new Customer(50000, "단골");

        customers.add(new Customer(10000, "온리아아"));
        customers.add(regularCustomer);
        customers.add(new Customer(30000, "카페인중독"));
        customers.add(new Customer(20000, "헬로월드"));
        customers.add(new Customer(20000, "카공족"));
        customers.add(regularCustomer);

        int workedTime = 0;

        System.out.println("주문하려면 o, 프로그램을 종료하려면 q 입력");

        boolean run = true;

        while (run) {

            if (orderList.isEmpty() && customers.isEmpty()) run = false; // 남은 손님과 주문이 없으면 영업 종료

            if (!checkList.isEmpty()) {// (직원 확인용) 주문이 쌓여있으면 쉬고 있는 직원이 주문을 받음
                for (Staff staff : staffList) {
                    if (!staff.isWorking()) {
                        staff.work(checkList, workedTime);
                        break;
                    }
                }
            }

            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equals("q")) run = false;
            else if (input.equals("o")) {
                ArrayList<Product> products = new ArrayList<>(); // 장바구니
                boolean ordering = true;

                while (ordering) {
                    System.out.println("===============================");
                    System.out.println("주문하기 (취소하려면 0)");
                    System.out.println("1.커피 | 2.논커피 | 3.스낵 | 4.장바구니 | 5.대기 중인 주문");
                    System.out.println("===============================");

                    switch (scanner.nextInt()) {
                        case 0:
                            ordering = false;
                            break;
                        case 1:
                            System.out.println("1.아메리카노(HOT/ICED) 1500원 | 2.카페라떼(HOT/ICED) 2700원 | 3.카페모카(HOT/ICED) " +
                                    "3700원");
                            switch (scanner.nextInt()) {
                                case 1:
                                    System.out.println("1.HOT | 2.ICED");
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            products.add(americano);
                                            break;
                                        case 2:
                                            products.add(icedAmericano);
                                            break;
                                    }
                                    break;
                                case 2:
                                    System.out.println("1.HOT | 2.ICED");
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            products.add(latte);
                                            break;
                                        case 2:
                                            products.add(icedLatte);
                                            break;
                                    }
                                    break;
                                case 3:
                                    System.out.println("1.HOT | 2.ICED");
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            products.add(mocha);
                                            break;
                                        case 2:
                                            products.add(icedMocha);
                                            break;
                                    }
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("1.초코(HOT/ICED) | 2.아이스티(복숭아/레몬) | 3.차(레몬/유자) | 4.주스(딸기/키위)");
                            switch (scanner.nextInt()) {
                                case 1:
                                    System.out.println("1.HOT | 2.ICED");
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            products.add(hotChoco);
                                            break;
                                        case 2:
                                            products.add(icedChoco);
                                            break;
                                    }
                                    break;
                                case 2:
                                    System.out.println("1.복숭아 | 2.레몬");
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            products.add(icedTeaPeach);
                                            break;
                                        case 2:
                                            products.add(icedTeaLemon);
                                            break;
                                    }
                                    break;
                                case 3:
                                    System.out.println("1.레몬 | 2.유자");
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            products.add(teaLemon);
                                            break;
                                        case 2:
                                            products.add(teaYuja);
                                            break;
                                    }
                                    break;
                                case 4:
                                    System.out.println("1.딸기 | 2.키위");
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            products.add(juiceStrawberry);
                                            break;
                                        case 2:
                                            products.add(juiceKiwi);
                                            break;
                                    }
                                    break;

                            }
                            break;
                        case 3:
                            System.out.println("1.쿠키(초코/아몬드) | 2.케익(초코/딸기/당근)");
                            switch (scanner.nextInt()) {
                                case 1:
                                    System.out.println("1.초코 | 2.아몬드");
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            products.add(cookieChocolate);
                                            break;
                                        case 2:
                                            products.add(cookieAlmond);
                                            break;
                                    }
                                    break;
                                case 2:
                                    System.out.println("1.초코 | 2.딸기 | 3.당근");
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            products.add(cakeChocolate);
                                            break;
                                        case 2:
                                            products.add(cakeStrawberry);
                                            break;
                                        case 3:
                                            products.add(cakeCarrot);
                                            break;
                                    }
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println(new GenerateReceipt().getReceipt(products));

                            switch (scanner.nextInt()) {
                                case 1:
                                    if (customers.get(0).getMoney() < calcPrice(products)) {
                                        System.out.println("금액이 부족합니다.");
                                        break;
                                    } else {
                                        Take take = null;
                                        System.out.println("1.매장에서 먹고 가기 | 2.포장해 가기");
                                        switch (scanner.nextInt()) {
                                            case 1:
                                                take = new ForHere();
                                                break;
                                            case 2:
                                                take = new ToGo();
                                                break;
                                        }
                                        myCafe.addOrder(new Order(customers.get(0), products, take));
                                        System.out.println("손님에게 남은 금액: " + customers.get(0).getMoney());
                                        customers.remove(0);
                                        ordering = false;
                                    }
                                case 2:
                                    continue;
                                case 3:
                                    System.out.println("몇 번째 상품을 삭제하시겠습니까?");
                                    products.remove(scanner.nextInt() - 1);
                                    break;
                                case 4:
                                    customers.remove(0);
                                    ordering = false;
                            }

                            break;
                        case 5:
                            for (Order order : myCafe.getOrderList()) {
                                System.out.println(order.getCustomer().getNickname() + "고객님의 주문 | 예상 소요 시간: " + order.getProductionTime());
                            }
                            break;
                    }

                }
            } else {
                workedTime++;
                for (Staff staff : staffList) {
                    if (staff.isWorking()) {
                        staff.getOrder().setProductionTime(staff.getOrder().getProductionTime() - 1);
                        if (staff.getOrder().getProductionTime() < 0) {
                            staff.setWorking(false);
                            System.out.println(staff.getName() + "직원: "
                                    + staff.getOrder().getCustomer().getNickname() + " 고객님, " +
                                    "주문하신 상품 나왔습니다.");
                        }
                    }
                }
            }

        }
        System.out.println("영업한 시간: " + workedTime + "분");
    }

    public static int calcPrice(ArrayList<Product> products) {
        int price = 0;
        for (Product product : products) {
            price += product.getPrice();
        }
        return price;
    }
}