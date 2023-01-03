package cn.edu.whut.sept.zuul.Command;

import java.util.HashMap;

public class CommandWords
{
    private final HashMap<String, Integer> validCommands;

    /**
     *没有使用传统的String数组来存储指令，而是采用一个以下方法来实例化指令，同时避免了直接操作数组
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        validCommands.put("go", 1);
        validCommands.put("quit", 1);
        validCommands.put("help", 1);
        validCommands.put("look", 1);
        validCommands.put("back", 1);
        validCommands.put("take", 1);
        validCommands.put("drop", 1);
        validCommands.put("items", 1);
        validCommands.put("eat", 1);
    }

    /**
     * 判断用户的输入指令是否合法
     * @param aString 待判断是否合法的用户输入指令，
     * @return 如果合法的用户输入指令，则返回true，否则返回false.
     */
    public boolean isCommand(String aString)
    {
        Integer isValid = validCommands.getOrDefault(aString, 0);
        return isValid == 1;

    }

    /**
     * 输出所有指令
     */
    public void showAll()
    {
        for(String i : validCommands.keySet()) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
