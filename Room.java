public class Room {
    private int number;
    private String roomType, status;
    private double price;

    public Room(int number, String roomType, String status, double price) {
        this.number = number;
        this.roomType = roomType;
        this.status = status;
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

