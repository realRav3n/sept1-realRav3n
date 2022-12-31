package cn.edu.whut.sept.zuul.POJO;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;

public class Room
{
    private int id;
    private String description;
    private HashMap<String, Room> exits;

    private ArrayList<Things> staff;
    private boolean trap,magicCookie;
    private static int cnt = 0;

    public Room(String description)
    {
        id = ++cnt;
        this.description = description;
        exits = new HashMap<>();
        staff =new ArrayList<>();
        trap = false;
        magicCookie = false;
    }

    public boolean getMagicCookie() {
        return magicCookie;
    }


    public void setMagicCookie() {
        magicCookie = true;
    }

    /**
     * 添加一个存在的房间
     * @param direction 方向
     * @param neighbor 相邻的房间
     */

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Things> getStaff() {
        return staff;
    }
    public void setTrap(boolean status){
        trap =status;
    }
    public boolean getTrap() {
        return trap;
    }

    public void addStaff(Things item) {staff.add(item);}


    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * 获取相邻方向有房间的所有方向
     * @return 返回一个包含所有相邻方向有房间的方向
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }


    public void addNewThings(Things things){
        this.staff.add(things);
    }

    /**
     * 展示房间内的物品
     * @return 物品件数
     */
    public int showThings(){
        int sumWeight = 0;
        System.out.println("Let me show you the things in this room!");
        for(Things thing:staff){
            sumWeight += thing.getWeight();
            System.out.println(thing.getName()+","+thing.getWeight()+","+thing.getDescription());
        }
        System.out.println("total weight of the room is : "+sumWeight);
        return staff.size();

    }

    /**
     * 获取指定方向的房间
     * @param direction 选定的方向
     * @return 房间
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }
}


