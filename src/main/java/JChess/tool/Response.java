package JChess.tool;

public class Response {
    Object obj;

    public Response () {
        this.obj = null;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isEmpty () {
        return obj == null ? true : false;
    }

}
