package cn.edu.whut.sept.zuul;

public class CommandWords
{
    private static final String[] validCommands = {
            "go", "quit", "help","look","back"
    };

    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * 判断用户的输入指令是否合法
     * @param aString 待判断是否合法的用户输入指令，
     * @return 如果合法的用户输入指令，则返回true，否则返回false.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        return false;
    }

    /**
     * 输出所有指令
     */
    public void showAll()
    {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
