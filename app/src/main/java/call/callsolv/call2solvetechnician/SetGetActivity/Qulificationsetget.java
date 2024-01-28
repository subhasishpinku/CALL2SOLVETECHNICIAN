package call.callsolv.call2solvetechnician.SetGetActivity;

public class Qulificationsetget {
    private String educationfound;
    private String examname;
    private String boardname;
    private String examyear;
    private String examprcnt;

    public Qulificationsetget(String educationfound,String examname,String boardname,String examyear,String examprcnt){
        this.educationfound = educationfound;
        this.examname = examname;
        this.boardname =boardname;
        this.examyear = examyear;
        this.examprcnt = examprcnt;
    }

    public String getEducationfound() {
        return educationfound;
    }

    public void setEducationfound(String educationfound) {
        this.educationfound = educationfound;
    }

    public String getExamname() {
        return examname;
    }

    public void setExamname(String examname) {
        this.examname = examname;
    }

    public String getBoardname() {
        return boardname;
    }

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }

    public String getExamyear() {
        return examyear;
    }

    public void setExamyear(String examyear) {
        this.examyear = examyear;
    }

    public String getExamprcnt() {
        return examprcnt;
    }

    public void setExamprcnt(String examprcnt) {
        this.examprcnt = examprcnt;
    }
}
