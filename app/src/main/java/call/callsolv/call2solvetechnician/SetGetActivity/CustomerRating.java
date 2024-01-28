package call.callsolv.call2solvetechnician.SetGetActivity;

public class CustomerRating {
    private String sl;
    private String bid;
    private String bookid;
    private String rate;
    private String customername;
    private String customerphn;
    private String productname;
    private String jobprcsdt;
    private String chrgtype;
    public CustomerRating(String sl,String bid,String bookid,String rate,String customername,String customerphn,String productname,String jobprcsdt,String chrgtype){
        this.sl=sl;
        this.bid =bid;
        this.bookid=bookid;
        this.rate=rate;
        this.customername=customername;
        this.customerphn=customerphn;
        this.productname=productname;
        this.jobprcsdt=jobprcsdt;
        this.chrgtype=chrgtype;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomerphn() {
        return customerphn;
    }

    public void setCustomerphn(String customerphn) {
        this.customerphn = customerphn;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getJobprcsdt() {
        return jobprcsdt;
    }

    public void setJobprcsdt(String jobprcsdt) {
        this.jobprcsdt = jobprcsdt;
    }

    public String getChrgtype() {
        return chrgtype;
    }

    public void setChrgtype(String chrgtype) {
        this.chrgtype = chrgtype;
    }
}
