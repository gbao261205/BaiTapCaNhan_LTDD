package Module;

public class MonHoc {
    private String name;
    private String description;
    private int pic;
    public MonHoc(String name, String description, int pic) {
        this.name = name;
        this.description = description;
        this.pic = pic;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getPic() {
        return pic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
