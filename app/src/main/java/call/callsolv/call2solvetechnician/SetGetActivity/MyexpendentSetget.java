package call.callsolv.call2solvetechnician.SetGetActivity;

public class MyexpendentSetget {
    private String sl;
    private String jobcode;
    private String calldate;
    private int amnt;
    private String expndfr;
    public MyexpendentSetget(String sl,String jobcode,String calldate,int amnt,String expndfr){
        this.sl=sl;
        this.jobcode = jobcode;
        this.calldate = calldate;
        this.amnt = amnt;
        this.expndfr = expndfr;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getJobcode() {
        return jobcode;
    }

    public void setJobcode(String jobcode) {
        this.jobcode = jobcode;
    }

    public String getCalldate() {
        return calldate;
    }

    public void setCalldate(String calldate) {
        this.calldate = calldate;
    }

    public int getAmnt() {
        return amnt;
    }

    public void setAmnt(int amnt) {
        this.amnt = amnt;
    }

    public String getExpndfr() {
        return expndfr;
    }

    public void setExpndfr(String expndfr) {
        this.expndfr = expndfr;
    }
}


