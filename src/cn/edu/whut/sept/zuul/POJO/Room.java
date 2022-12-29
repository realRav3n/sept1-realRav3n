package cn.edu.whut.sept.zuul.POJO;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import cn.edu.whut.sept.zuul.POJO.Things;

public class Room
{
    private String description;
    private HashMap<String, Room> exits;

    private ArrayList<Things> staff;

    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
        staff =new ArrayList<>();
    }

    /**
     * 添加一个存在的房间
     * @param direction 方向
     * @param neighbor 相邻的房间
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    public String getShortDescription()
    {
        return description;
    }

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
    /**
     * 获取指定方向的房间
     * @param direction 选定的方向
     * @return 房间
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    public void addNewThings(Things things){
        this.staff.add(things);
    }

    public int showThings(){
        System.out.println("Let me show you the things in this room!");
        for(Things thing:staff){
            System.out.println(thing.getName()+","+thing.getWeight()+","+thing.getDescription());
        }
        return staff.size();
    }
}


