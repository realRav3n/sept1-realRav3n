package cn.edu.whut.sept.zuul.POJO;

import java.util.ArrayList;

public class Player {
    private int limitWeight, currentWeight;
    private String name;
    private int currentRoomId;
    private ArrayList<Things> ownThings;

    public Player(int limitWeight, String name, int currentRoomId) {
        this.limitWeight = limitWeight;
        this.name = name;
        this.currentRoomId = currentRoomId;
        ownThings = new ArrayList<>();
        currentWeight = 0;
    }


    public String getName() {
        return name;
    }

    public int getCurrentRoomId() {
        return currentRoomId;
    }

    public void updateLimitWeight(int x){this.limitWeight += x;}

    public void setCurrentRoomId(int currentRoomId) {
        this.currentRoomId = currentRoomId;
    }

    public void showThings() {
        System.out.print("the items of the player are :");
        int sumWeight = 0;
        for(Things thing : ownThings) {
            System.out.print(thing.getName() + " ");
            sumWeight += thing.getWeight();
        }
        System.out.println();
        System.out.println("total weight of the player is : "+sumWeight);
    }

    public boolean takeThings(Things thing) {
        if(currentWeight + thing.getWeight() > limitWeight) return false;
        currentWeight += thing.getWeight();
        ownThings.add(thing);
        return true;
    }

    public Things dropThings(String thingsName) {
        int pos = -1;
        for(int i = 0; i < ownThings.size(); i++) {
            Things thing = ownThings.get(i);
            if(thing.getName().equals(thingsName)) {
                pos = i;
                break;
            }
        }
        if(pos == -1) return null;
        Things thing = ownThings.get(pos);
        ownThings.remove(pos);
        return thing;
    }

}
