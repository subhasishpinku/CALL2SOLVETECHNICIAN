package call.callsolv.call2solvetechnician.SetGetActivity;

public class Kycsetget {
    private String docfound;
    private String docname;
    private String docno;
    private String filename;

    public Kycsetget(String docfound,String docname,String docno,String filename){
        this.docfound = docfound;
        this.docname = docname;
        this.docno=docno;
        this.filename=filename;
    }

    public String getDocfound() {
        return docfound;
    }

    public void setDocfound(String docfound) {
        this.docfound = docfound;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
