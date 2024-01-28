package call.callsolv.call2solvetechnician.SetGetActivity;

public class pending_rating_setget {
    private int image;
    private String pname;
    private String dName;
    private String username;
    private String phone;
    private String place;
    private String amount;
    public pending_rating_setget(int image,String pname,String dName,String username,String phone,String place,String amount){
        this.image =image;
        this.pname =pname;
        this.dName =dName;
        this.username =username;
        this.phone =phone;
        this.place =place;
        this.amount =amount;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
