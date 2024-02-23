package semi01.project;

public class TwinRoomReservation extends RoomReservation {
    public TwinRoomReservation() {
        initReservation();
    }

    public TwinRoomReservation(String customerName, int reservationDate) {
        super(customerName, reservationDate);
        initReservation();
    }

    private void initReservation() {
        super.roomName = "트윈";
        super.maxNum = 3;
        super.roomPrice = 250000;
        if (reservationDate >= 3) super.discountRatio = 0.1;
    }
}
