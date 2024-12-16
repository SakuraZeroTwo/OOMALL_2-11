package cn.edu.xmu.oomall.customer.controller.dto;

public class ResponseWrapper {
    private String errmsg;
    private Object data;
    private int errno;

    public ResponseWrapper(String errmsg, Object data, int errno) {
        this.errmsg = errmsg;
        this.data = data;
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "errmsg='" + errmsg + '\'' +
                ", data=" + data +
                ", errno=" + errno +
                '}';
    }
}