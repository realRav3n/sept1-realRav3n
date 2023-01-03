package cn.edu.whut.sept.zuul.Command;

public class Command
{
    private String commandWord;
    private String secondWord;

    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    public String getCommandWord()
    {
        return commandWord;
    }


    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * 判断用户的输入指令是否合法
     * @return 如果用户输入指令存在第一个词，则返回true，否则返回false.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }
    /**
     * 判断指令是否存在第二个词
     * @return 如果指令存在第二个词，则返回true，否则返回false.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}
