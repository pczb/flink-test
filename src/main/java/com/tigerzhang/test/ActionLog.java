package com.tigerzhang.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Created by tigerzhang on 2017/8/8.
 */
public class ActionLog {
    private String uid;
    private String mid;
    private int actionCode;
    private long time;
    private JSONObject extra;
    private String networkType;

    public ActionLog() {
    }

    public ActionLog(String uid, String mid, int actionCode, long time, JSONObject extra) {
        this.actionCode = actionCode;
        this.time = time;
        this.uid = uid;
        this.mid = mid;
        this.extra = extra;
    }

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public JSONObject getExtra() {
        return extra;
    }

    public void setExtra(JSONObject extra) {
        this.extra = extra;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public static ActionLog parse(String msg) {
        String[] fields = msg.split("`", -1);
        if (fields.length >= 6) {
            if (StringUtils.isBlank(fields[2]) || !StringUtils.isNumeric(fields[2].trim())) {
                return null;
            }
            // 将从数据打码日志中，获取的信息进行赋值
            String uid = fields[1].trim();
            if (!StringUtils.isNumeric(uid)) {
                return null;
            }
            String mid = fields[3].trim();
            if (!StringUtils.isNumeric(mid) || mid.length() != 16) {
                return null;
            }
            ActionLog actionLog = new ActionLog();
            actionLog.setMid(mid);
            actionLog.setUid(uid);
            String opTimeStr = fields[0].split("\\|")[1];
            long timestamp = LocalDateTime.parse(opTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toEpochSecond(ZoneOffset.UTC);
            actionLog.setTime(timestamp);

            return actionLog;
        }
        return null;
    }
}
