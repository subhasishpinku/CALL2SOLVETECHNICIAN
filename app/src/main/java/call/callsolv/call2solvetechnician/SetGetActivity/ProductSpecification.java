package call.callsolv.call2solvetechnician.SetGetActivity;

public class ProductSpecification {
    private String productfound;
    private String productcategory;
    private String product;
    private String img;

    public ProductSpecification(String productfound,String productcategory,String product,String img){
        this.productfound = productfound;
        this.productcategory =productcategory;
        this.product = product;
        this.img=img;
    }

    public String getProductfound() {
        return productfound;
    }

    public void setProductfound(String productfound) {
        this.productfound = productfound;
    }

    public String getProductcategory() {
        return productcategory;
    }

    public void setProductcategory(String productcategory) {
        this.productcategory = productcategory;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
