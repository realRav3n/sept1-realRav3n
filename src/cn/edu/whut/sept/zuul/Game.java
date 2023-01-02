/**
 * 该类是“World-of-Zuul”应用程序的主类。
 * 《World of Zuul》是一款简单的文本冒险游戏。用户可以在一些房间组成的迷宫中探险。
 * 你们可以通过扩展该游戏的功能使它更有趣!.
 *
 * 如果想开始执行这个游戏，用户需要创建Game类的一个实例并调用“play”方法。
 *
 * Game类的实例将创建并初始化所有其他类:它创建所有房间，并将它们连接成迷宫；它创建解析器
 * 接收用户输入，并将用户输入转换成命令后开始运行游戏。
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import cn.edu.whut.sept.zuul.POJO.Player;
import cn.edu.whut.sept.zuul.POJO.Room;
import cn.edu.whut.sept.zuul.POJO.Things;
import cn.edu.whut.sept.zuul.util.FileUtil;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private HashMap<Integer,Room> idRoomMap;
    private Deque<Integer> stack;
    private Player nowPlayer;
    private int totalRoom ;


    /**
     * 创建游戏并初始化内部数据和解析器.
     */
    public Game() throws IOException {
        parser = new Parser();
        stack =new ArrayDeque<>();
        idRoomMap =new HashMap<>();
        initMap();
        createPlayers();

    }

    /**
     * 创建所有房间对象并连接其出口用以构建迷宫.
     */
    /*
    private void createRooms()
    {
        Room outside, theater, pub, lab, office,transport;
        Things apple;

        //创建房间
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transport =new Room("middle there is a magical spring,you goes up..");
        //建立索引
        idRoomMap.put(outside.getId(),outside);
        idRoomMap.put(theater.getId(),theater);
        idRoomMap.put(pub.getId(),pub);
        idRoomMap.put(lab.getId(),lab);
        idRoomMap.put(office.getId(),office);
        idRoomMap.put(transport.getId(),transport);
        //创建初始物品
        apple =new Things("apple",10,"我是一个朴实无华的苹果");
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.addNewThings(apple);

        theater.setExit("west", outside);
        theater.setExit("east",transport);

        transport.setExit("west",theater);
        transport.setTrap(true);

        pub.setExit("east", outside);
        pub.setMagicCookie();

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);


        currentRoom = outside;  // start game outside
        stack.add(1);
    }

     */

    /**
     * 读文件，并初始化地图
     * @throws IOException
     */
    public void initMap() throws IOException {
        FileReader fileReader = new FileReader("src/cn/edu/whut/sept/zuul/GameMap.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s;
        s=bufferedReader.readLine();
        this.totalRoom=Integer.parseInt(s);
        //创建房间
        for(int i=1;i<=this.totalRoom;i++) {
            String description = bufferedReader.readLine();
            s = bufferedReader.readLine();
            int roomType = Integer.parseInt(s);
            Room newRoom = new Room(description, roomType);
            idRoomMap.put(newRoom.getId(), newRoom);
        }
        //对于每个房间，进行以下的识别
        for(int i=1;i<=this.totalRoom;i++){
            //读取路口数
            Room nowRoom = idRoomMap.get(i);
            s=bufferedReader.readLine();
            int exitNum=Integer.parseInt(s);
            //读路口
            for(int j=0;j<exitNum;j++){
                s=bufferedReader.readLine();
                String direction,roomName;
                Scanner tokenizer = new Scanner(s);
                direction = tokenizer.next();
                roomName = tokenizer.next();
                nowRoom.setExit(direction,idRoomMap.get(Integer.parseInt(roomName)));
            }
            //读物品
            s=bufferedReader.readLine();
            int itemNum=Integer.parseInt(s);
            for(int j=0;j<itemNum;j++){
                String itemName,itemDescription,weight;
                itemName=bufferedReader.readLine();
                itemDescription=bufferedReader.readLine();
                weight=bufferedReader.readLine();
                nowRoom.addNewThings(itemName,itemDescription,Integer.parseInt(weight));
            }
        }
        currentRoom = idRoomMap.get(1);  // start game outside
        stack.add(1);
    }
    /**
     *  游戏主控循环，直到用户输入退出命令后结束整个程序.
     */
    public void play()
    {
        printWelcome();
        Integer finished = 0;
        while (finished == 0) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * 向用户输出欢迎信息.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Koprulu Sector!");
        System.out.println("Koprulu Sector is a piece of sh*t .");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * 用于选择执行哪一种操作
     * 简化了之前的if语句，让它更加合理
     * @param command 命令
     * @param param 操作的种类
     * @return 返回程序是否继续进行
     */
    public Integer selectCommand(String param, Command command)
    {
        HashMap<String, Function<Command, Integer>> map = new HashMap<>();
        map.put("help", this::printHelp);
        map.put("go", this::goRoom);
        map.put("quit", this::quit);
        map.put("look",this::look);
        map.put("back",this::back);
        map.put("take", this::take);
        map.put("drop", this::drop);
        map.put("items", this::showItems);
        map.put("eat", this::eat);
        return map.get(param).apply(command);
    }

    /**
     * 创建一个玩家
     */
    private void createPlayers()
    {
        Player player;
        player = new Player(8, "Raven", 1);
        nowPlayer = player;
    }
    /**
     * 执行用户输入的游戏指令.
     * @param command 待处理的游戏指令，由解析器从用户输入内容生成.
     * @return 如果执行的是游戏结束指令，则返回true，否则返回false.
     */
    private Integer processCommand(Command command)
    {
        Integer wantToQuit;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return 0;
        }
        String commandWord = command.getCommandWord();
        wantToQuit = selectCommand(commandWord, command);
        return wantToQuit;

    }

    // implementations of user commands:

    /**
     * 执行help指令，在终端打印游戏帮助信息.
     * 此处会输出游戏中用户可以输入的命令列表
     */
    private Integer printHelp(Command command)
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        return 0;
    }

    /**
     * 执行go指令，向房间的指定方向出口移动，如果该出口连接了另一个房间，则会进入该房间，
     * 否则打印输出错误提示信息.
     */
    private Integer goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return 0;
        }
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            if(currentRoom.getTrap()==1) {
                System.out.println("You were transferred to a random room");
                currentRoom = randomToRoom(currentRoom);
            }
            stack.addFirst(currentRoom.getId());
            nowPlayer.setCurrentRoomId(currentRoom.getId());
            System.out.println(currentRoom.getLongDescription());
        }
        return 0;
    }

    /**
     *
     * @param command 命令编号look
     * @return 如果有东西返回true，否则为false
     */
    private Integer look(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Look what?");
        }
        else{
           currentRoom.showThings();
        }
        return 0;
    }

    /**
     * 返回上一房间
     * @param command 指令back
     * @return 0/1
     */
    private Integer back(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
        }
        else {
            Room lastRoom = backLastRoom(currentRoom);
            try{
                currentRoom = lastRoom;
                System.out.println(currentRoom.getLongDescription());
            }catch (Exception e){
                System.out.println("You have no place to HIDE!!!!!");
            }
        }
        return 0;
    }

    /**
     * 拿物品
     * @param command 命令take
     * @return 0/1
     */
    private Integer take(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return 0;
        }
        String takeItemName = command.getSecondWord();
        int pos = -1;
        ArrayList<Things> staff = currentRoom.getStaff();

        for(int i = 0; i < staff.size(); i++) {
            Things thing = staff.get(i);
            if(thing.getName().equals(takeItemName)) {
                pos = i;
                break;
            }
        }
        if(pos == -1) {
            System.out.println("you can't take the item that doesn't exist");
            return 0;
        }
        if(nowPlayer.takeThings(staff.get(pos))) {
            staff.remove(pos);
            System.out.println("Successfully take");
        }
        else {
            System.out.println("no room for staff,you have to leave sth");
        }
        return 0;
    }

    /**
     * 丢弃物品
     * @param command 指令drop
     * @return 0/1
     */
    private Integer drop(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return 0;
        }
        String dropItemName = command.getSecondWord();
        Things thing = nowPlayer.dropThings(dropItemName);
        if(thing == null) {
            System.out.println("you don't have this item!");
            return 0;
        }
        currentRoom.addNewThings(thing.getName(), thing.getDescription(), thing.getWeight());
        System.out.println("Successfully drop");
        return 0;
    }

    /**
     * 展示房间内物品
     * @param command 指令show
     * @return 0/1
     */
    private Integer showItems(Command command)
    {
        currentRoom.showThings();
        nowPlayer.showThings();
        return 0;
    }

    /**
     * 吃饼干
     * @param command 指令Eat
     * @return 1/0
     */
    private Integer eat(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Eat what?");
            return 0;
        }
        String food = command.getSecondWord();
        if(!food.equals("cookie")) {
            System.out.println("you can't eat " + food);
            return 0;
        }
        nowPlayer.eatCookie();
        return 0;
    }

    /**
     * 随机跳转
     * @param currentRoom 当前房间
     * @return 跳转后的房间
     */
    private Room randomToRoom(Room currentRoom) {
        int randomPlace;
        do{
            //防一手原地tp
            Random rand = new Random();
            randomPlace = rand.nextInt(totalRoom);
        } while(randomPlace == currentRoom.getId());
        currentRoom = idRoomMap.get(randomPlace);
        return currentRoom;
    }

    /**
     * 回到上一房间
     * @param currentRoom 当前房间
     * @return 返回后的房间
     */
    private Room backLastRoom(Room currentRoom){
        try{
            stack.removeFirst();
            return idRoomMap.get(stack.peek());
        } catch (Exception e){
            return currentRoom;
        }
    }

    /**
     * 退出
     * @param command 指令 quit
     * @return 没什么好说的
     */
    private Integer quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return 0;
        }
        else {
            return 1;  // signal that we want to quit
        }
    }

}