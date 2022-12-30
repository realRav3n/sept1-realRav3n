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

import cn.edu.whut.sept.zuul.POJO.Room;
import cn.edu.whut.sept.zuul.POJO.Things;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private HashMap<Integer,Room> idRoomMap =new HashMap<>();

    private int totalRoom = 6;


    /**
     * 创建游戏并初始化内部数据和解析器.
     */
    public Game()
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * 创建所有房间对象并连接其出口用以构建迷宫.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office,transport;
        Things apple;

        //创建房间
        outside = new Room(1,"outside the main entrance of the university");
        theater = new Room(3,"in a lecture theater");
        pub = new Room(2,"in the campus pub");
        lab = new Room(5,"in a computing lab");
        office = new Room(6,"in the computing admin office");
        transport =new Room(4,"middle there is a magical spring,you goes up..");
        //建立索引
        idRoomMap.put(1,outside);
        idRoomMap.put(2,pub);
        idRoomMap.put(3,theater);
        idRoomMap.put(4,transport);
        idRoomMap.put(5,lab);
        idRoomMap.put(6,office);
        //创建初始物品
        apple =new Things("apple",10,"我是一个朴实无华的苹果");
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);
        theater.setExit("east",transport);

        transport.setExit("west",theater);
        transport.setTrap(true);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        outside.addNewThings(apple);

        currentRoom = outside;  // start game outside
    }

    /**
     *  游戏主控循环，直到用户输入退出命令后结束整个程序.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
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
        return map.get(param).apply(command);

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
            if(currentRoom.getTrap())
            {
                System.out.println("You were transferred to a random room");
                currentRoom = randomToRoom(currentRoom);
            }
            System.out.println(currentRoom.getLongDescription());
        }
        return 0;
    }

    /**
     *
     * @param command 命令编号
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
     * 执行Quit指令，用户退出游戏。如果用户在命令中输入了其他参数，则进一步询问用户是否真的退出.
     * @return 如果游戏需要退出则返回true，否则返回false.
     */
    private Room randomToRoom(Room currentRoom)
    {
        int randomPlace;
        do{
            //防一手原地tp
            Random rand = new Random();
            randomPlace = rand.nextInt(totalRoom);
        } while(randomPlace == currentRoom.getId());
        currentRoom = idRoomMap.get(randomPlace);
        return currentRoom;
    }
    private Integer quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return 0;
        }
        else {
            return 1;  // signal that we want to quit
        }
    }

}