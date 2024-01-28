package call.callsolv.call2solvetechnician.SetGetActivity;

public class AddchargeValue {
    private String addchagevalue;
    private String addchagename;
    private String subproid;
    private String chrgtyp;
    public AddchargeValue(String addchagevalue,String addchagename,String subproid,String chrgtyp){
        this.addchagevalue = addchagevalue;
        this.addchagename = addchagename;
        this.subproid=subproid;
        this.chrgtyp=chrgtyp;
    }

    public String getAddchagevalue() {
        return addchagevalue;
    }

    public void setAddchagevalue(String addchagevalue) {
        this.addchagevalue = addchagevalue;
    }

    public String getAddchagename() {
        return addchagename;
    }

    public void setAddchagename(String addchagename) {
        this.addchagename = addchagename;
    }

    public String getSubproid() {
        return subproid;
    }

    public void setSubproid(String subproid) {
        this.subproid = subproid;
    }

    public String getChrgtyp() {
        return chrgtyp;
    }

    public void setChrgtyp(String chrgtyp) {
        this.chrgtyp = chrgtyp;
    }
}
