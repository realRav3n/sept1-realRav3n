# 软件工程实训任务报告



## 1.任务概述

### 1.1任务简介

《World of Zuul》是一款简单的文本冒险游戏。用户可以在一些房间组成的迷宫中探险。现在需要通过扩展该游戏的功能使它更有趣!

### 1.2任务目标

1. 理解软件代码规范的重要性
2. 理解代码变化对软件质量带来的影响
3. 掌握基于Git的个人代码版本维护方法
4. 掌握markdown文件编写方法

## 2.任务分析

### 2.1任务重点

1. 阅读、理解和标注样例代码
2. 分析和学习代码质量特征、设计方法和编程风格
3. 运用所学方法，对开源代码进行标注
4. 对样例工程进行简单功能扩充和维护

### 2.2任务要求

#### 2.2.1 阅读和描述样例工程

- fork样例工程，并clone到本地仓库；
- 在本地开发环境上运行样例工程，理解样例工程的代码逻辑；
- 以UML图描述样例工程的组成及结构图（类及类之间的关系）
- 可结合markdown语法和mermaid插件绘制所需图形标注样例工程中的代码


####  2.2.2标注样例工程中的代码

- 基于javadoc规范标注代码，对包、类、方法、代码片段、参数和语句等代码层次进行注释（可参考Game类的标注样例）；
- 注释后的代码提交到本地代码库后，同步推送到远程代码仓库；
- 可参考ESLint、github/super-linter等开发插件了解关于代码规范的相关知识；

#### 2.2.3扩充和维护样例工程

- 对样例代码中的功能设计进行分析，找出若干设计缺陷和改进点，并进行修正或扩充，并集成到工程代码中；
- 可借助代码质量分析工具或代码规范检查工具对代码质量进行分析，发现潜在问题；

#### 2.2.4功能扩充点

- 样例工程“world-of-zuul”具备最基本的程序功能，该项目具有极大的扩展空间，各位同学可选择或自行设计系统结构优化或功能扩充需求，完成3项左右的功能扩充实现；

#### 2.2.5编写测试用例

- 针对功能改进和扩充，在项目结构中编写单元测试用例，对代码执行单元测试；

## 3.开发计划

### 3.1对项目示例代码进行建模

在fork仓库代码并clone到本地后,阅读代码后尝试理解实例代码的结构
需要对原始项目的UML图进行绘制。在对UML进行绘制之后，需要对项目进行观察和添加注释。其中着重需要添加的是项目类图

### 3.2维护样例工程

阅读样例代码后，发现如下几点问题，

1. 在Game类的processCommand()方法中，当用户输入的命令被辨认出来以后，有一系列的if语句用来分派程序到不同的地方去执行。从面向对象的设计原则来看，这种解决方案不太好，因为每当要加入一个新的命令时，就得在这一堆if语句中再加入一个if分支，最终会导致这个方法的代码膨胀得极其臃肿。
2. 在CommandWords类的isCommand()方法中判断单词是否合法的方式是通过循环逐个判断,这种方法效率较低。且存储指令的数据结构是数组，效率很低，在指令较多时耗费时间长
3. 在Game类中的createRooms方法中,使用了逐句赋值的方法来初始化地图,这种方法会造成文件内代码过多,同时修改地图也比较繁琐。
4. 类没有经过分类，没有被放入包中。直接阅读可能会令人误解内容。

在接下来的开发过程中，将利用IDEA编辑器，结合所学的面向对象的知识和数据结构的知识尝试解决

### 3.3完成项目拓展功能

根据给出的样例功能实现，确定要增加的功能扩充实现需求如下：

1. 添加物品存放系统，需要能让房间中能存在多个物品，同时能在游戏运行时像添加地图一样向房间中添加物品。具体实施方法是添加新的类things，在其中实现有关物品的属性与方法。 
2. 添加look功能，允许玩家在房间中使用look功能查看当前房间中的物品。实施方案是在game类中添加新的方法，并在room类中添加方法用于显示当前房间中的物品。
3. 添加back功能，使玩家在输入后可以回到上一个房间中，如果多次使用可以使玩家退回到起点。使用的数据结构和解决方案是在game中添加一个堆栈用来记录步数，并在game中实现back指令。
4. 新增房间类型，传送房间，进入后会被传送到任何一个非传送房间中（此处为了避免出现诸如原地传送之类的bug）。实现方法是在room类中新增一个传送房间属性，并在goRoom方法中添加对于该类房间的特判。
5. 在游戏中新建一个独立的Player类用来表示玩家
   1. 一个玩家通过登录来进行游行，可以区分为初次登录和载入存档。存档可以通过数据库或者文件的形式进行保存。
    2. 一个玩家对象应该保存玩家的姓名等基本信息，也应该保存玩家当前所在的房间。
    3. 玩家可以随身携带任意数量的物件，但随身物品的总重量不能操过某个上限值。
    4. 玩家可以拾取房间内的指定物品或丢弃身上携带的某件或全部物品，当拾取新的物件时超过了玩家可携带的重量上限，系统应给出提示。
    5. 在游戏中增加一个新的命令“items”, 可以打印出当前房间内所有的物件及总重量，以及玩家随身携带的所有物件及总重量。
    6. 在某个或某些房间中随机增加一个magic cookie（魔法饼干）物件，并增加一个“eat cookie”命令，如果玩家找到并吃掉魔法饼干，就可以增长玩家的负重能力，增加的重量视为魔法饼干本身的重量。

在确定要实现的需求后，我们便可以在IDEA编辑器中编写代码来实现需求。

### 3.4装配项目数据库与文件系统

由于项目采用的数据库是mysql, 对于数据库的编写在mysql的Workbench中完成。本人的数据库版本为mysql 5.7.22。若要运行主程序，首先配置项目结构中对应版本的jdbc，然后可以通过docker或者本地部署的方式开启数据库，最后直接运行main方法即可。

文件系统则用于存储地图、物品等难以用关系数据库存储的信息。本次实验中，我直接用txt文本存储以上信息。

### 3.5优化代码编写风格和结构

在全部功能基本实现后，利用IDEA内自带的代码检查功能对代码的格式和命名进行修改。
同时尽量保证新编写的代码和样例工程中的代码风格保持一致。

### 3.6对项目进行软件测试

在全部需求实现完成，同时对代码进行初步检查后，在IDEA编辑器内的插件JUnit来对项目进行软件测试，再根据检查的结果来修改bug或是优化代码。重复以上过程。

## 4.软件配置计划

### 4.1软件版本编码方案

采取最为广泛的“主版本号.子版本号.修正版本号”格式。详细规则如图所示:

[![YNE-DT40-Y-4-Z-FJX-I1-CV7.png](https://i.postimg.cc/L62bRrmC/YNE-DT40-Y-4-Z-FJX-I1-CV7.png)](https://postimg.cc/ThN9JNLg)

### 4.2命名规范

采用最广泛的驼峰规则，也就是变量名称首个单词小写，其余单词首字母大写。类的命名则采用每个单词首字母大写的形式。在实例化时则尽量让变量名称为变量类型的小写。

### 4.3分支管理规范

由于项目由个人独立开发，不用考虑将别人的代码进行合并的操作。一共使用两种类型的版本，-master和-develop。前者为可稳定使用的版本，后者为开发新功能的版本

- -master 分支（主分支） 稳定版本
- -develop 分支（开发分支） 最新版本

在对develop版本测试一天后没有发现问题，则将其推送为master版本。

### 4.4提交规范

计划使用基于 angular 规范的 commit

基于 angular 规范的 commit commit格式如下: <type>: <subject> <BLANK LINE> <body> type - 提交 commit 的类型 feat: 新功能 fix: 修复问题 docs: 修改文档 style: 修改代码格式(不影响逻辑功能,比如格式化.补充分号等等) refactor: 重构代码(fix bug或增加新功能不属于此范围) perf: 提升页面性能 test: 增加/修改测试用例 chore:

在实际操作中，由于对此了解较少，最终采取以下格式：

第一行：版本号

第二行：本次提交的新功能

第三行：实现新功能的具体方法

## 5.测试计划

利用Junit框架对项目中的基类中的非常规方法（即不包括get，set，show这类方法）进行白盒测试，对于不易于编写测试用例的，需要大量与用户交互并依赖用户来进行结果分析的类（即Game类）中的方法，我们设计完整的测试流程对其进行黑盒测试。

## 6.实施情况

### 6.1绘制示例代码UML图

根据实例代码的类之间的关系，可以得到如下的UML类图

![](D:\WHUT\软件工程实训\图片\原始Uml类图.png)

### 6.2 维护样例工程

在3.2中我们已经讨论了对样例工程的那些地方进行修改，在这里将具体讨论如何实现

1. if-else语句过多

   解决方法:将原有的if-else语句改为一个Hashmap的映射，其中映射类型为<String , 函数>。如此，如果读入一个字符串，程序将会调用Game中对应的命令函数。在下例中，help与对应的printHelp方法对应，go与对应的goRoom对应，quit与对应的quit对应。在输入以上串时，会调用对应的函数。

```java
 HashMap<String, Function<Command, Integer>> map = new HashMap<>();
 map.put("help", this::printHelp);
 map.put("go", this::goRoom);
 map.put("quit", this::quit);
```

2. 用循环判断合法词

   在源文件中，指令采用较为原始的数组进行存储。这样如果要判断输入的指令是否合法，必须从数组头部开始向后遍历。这样效率较低，因此将其也改为Hashmap形式。
   
   可见，将所有合法指令都和1绑定，非法指令则不出现。

```javascript
	validCommands = new HashMap<>();
	validCommands.put("go", 1);
	validCommands.put("quit", 1);
```

​		通过新增方法isCommand进行判断，读入一个命令字符串，如果命令字符串可以在Hashmap中找到映射关系，则说明有效。

```java
    public boolean isCommand(String sString)
    {
        Integer isValid = validCommands.getOrDefault(sString, 0);
        return isValid == 1;
    }
```

3.  初始化被定义在Game中

   如果初始化地图被定义在Game中，则地图信息隐私性得不到保障。且如果需要载入玩家自己的存档，必须对代码进行修改。

   解决方法:将地图信息保存在GameMap.txt文件中,程序中读这个文件来获取地图信息，如果该txt的格式发生变更，程序会报错并且自动退出。因此在实际操作中，必须对该文件进行仔细的规范。
   
4. 类的定义较为混乱

   为此，将现有的类分为多个包

   POJO包：用于存放所有的基类。目前，向其中添加Room类和Things类，其中存在对应的方法和属性。

   Command包：用于存放所有和命令判断有关的类，目前存有Command、CommandWords和Parser类

   Config包：用于存放用于读取配置文件的类

   Properties包：存放配置文件

   Util包：存放和数据库操作和文件读取写入有关的类

   最后，剩余的Game和Main放在外层，方便直接修改

### 6.3 完成项目拓展功能

以下是对于3.3中操作的具体实现过程

1. 添加物品存放系统。创建Things类用于表示物品物件，在房间对象内创建一个存储物品对象的ArrayList<Things>属性。

   Things类中属性如下，分别对应物品名称、重量和描述。

   ```java
   private String name;
   private int weight;
   private String description;
   ```

2. look指令，允许玩家查看房间内的物品。方法被定义在Room类中，主要内容如下：

   ```java
   public int showThings(){
           int sumWeight = 0;
           System.out.println("来看看这里有什么吧!");
           for(Things thing:staff){
               sumWeight += thing.getWeight();
               	System.out.println(thing.getName()+","+thing.getWeight()+","+thing.getDescription());
           }
           System.out.println("总重量为 : "+sumWeight);
           return staff.size();
       }
   ```

   同时，需要在CommandWord中添加对应映射，方法如6.2.2中所示

3. “back”命令，添加back作为合法指令,并建立对应的方法。在Room类中添加一个表示房间编号的属性，在Game类中添加一个编号对房间的映射。back指令仅有指令本身这一个关键词，在输入后，会返回到玩家所处的上一个房间。

   为实现这一功能，需要在game中新增一个栈。在此次实验中，我通过双端队列Deque实现，每次前进一步，就像其中新增一个房间id，使用back指令时，则回退。在栈为空时，则会输出“no where to hide”。

   ```java
   private Deque<Integer> stack;
   ```

   

4. 随机传送。在Room类中增加一个属性，表示是否为随机传送的房间。在goRoom方法中额外判断进入的房间是否为随机传送房间，如果是，则在房间编号中随机一个（需要保证和进入的房间编号不同）作为当前房间编号。修改当前房间对象即可

   ```java
   private int trap; //是否为传送房间
   ```

   ```java
   private Room randomToRoom(Room currentRoom) {
           int randomPlace;
           do{
               //防一手原地tp
               Random rand = new Random();
               randomPlace = rand.nextInt(totalRoom);
           } while(randomPlace == currentRoom.getId());
    //这里之前没有解释过，idRoomMap是一个Hashmap，可以实现id与Room间的映射，反过来的映射通过getID实现
           currentRoom = idRoomMap.get(randomPlace);
           return currentRoom;
       }
   ```

   

5. Player类的相关操作。

   首先需要新增一个player类作为基点。player类拥有以下属性：

   ```java
   private String name; //名字
   private int currentRoomId; //目前所在房间编号
   private ArrayList<Things> ownThings; //拥有物件
   ```

   

   1. 登录操作。首先，为了实现登录功能，在这里使用了数据库对玩家进行记录。具体方法在3.4中进行讲述。

      这个方法首先会根据输入的用户名在数据库中判断是否存在该用户。如果存在，则会读取数据库中的信息在程序中对该用户进行实例化。否则，会在数据库中创建一个新用户，并在程序中按照初始信息进行实例化。

      ```java
      public void login(){
              System.out.println("你好，请输入您的用户名！");
              System.out.print(">>");
              String input = new Scanner(System.in).nextLine();
              DBUtil db = new DBUtil();
              try{
                  db.getConnection();
                  String sqlTest = "SELECT * FROM `user` WHERE userName='"+input+"'";
                  ResultSet rs = db.executeQuery(sqlTest,null);
                  if(rs.next()){
                      System.out.println("欢迎回来！");
                      this.nowPlayer = new Player(rs.getInt("capacity"),rs.getString("userName"),rs.getInt("nowRoomId"));
                  }else{
                      String save_user_sql = "call `save_user`(?,?,?,@res);";
                      Object[] param = new Object[] { input, 1 8};
                      if (db.executeUpdate(save_user_sql, param) > 0) {
                          System.out.println("未检测到您的旧有账户，已为您创建新账户");
                          this.nowPlayer = new Player(8, input, 1);
                      }
                      else{
                          System.out.println("连接异常!");
                      }
                  }
                  this.currentRoom=idRoomMap.get(this.nowPlayer.getCurrentRoomId());
                  stack.add(this.currentRoom.getId());
              }catch (Exception e){
                  e.printStackTrace();
              }finally {
                  db.closeAll();
              }
              play();
          }
      ```

      这里默认玩家处于入口处，且最大载荷为8.

   2. 在Player类中加入相应属性和获取/修改属性的方法即可。具体实现为getter、setter方法和构造函数。

   3. 加入现在负重的属性，在获取物品时判断加入物品是否导致现有负重超过负重上限即可。

      ```java
      private int limitWeight, currentWeight; // 最大载荷 当前载荷
      ```

      

   4. 添加trop和take作为合法指令,并建立对应的方法。其逻辑为读取玩家捡起或放下的物品，在Player中添加dropThings和takeThings方法，输入物品名称，玩家背包中新增或减少物品，同时房间中添加addNewThings方法和loseThings方法，效果为新增或减少房间内物品。最后在Game类中添加Take方法和Drop方法，分别调用以上两个方法。

   5. 打印物品。添加items作为合法指令,并建立对应的方法。遍历玩家和房间内的物品数组，分别输出其名字以及重量和即可。

   6. 魔法饼干。在Room类中添加“是否存在魔法饼干”这一属性。如果存在，它将以每个5kg的重量以物品形式存在。同时，新增指令eat，允许玩家食用物品。目前仅能使用魔法饼干，因此在编写代码时，采取的是使用if语句特判。食用后，会将当前用户的载荷增大10kg。

      ```java
      private Integer eat(Command command)
          {
              if(!command.hasSecondWord()) {
                  System.out.println("Eat啥?");
                  return 0;
              }
              String food = command.getSecondWord();
              if(!food.equals("cookie")) {
                  System.out.println("你不能吃 " + food);
                  return 0;
              }
              nowPlayer.eatCookie();
              return 0;
          }
      ```

   7. 保存并退出。如果没有正常退出，游戏是不会保存玩家数据的。必须通过Exit指令正藏退出才会记录：Exit指令会读取当前玩家的信息，并将其update进数据库。

      ```java
      private Integer quit(Command command) {
              if(command.hasSecondWord()) {
                  System.out.println("Quit啥?");
                  return 0;
              }
              else {
                  DBUtil db = new DBUtil();
                  try{
                      db.getConnection();
                      String save_progress_sql = "call `update_user`(?,?,?);";
                      Object[] param = new Object[] { nowPlayer.getName(),this.currentRoom,nowPlayer.getLimitWeight()};
                      if (db.executeUpdate(save_progress_sql, param) > 0) {
                          System.out.println("保存中...");
                      }else{
                          System.out.println("连接失败！！！!");
                      }
                      this.currentRoom=idRoomMap.get(this.nowPlayer.getCurrentRoomId());
                      stack.add(this.currentRoom.getId());
                  }catch (Exception e){
                      e.printStackTrace();
                  }finally {
                      db.closeAll();
                  }
                  return 1;  // signal that we want to quit
              }
          }
      ```

6.文件系统

为了能让玩家可以更方便的载入自己的地图，我将Game类中的初始化方法改成了从文件中读取信息。

首先，在Properties包中，我添加了游戏地图文件GameMap.txt。其格式为

```
房间数量
 房间描述（重复若干次）
 是否为传送房间（重复若干次）
以下内容重复多次：
 每个房间的出口方向 出口房间编号
 房间内物品数
  物品名
  物品重量
  物品描述
```

在此之后在Room中搭配对应的读取方法，该方法实现了从txt中读取信息，并将其内容装配的功能，实现细节如注释所示：

```java
public void initMap() throws IOException {
        FileReader fileReader = new              FileReader("src/cn/edu/whut/sept/zuul/Properties/GameMap.txt");
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
    }
```

以上为添加的主要内容，其余内容可在原码中查看。该仓库位于https://github.com/realRav3n/sept1-realRav3n中。

### 6.4测试结果

1.黑盒测试

黑盒测试用于在未知项目逻辑的情况下查看流程是否生效

因此，先预先载入一张地图，其中入口处有一个重量为5kg的布料、10kg的石头和重量为65535kg的中子物质，剧院中有一块魔法饼干，传送处可以让玩家随机传送

![](D:\WHUT\软件工程实训\图片\QQ截图20230103171705.png)

因此，需要设计一个流程让其能够囊括所有情况

```自然语言
1.登录系统，任意输入一个字符串
2.尝试使用look指令查看入口的物件，尝试在look指令后添加内容
3.尝试通过take拿取布料、中子物质、石头和空
4.尝试通过drop丢弃布料，尝试丢弃中子物质，尝试丢弃空
5.尝试通过eat指令食用布料、食用中子物质、食用空
6.输入help指令
7.输入items指令查看
8.输入go 、输入go EAST、输入go north、输入go east
9.输入look指令
10.输入take cookie
11.输入eat cookie
12.输入look指令
13.输入back指令，直无法进一步返回，输入take stone，再drop stone
14.再次输入go east
15.再次输入go east
16.输入back，直到无法进一步返回
17.输入exit
18.重新登录
19.输入take stone
```

对应结果应当如下：

```
1.成功登录
2.显示布料、石头、中子物质的信息、重量，并显示总质量
3.布料拿取成功，中子物质、石头和空拿取失败
4.布料丢弃成功，其余丢弃失败
5.全部食用失败
6.出现go、quit、help、look、back、take、drop、items和eat指令
7.可以看见自己身上为空，地上有布料、石头和中子物质，同时显示各自总质量
8.前三个指令失败，第四个成功，并进入到位于东侧的剧院中
9.观察到地上有一块魔法饼干
10.显示拿取成功
11.显示食用成功，且载重增大到18kg
12.显示空
13.返回到大门，然后显示"no where to hide",拿取石头成功，放下成功
14.进入剧院
15.进入传送处，最后到达随机位置
16.会沿途返回，直到到达起点
17.显示保存成功
18.显示登录成功
19.显示石头拿取成功
```

经过以上过程，能尽可能覆盖所有功能

2.白盒测试

白盒测试是测试人员在知晓逻辑后对代码做出的测试。在这里，我选择了Idea自带的Junit用于进行测试。这些测试都被存放在test包中，以下代码以room类的测试为例：

```java
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
```

以上三个测试分别对设置出口、添加物品和显示物品三个方法做出测试。如果测试成功，则在控制台打印出True，否则打印false，以下是运行结果，结果在预期之中

![QQ截图20230103183538](D:\WHUT\软件工程实训\图片\QQ截图20230103183538.png)

![QQ截图20230103183557](D:\WHUT\软件工程实训\图片\QQ截图20230103183557.png)

![QQ截图20230103183609](D:\WHUT\软件工程实训\图片\QQ截图20230103183609.png)

其余测试在test包中，现已随var1.0.1一同上传

### 6.5提交情况

第一次提交完成了源文件的注释（var0.1.0）

第二次提交完成了对于if-else语句的优化（var0.1.1）

第三次提交新增了Things类，并添加了look指令用于检索房间内的物品（var0.2.0）

第四次提交完善了Room类，在其中新增了传送房间这一属性，同时完成了随机跳转的编写（var0.2.1）

第五次提交新增了Back指令，允许玩家返回上个房间（var0.2.2）

第六次提交新增了Player类，同时完成了take、drop、eat、items指令的编写，实现了游戏全部基础拓展功能（var0.3.0）

第七次提交完善了对于拓展功能注释的编写

第八次提交新增文件系统，允许直接从txt中载入地图（var0.4.0）

第九次提交新增数据库系统，可将玩家信息上传到数据库中，并提供载入和新建功能（var0.9.0）

第十次提交完成了对于部分游戏bug的修复，并添加了注释（var1.0.0）

![](D:\WHUT\软件工程实训\图片\全过程.png)

第十一次提交与1.3日进行，修复了若干bug，并添加测试类（var1.0.1）

## 7.实施过程问题记录与分析

### 7.1 fork失败

在刚刚开始项目时，我曾经尝试将wutcst中我的示例项目fork到我的github上。但是显示不允许fork

![](D:\WHUT\软件工程实训\图片\QQ截图20230103184537.png)

最终，将wutcst上的项目公开之后成功的fork下来。fork后我将项目调回private。

![](D:\WHUT\软件工程实训\图片\1.png)

### 7.2 无法提交至github

问题：一共出现过三次，在电脑开启加速器或者裸连github的情况下能从浏览器成功登上github，但是idea无法提交或上传，显示连接超时。

解决方案：等待一小时，重新尝试上传即可成功上传

## 8.任务总结

在这次实验中，我最大的收获不是代码编写上的提升，而是对于markdown文件的编写和对git操作的练习。

如果要更好的从事互联网行业，只会写代码是远远不够的，更加需要的是编写文档的能力。而markdown这种符号语言就很好的满足了开发人员的需求。而这次实验我首次完整的通过markdown文件编写了完整的文档，并在编写文档的同时完善了我的代码程序。当然，这次的文档仍然有很多缺陷，而且我对于typora的使用仍然不是很熟悉。但是无论如何，做出尝试就能获得经验。

此外，我也对github有了更深层次的了解。虽然这并不是我首次编写包含版本控制的程序，但是这次的体验也大大加强了我对于github运用的熟练度。对于我今后的学习工作都有很大的意义。

同时，我也对面向对象有了更深的认识。当然，这次也有一些遗憾，比如没能使用spring框架重构这次的代码，也没能将代码改造为MVC结构。但我今后会做出尝试并努力学习相关知识，争取下次取得更好的成果。

## 9.参考文献

1. [基于 angular 规范的 commit](https://www.shuzhiduo.com/A/lk5amGwZ51/)
2. [在IDEA上进行JUnit测试](https://blog.csdn.net/qq_44028290/article/details/108903857)
3. [驼峰命名法](https://baike.baidu.com/item/%E9%A9%BC%E5%B3%B0%E5%91%BD%E5%90%8D%E6%B3%95/7560610)
4. [Hashmap详解](https://blog.csdn.net/shi_xiansheng/article/details/117792691)
5. [Deque详解](https://blog.csdn.net/SeekN/article/details/114231727)
6. [CodeStyle详解](https://blog.csdn.net/m0_47305114/article/details/107463791)





