package cn.edu.whut.sept.zuul.POJO;

public class Things {
    private String name;
    private int weight;
    private String description;

    public Things(String name, int weight, String description) {
        this.name = name;
        this.weight = weight;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }
}
