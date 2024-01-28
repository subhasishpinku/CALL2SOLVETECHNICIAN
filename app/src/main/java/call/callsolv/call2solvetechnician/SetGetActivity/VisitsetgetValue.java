package call.callsolv.call2solvetechnician.SetGetActivity;

public class VisitsetgetValue {
    private String visitvalue;
    private String visitname;
    private String subproid;
    private String chrgtyp;
    public VisitsetgetValue( String visitvalue,String visitname,String subproid,String chrgtyp){
        this.visitvalue = visitvalue;
        this.visitname = visitname;
        this.subproid=subproid;
        this.chrgtyp=chrgtyp;
    }

    public String getVisitvalue() {
        return visitvalue;
    }

    public void setVisitvalue(String visitvalue) {
        this.visitvalue = visitvalue;
    }

    public String getVisitname() {
        return visitname;
    }

    public void setVisitname(String visitname) {
        this.visitname = visitname;
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
