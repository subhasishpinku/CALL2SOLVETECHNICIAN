package call.callsolv.call2solvetechnician.SetGetActivity;

public class RejectcallListSetGet {
    private String sl;
    private String bid;
    private String bookid;
    private String calldatetime;
    private String srvcchrg;
    private String cncldt;
    private String cnclresn;
    private String chrgtypname;

    public RejectcallListSetGet(String sl,String bid,String bookid,String calldatetime,String srvcchrg,String cncldt,String cnclresn,String chrgtypname){
        this.sl=sl;
        this.bid = bid;
        this.bookid =bookid;
        this.calldatetime =calldatetime;
        this.srvcchrg =srvcchrg;
        this.cncldt =cncldt;
        this.cnclresn =cnclresn;
        this.chrgtypname =chrgtypname;
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

    public String getCalldatetime() {
        return calldatetime;
    }

    public void setCalldatetime(String calldatetime) {
        this.calldatetime = calldatetime;
    }

    public String getSrvcchrg() {
        return srvcchrg;
    }

    public void setSrvcchrg(String srvcchrg) {
        this.srvcchrg = srvcchrg;
    }

    public String getCncldt() {
        return cncldt;
    }

    public void setCncldt(String cncldt) {
        this.cncldt = cncldt;
    }

    public String getCnclresn() {
        return cnclresn;
    }

    public void setCnclresn(String cnclresn) {
        this.cnclresn = cnclresn;
    }

    public String getChrgtypname() {
        return chrgtypname;
    }

    public void setChrgtypname(String chrgtypname) {
        this.chrgtypname = chrgtypname;
    }
}
