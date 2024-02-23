package semi01.project;

public class RoomReservation {
    protected String customerName;
    protected int reservationDate;
    protected String roomName;

    int maxNum;
    boolean provideBreakfast;
    int roomPrice;

    public int discountPrice;
    double discountRatio;

    static final char BREAKFAST = 'O';
    static final char NO_BREAKFAST = 'X';
    char breakfast = provideBreakfast ? BREAKFAST : NO_BREAKFAST;

    public String getCustomerName() {
        return customerName;
    }

    public int getReservationDate() {
        return reservationDate;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getMaxNum() {
        return maxNum;
    }

    // 생성자
    public RoomReservation() {
        initReservation();
    }

    public RoomReservation(String customerName, int reservationDate) {
        this.customerName = customerName;
        this.reservationDate = reservationDate;

        initReservation();
    }

    // 고객등급 초기화 메소드
    private void initReservation() {
        roomName = "싱글";
        maxNum = 1;
        roomPrice = 100000;
        discountRatio = 0;
        provideBreakfast = false;
    }

    public int calcPrice(int reservationDate) {
        int price = roomPrice * reservationDate;
        return price - (int) (price * discountRatio);
    }

    public String showReceipt(int reservationDate) {
        System.out.println("-------- 영수증 --------");
        return ("총액: " + calcPrice(reservationDate) + "원\n" +
                roomName + "룸 | " + reservationDate + "박 | 조식제공: " + breakfast).replace("0000원", "만원");
    }

    public String showRoomInfo() {
        return (roomName + "룸 > 가격: " + roomPrice + "원 | 인원제한: " + maxNum + "명 | 조식제공: " + breakfast).replace("0000원"
                , "만원");
    }

    public String showReservationInfo() {
        return "- " + customerName + "님의 예약 정보:\n\t" + roomName + "룸 | " + reservationDate + "박 | 조식제공: " + breakfast;
    }
}
