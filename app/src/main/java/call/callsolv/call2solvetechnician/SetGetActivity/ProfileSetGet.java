package call.callsolv.call2solvetechnician.SetGetActivity;

public class ProfileSetGet {
    private String msg;
    private String Message;
    private String loginid;
    private String hubid;
    private String spokeid;
    private String techid;
//    public ProfileSetGet() {
//
//    }
    public ProfileSetGet(String msg,String Message,String loginid,String hubid,String spokeid,String techid){
        this.msg = msg;
        this.Message = Message;
        this.loginid =loginid;
        this.hubid = hubid;
        this.spokeid = spokeid;
        this.techid=techid;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getHubid() {
        return hubid;
    }

    public void setHubid(String hubid) {
        this.hubid = hubid;
    }

    public String getSpokeid() {
        return spokeid;
    }

    public void setSpokeid(String spokeid) {
        this.spokeid = spokeid;
    }

    public String getTechid() {
        return techid;
    }

    public void setTechid(String techid) {
        this.techid = techid;
    }
}
