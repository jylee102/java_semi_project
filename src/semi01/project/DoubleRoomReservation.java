package semi01.project;

public class DoubleRoomReservation extends RoomReservation {
    public DoubleRoomReservation() {
        initReservation();
    }

    public DoubleRoomReservation(String customerName, int reservationDate) {
        super(customerName, reservationDate);
        initReservation();
    }

    private void initReservation() {
        super.roomName = "더블";
        super.maxNum = 2;
        super.roomPrice = 200000;
        if (reservationDate >= 3) super.discountRatio = 0.05;
    }
}
