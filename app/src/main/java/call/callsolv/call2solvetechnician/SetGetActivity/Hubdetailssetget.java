package call.callsolv.call2solvetechnician.SetGetActivity;

public class Hubdetailssetget {
    private String hubcntctname;
    private String hubcntctdesg;
    private String hubcntctphn;
    private String hubcntctmail;

    public Hubdetailssetget(String hubcntctname,String hubcntctdesg,String hubcntctphn,String hubcntctmail){
        this.hubcntctname =hubcntctname;
        this.hubcntctdesg =hubcntctdesg;
        this.hubcntctphn =hubcntctphn;
        this.hubcntctmail =hubcntctmail;
    }

    public String getHubcntctname() {
        return hubcntctname;
    }

    public void setHubcntctname(String hubcntctname) {
        this.hubcntctname = hubcntctname;
    }

    public String getHubcntctdesg() {
        return hubcntctdesg;
    }

    public void setHubcntctdesg(String hubcntctdesg) {
        this.hubcntctdesg = hubcntctdesg;
    }

    public String getHubcntctphn() {
        return hubcntctphn;
    }

    public void setHubcntctphn(String hubcntctphn) {
        this.hubcntctphn = hubcntctphn;
    }

    public String getHubcntctmail() {
        return hubcntctmail;
    }

    public void setHubcntctmail(String hubcntctmail) {
        this.hubcntctmail = hubcntctmail;
    }
}
