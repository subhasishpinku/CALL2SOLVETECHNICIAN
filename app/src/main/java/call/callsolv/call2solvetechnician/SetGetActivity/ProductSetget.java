package call.callsolv.call2solvetechnician.SetGetActivity;

public class ProductSetget {
    private String categoryname;
    private String productname;
    private String productimage;

    public  ProductSetget(String categoryname,String productname,String productimage){
        this.categoryname = categoryname;
        this.productname =productname;
        this.productimage=productimage;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }
}
