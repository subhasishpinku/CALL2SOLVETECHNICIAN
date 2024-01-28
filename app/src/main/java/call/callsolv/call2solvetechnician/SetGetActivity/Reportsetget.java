package call.callsolv.call2solvetechnician.SetGetActivity;

public class Reportsetget {
    private int image;
    private String name;
    private int total;

    public Reportsetget(int image, String name, int total){
        this.image =image;
        this.name =name;
        this.total =total;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
