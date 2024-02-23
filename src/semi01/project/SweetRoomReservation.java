package semi01.project;

public class SweetRoomReservation extends RoomReservation {
    public SweetRoomReservation() {
        initReservation();
    }

    public SweetRoomReservation(String customerName, int reservationDate) {
        super(customerName, reservationDate);
        initReservation();
    }

    private void initReservation() {
        super.roomName = "스위트";
        super.roomPrice = 500000;
        if (reservationDate >= 3) super.discountRatio = 0.2;
        provideBreakfast = true;
    }

    @Override
    public String showRoomInfo() {
        super.provideBreakfast = true;
        return super.showRoomInfo().replace(": 1명", " 없음");
    }
}
