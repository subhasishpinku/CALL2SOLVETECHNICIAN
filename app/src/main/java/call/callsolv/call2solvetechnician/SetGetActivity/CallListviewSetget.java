package call.callsolv.call2solvetechnician.SetGetActivity;

public class CallListviewSetget {
    private String slno;
    private String bid;
    private String bookid;
    private String calldatetime;
    private String srvcchrg;
    private String jobasigndt;
    private String jobasigntime;
    private String chrgtypname;

    public CallListviewSetget(String slno,String bid,String bookid,String calldatetime,String srvcchrg,String jobasigndt,String jobasigntime,String chrgtypname){
        this.slno =slno;
        this.bid = bid;
        this.bookid =bookid;
        this.calldatetime =calldatetime;
        this.srvcchrg =srvcchrg;
        this.jobasigndt =jobasigndt;
        this.jobasigntime=jobasigntime;
        this.chrgtypname=chrgtypname;
    }

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
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

    public String getJobasigndt() {
        return jobasigndt;
    }

    public void setJobasigndt(String jobasigndt) {
        this.jobasigndt = jobasigndt;
    }

    public String getJobasigntime() {
        return jobasigntime;
    }

    public void setJobasigntime(String jobasigntime) {
        this.jobasigntime = jobasigntime;
    }

    public String getChrgtypname() {
        return chrgtypname;
    }

    public void setChrgtypname(String chrgtypname) {
        this.chrgtypname = chrgtypname;
    }
}
