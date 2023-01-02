package cn.edu.whut.sept.zuul.util;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
    public String readMap() throws IOException {
        FileReader fileReader = new FileReader("/src/cn/edu/whut/sept/zuul/GameMap.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s;
        s = bufferedReader.readLine();
        fileReader.close();
        return s;
    }
}