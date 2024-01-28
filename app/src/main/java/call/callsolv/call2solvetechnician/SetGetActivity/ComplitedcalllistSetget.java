package call.callsolv.call2solvetechnician.SetGetActivity;

public class ComplitedcalllistSetget {
    private String bid;
    private String bookid;
    private String calldatetime;
    private int srvcchrg;
    private String jobcmpltdt;
    private String jobcmplttm;
    private String chrgtypname;
    public ComplitedcalllistSetget(String bid,String bookid,String calldatetime,int srvcchrg,String jobcmpltdt,String jobcmplttm,String chrgtypname){
        this.bid =bid;
        this.bookid =bookid;
        this.calldatetime =calldatetime;
        this.srvcchrg =srvcchrg;
        this.jobcmpltdt =jobcmpltdt;
        this.jobcmplttm =jobcmplttm;
        this.chrgtypname =chrgtypname;
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

    public int getSrvcchrg() {
        return srvcchrg;
    }

    public void setSrvcchrg(int srvcchrg) {
        this.srvcchrg = srvcchrg;
    }

    public String getJobcmpltdt() {
        return jobcmpltdt;
    }

    public void setJobcmpltdt(String jobcmpltdt) {
        this.jobcmpltdt = jobcmpltdt;
    }

    public String getJobcmplttm() {
        return jobcmplttm;
    }

    public void setJobcmplttm(String jobcmplttm) {
        this.jobcmplttm = jobcmplttm;
    }

    public String getChrgtypname() {
        return chrgtypname;
    }

    public void setChrgtypname(String chrgtypname) {
        this.chrgtypname = chrgtypname;
    }
}
