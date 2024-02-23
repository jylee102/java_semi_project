package semi01.project.application;

import semi01.project.*;

import java.util.ArrayList;
import java.util.Scanner;

public class RoomApplication {
    private static ArrayList<RoomReservation> reservationList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean run = true;

        while (run) {
            System.out.println("======================= 메뉴 =======================");
            System.out.println("1.예약하기 | 2.룸 종류 보기 | 3.예약 확인 | 4.예약 목록 | 5.종료");
            System.out.println("===================================================");

            System.out.print("메뉴 선택> ");

            switch (scanner.nextInt()) {
                case 1:
                    makeReservetion();
                    break;

                case 2:
                    System.out.println("================== 룸 정보 ==================");
                    System.out.println(new RoomReservation().showRoomInfo());
                    System.out.println(new DoubleRoomReservation().showRoomInfo());
                    System.out.println(new TwinRoomReservation().showRoomInfo());
                    System.out.println(new SweetRoomReservation().showRoomInfo());
                    break;

                case 3:
                    System.out.println("================== 예약 확인 ==================");

                    System.out.print("예약자명: ");
                    RoomReservation reservation = findCustomer(scanner.next());

                    if (reservation == null) {
                        System.out.println("Error: 해당 예약자를 찾을 수 없습니다.");
                        return;
                    }

                    System.out.println(reservation.showReservationInfo());
                    break;

                case 4:
                    System.out.println("================== 예약 목록 ==================");
                    showAllReservation();
                    break;

                case 5:
                    run = false;
            }
        }
        System.out.println("프로그램 종료");
    }

    private static void makeReservetion() {
        System.out.println("========= 예약 =========");

        System.out.print("예약자명: ");
        String name = scanner.next();
        System.out.print("예약일수: ");
        int day = scanner.nextInt();
        System.out.print("예약할 룸: ");
        String room = scanner.next();
        System.out.print("인원: ");
        int peopleNum = scanner.nextInt();

        RoomReservation reservation = null;

        switch (room) {
            case "single":
                reservation = new RoomReservation(name, day);
                break;
            case "double":
                reservation = new DoubleRoomReservation(name, day);
                break;
            case "twin":
                reservation = new TwinRoomReservation(name, day);
                break;
            case "sweet":
                reservation = new SweetRoomReservation(name, day);
                break;
            default:
                return;
        }

        if (peopleNum > reservation.getMaxNum() && !room.equals("sweet")) {
            System.out.println("Error: " + reservation.getRoomName() + "룸의 인원제한인 "
                    + reservation.getMaxNum() + "명을 초과하여 예약할 수 없습니다.");
            return;
        }

        reservationList.add(reservation);
        String receipt = reservation.showReceipt(reservation.getReservationDate());
        System.out.println(receipt);
    }

    public static void showAllReservation() {
        System.out.println("=============모든 예약 정보 출력=============");

        for (RoomReservation reservation : reservationList) {
            System.out.println(reservation.showReservationInfo());
        }
    }

    public static RoomReservation findCustomer(String customerName) {
        RoomReservation resultCustomer = null;
        for (RoomReservation reservation : reservationList) {
            if (reservation.getCustomerName().equals(customerName)) {
                resultCustomer = reservation;
                break;
            }
        }
        return resultCustomer;
    }
}
