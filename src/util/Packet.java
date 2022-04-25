package util;

import java.io.Serializable;

public class Packet implements Serializable {

    private int requestType;
    private String[] msg;
    private boolean operationSuccess;

    public static final int LOGIN = 0;
    public static final int CREATE = 1;
    public static final int DELETE = 2;

    public static final int CREATE_COURSE = 10;
    public static final int RENAME_COURSE = 11;
    public static final int DELETE_COURSE = 12;
    public static final int ENTER_COURSE = 13;

    public static final int CREATE_FORUM = 20;
    public static final int EDIT_FORUM = 21;
    public static final int DELETE_FORUM = 22;
    public static final int ENTER_FORUM = 23;

    public static final int CREATE_POST = 30;
    public static final int DELETE_POST = 31;
    public static final int EDIT_POST = 32;
    public static final int REPLY_POST = 33;
    public static final int VOTE_POST = 34;
    public static final int SORT_POST = 35;

    public static final int REQUEST_COURSE_TITLES = 40;
    public static final int REQUEST_FORUM_TOPICS = 41;
    public static final int REQUEST_POST_LIST = 42;

    public Packet(int requestType) {
        this.requestType = requestType;
    }

    public Packet(int requestType, String[] msg) {
        this.requestType = requestType;
        this.msg = msg;
    }

    public Packet(int requestType, String[] msg, boolean operationSuccess) {
        this.requestType = requestType;
        this.msg = msg;
        this.operationSuccess = operationSuccess;
    }

    public Packet(boolean operationSuccess) {
        this.operationSuccess = operationSuccess;
    }

    public Packet(String[] msg) {
        this.msg = msg;
    }

    public Packet(String[] msg, boolean operationSuccess) {
        this.msg = msg;
        this.operationSuccess = operationSuccess;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public String[] getMsg() {
        return msg;
    }

    public void setMsg(String[] msg) {
        this.msg = msg;
    }

    public boolean isOperationSuccess() {
        return operationSuccess;
    }

    public void setOperationSuccess(boolean operationSuccess) {
        this.operationSuccess = operationSuccess;
    }
}
