package test;

import cn.edu.whut.sept.zuul.POJO.Room;
import cn.edu.whut.sept.zuul.POJO.Things;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void setExit() {
        Room room =new Room("test",0);
        Room eastRoom =new Room("eastRoom",0);
        room.setExit("east",eastRoom);
        if(room.getExit("east").equals(eastRoom)){
            System.out.println("True");
        }
        else{
            System.out.println("False");
        }
    }
    @Test
    void addStaff() {
        Room room =new Room("test",0);
        room.addStaff(new Things("test",10,"emmmmm"));
        if (room.getStaff().size()==1){
            System.out.println("True");
        }
        else{
            System.out.println("False");
        }
    }
    @Test
    void showThings() {
        Room room =new Room("test",0);
        room.addStaff(new Things("test",10,"emmmmm"));
        assertEquals(room.showThings(),1);
    }
}