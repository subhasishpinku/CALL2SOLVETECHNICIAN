package call.callsolv.call2solvetechnician.SetGetActivity;

public class Datacollectionsetget {
    private int image;
    private String pname;
    private String dname;
    private String jobid;
    private String amount;

    public Datacollectionsetget(int image,String pname,String dname,String jobid,String amount){
        this.image =image;
        this.pname =pname;
        this.dname =dname;
        this.jobid =jobid;
        this.amount =amount;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
