package model;

import java.math.RoundingMode;

public class Room implements IRoom {
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    public Room(String roomNumber,Double price,RoomType enumeration){
        super();
        this.roomNumber=roomNumber;
        this.price=price;
        this.enumeration=enumeration;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
    public Double getRoomPrice() {
        return price;
    }
    public RoomType getRoomType() {
        return enumeration;
    }
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString(){
        return ("Room number: "+roomNumber+" ;Price per night: "+ price+" ;Room type: "+ enumeration);
    }
}
