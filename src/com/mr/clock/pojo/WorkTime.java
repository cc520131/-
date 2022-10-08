package com.mr.clock.pojo;

public class WorkTime {//作息时间类
    private String start;// 上班时间
    private String end;// 下班时间

    public WorkTime() {
        super();
    }

    public WorkTime(String start, String end) {
        super();
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "WorkTime [start=" + start + ", end=" + end + "]";
    }
}
