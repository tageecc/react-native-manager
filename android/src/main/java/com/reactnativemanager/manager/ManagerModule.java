package com.reactnativemanager.manager;

import android.widget.Toast;
import android.database.Cursor;
import android.net.Uri;
import android.Manifest;
import android.support.v4.content.PermissionChecker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Pattern;

import com.facebook.react.bridge.*;

public class ManagerModule extends ReactContextBaseJavaModule {

    private static final String MODULE_NAME="Manager";

    public ManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }


    @ReactMethod
    public void getSms(ReadableMap _filter, final Promise promise) {

        if(PermissionChecker.PERMISSION_GRANTED!=PermissionChecker.checkSelfPermission(getReactApplicationContext(), Manifest.permission.READ_SMS)){
            Toast.makeText(getReactApplicationContext(), "Permission denied！", Toast.LENGTH_SHORT).show();
            promise.reject("Permission denied！Please make sure that you have read SMS permission");
            return;
        }


        JSONArray smsList = new JSONArray();
        try {

            JSONObject defaultFilter = new JSONObject();
            defaultFilter.put("_id", "");                   // 一个自增字段，从1开始
            defaultFilter.put("thread_id", "");             // 序号，同一发信人的id相同
            defaultFilter.put("address", "");               // 发件人手机号码
            defaultFilter.put("person", "");                // 联系人列表里的序号，陌生人为null
            defaultFilter.put("date", "");                  // 发件日期
            defaultFilter.put("protocol", "");              // 协议，分为： 0 SMS_RPOTO, 1 MMS_PROTO
            defaultFilter.put("read", "");                  // 是否阅读 0未读， 1已读
            defaultFilter.put("status", "");                // 状态 -1接收，0 complete, 64 pending, 128 failed
            defaultFilter.put("type", "");                  // 类型 1是接收到的，2是已发出
            defaultFilter.put("body", "");                  // 短消息内容
            defaultFilter.put("service_center", "");        // 短信服务中心号码编号
            defaultFilter.put("subject", "");               // 短信的主题
            defaultFilter.put("reply_path_present", "");    // TP-Reply-Path
            defaultFilter.put("locked", "");                // locked
            defaultFilter.put("sms_type", "");              // 默认 "" 所有短信,inbox 收件箱,sent 已发送,draft 草稿,outbox 发件箱,failed 发送失败,queued 待发送列表
            defaultFilter.put("count", "10");               // 返回数量

            JSONObject filter = Util.convertMapToJson(_filter);
            filter = Util.deepMerge(defaultFilter, filter);
            Uri uri = Uri.parse("content://sms/" + filter.getString("sms_type"));
            Cursor cur = getCurrentActivity().getContentResolver().query(uri, null, null, null, "date desc"); // 获取手机内部短信

            if (cur.moveToFirst()) {

                do {
                    JSONObject sms = new JSONObject();

                    int _id = cur.getInt(cur.getColumnIndex("_id"));
                    int thread_id = cur.getInt(cur.getColumnIndex("thread_id"));
                    String address = cur.getString(cur.getColumnIndex("address"));
                    String person = cur.getString(cur.getColumnIndex("person"));
                    long date = cur.getLong(cur.getColumnIndex("date"));
                    int protocol = cur.getInt(cur.getColumnIndex("protocol"));
                    int read = cur.getInt(cur.getColumnIndex("read"));
                    int status = cur.getInt(cur.getColumnIndex("status"));
                    int type = cur.getInt(cur.getColumnIndex("type"));
                    String body = cur.getString(cur.getColumnIndex("body"));
                    String service_center = cur.getString(cur.getColumnIndex("service_center"));
                    String subject = cur.getString(cur.getColumnIndex("subject"));
                    String reply_path_present = cur.getString(cur.getColumnIndex("reply_path_present"));
                    int locked = cur.getInt(cur.getColumnIndex("locked"));

                    // filter
                    if (filter.getString("_id").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("_id"));
                        if (!pattern.matcher("" + _id).matches()) continue;
                    }
                    if (filter.getString("thread_id").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("thread_id"));
                        if (!pattern.matcher("" + thread_id).matches()) continue;
                    }
                    if (filter.getString("address").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("address"));
                        if (!pattern.matcher(address).matches()) continue;
                    }
                    if (filter.getString("person").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("person"));
                        if (!pattern.matcher(person).matches()) continue;
                    }
                    if (filter.getString("date").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("date"));
                        if (!pattern.matcher("" + date).matches()) continue;
                    }
                    if (filter.getString("protocol").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("protocol"));
                        if (!pattern.matcher("" + protocol).matches()) continue;
                    }
                    if (filter.getString("read").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("read"));
                        if (!pattern.matcher("" + read).matches()) continue;
                    }
                    if (filter.getString("status").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("status"));
                        if (!pattern.matcher("" + status).matches()) continue;
                    }
                    if (filter.getString("type").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("type"));
                        if (!pattern.matcher("" + type).matches()) continue;
                    }
                    if (filter.getString("body").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("body"));
                        if (!pattern.matcher(body).matches()) continue;
                    }
                    if (filter.getString("service_center").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("service_center"));
                        if (!pattern.matcher(service_center).matches()) continue;
                    }
                    if (filter.getString("subject").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("subject"));
                        if (!pattern.matcher(subject).matches()) continue;
                    }
                    if (filter.getString("reply_path_present").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("reply_path_present"));
                        if (!pattern.matcher(reply_path_present).matches()) continue;
                    }
                    if (filter.getString("locked").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("locked"));
                        if (!pattern.matcher("" + locked).matches()) continue;
                    }
                    if (filter.getString("locked").length() != 0) {
                        Pattern pattern = Pattern.compile(filter.getString("locked"));
                        if (!pattern.matcher("" + locked).matches()) continue;
                    }
                    if (smsList.length() >= filter.getInt("count")) break;


                    sms.put("_id", _id);
                    sms.put("thread_id", thread_id);
                    sms.put("address", address);
                    sms.put("person", person);
                    sms.put("date", date);
                    sms.put("protocol", protocol);
                    sms.put("read", read);
                    sms.put("status", status);
                    sms.put("type", type);
                    sms.put("body", body);
                    sms.put("service_center", service_center);
                    sms.put("subject", subject);
                    sms.put("reply_path_present", reply_path_present);
                    sms.put("locked", locked);

                    smsList.put(sms);

                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            }

            promise.resolve(Util.convertJsonToArray(smsList));

        } catch (Exception ex) {
            promise.reject(ex);
        }
    }
}