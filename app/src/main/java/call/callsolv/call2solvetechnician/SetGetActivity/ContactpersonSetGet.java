package call.callsolv.call2solvetechnician.SetGetActivity;

public class ContactpersonSetGet {
    private String spokecntctname;
    private String spokecntctdesg;
    private String spokecntctphn;
    private String spokecntctmail;

    public ContactpersonSetGet(String spokecntctname,String spokecntctdesg,String spokecntctphn,String spokecntctmail){
        this.spokecntctname =spokecntctname;
        this.spokecntctdesg =spokecntctdesg;
        this.spokecntctphn =spokecntctphn;
        this.spokecntctmail =spokecntctmail;
    }

    public String getSpokecntctname() {
        return spokecntctname;
    }

    public void setSpokecntctname(String spokecntctname) {
        this.spokecntctname = spokecntctname;
    }

    public String getSpokecntctdesg() {
        return spokecntctdesg;
    }

    public void setSpokecntctdesg(String spokecntctdesg) {
        this.spokecntctdesg = spokecntctdesg;
    }

    public String getSpokecntctphn() {
        return spokecntctphn;
    }

    public void setSpokecntctphn(String spokecntctphn) {
        this.spokecntctphn = spokecntctphn;
    }

    public String getSpokecntctmail() {
        return spokecntctmail;
    }

    public void setSpokecntctmail(String spokecntctmail) {
        this.spokecntctmail = spokecntctmail;
    }
}
