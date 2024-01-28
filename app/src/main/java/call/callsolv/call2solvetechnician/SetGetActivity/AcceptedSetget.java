package call.callsolv.call2solvetechnician.SetGetActivity;

public class AcceptedSetget {
    private String bookcode;
    private String bookrowid;
    private String prdctname;
    private String prdcticon;
    private String customername;
    private String customerphn;
    private String customeradrs;
    private String timeage;
    private String startjob;
    private String trackflag;
    private String asigndate;
    private String asigntime;
    public AcceptedSetget(String bookcode,String bookrowid,String prdctname,String prdcticon,
                          String customername,String customerphn,String customeradrs,
                          String timeage,String startjob,String trackflag,String asigndate,String asigntime){
        this.bookrowid = bookrowid;
        this.prdctname = prdctname;
        this.prdcticon = prdcticon;
        this.customername = customername;
        this.customerphn = customerphn;
        this.customeradrs = customeradrs;
        this.timeage = timeage;
        this.startjob = startjob;
        this.bookcode=bookcode;
        this.trackflag=trackflag;
        this.asigndate=asigndate;
        this.asigntime=asigntime;
    }

    public String getBookcode() {
        return bookcode;
    }

    public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

    public String getBookrowid() {
        return bookrowid;
    }

    public void setBookrowid(String bookrowid) {
        this.bookrowid = bookrowid;
    }

    public String getPrdctname() {
        return prdctname;
    }

    public void setPrdctname(String prdctname) {
        this.prdctname = prdctname;
    }

    public String getPrdcticon() {
        return prdcticon;
    }

    public void setPrdcticon(String prdcticon) {
        this.prdcticon = prdcticon;
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

    public String getCustomeradrs() {
        return customeradrs;
    }

    public void setCustomeradrs(String customeradrs) {
        this.customeradrs = customeradrs;
    }

    public String getTimeage() {
        return timeage;
    }

    public void setTimeage(String timeage) {
        this.timeage = timeage;
    }

    public String getStartjob() {
        return startjob;
    }

    public void setStartjob(String startjob) {
        this.startjob = startjob;
    }

    public String getTrackflag() {
        return trackflag;
    }

    public void setTrackflag(String trackflag) {
        this.trackflag = trackflag;
    }

    public String getAsigndate() {
        return asigndate;
    }

    public void setAsigndate(String asigndate) {
        this.asigndate = asigndate;
    }

    public String getAsigntime() {
        return asigntime;
    }

    public void setAsigntime(String asigntime) {
        this.asigntime = asigntime;
    }
}
