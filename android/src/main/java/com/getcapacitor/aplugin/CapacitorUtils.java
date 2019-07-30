package com.getcapacitor.aplugin;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Toast;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.aplugin.utils.PermissionsUtils;
import com.getcapacitor.aplugin.utils.SMSUtils;

import java.util.ArrayList;
import java.util.List;

@NativePlugin()
public class CapacitorUtils extends Plugin {
    //创建监听权限的接口对象
    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            Toast.makeText(getActivity(), "权限通过，可以做其他事情!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void forbitPermissons() {
//            finish();
            Toast.makeText(getActivity(), "权限不通过!", Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 发送短消息
     * @param call {phoneNumber message}
     */
    @PluginMethod()
    public void sendSMS(PluginCall call) {
        String mobileNumber = call.getString("phoneNumber");
        String message = call.getString("message");
        JSObject ret = new JSObject();
        ret.put("method", "sendSMS");
        Boolean hasRight = SMSUtils.isSendSmsAllowed(getContext());
        if(hasRight)
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mobileNumber, null, message, null, null);
            ret.put("value",true);
        }
        else{
            SMSUtils.requestSmsSendPermission(getActivity());
            ret.put("value",false);
        }
        call.success(ret);
    }
    /**
     * 最小化应用
     * @param call {minimise:true}
     */
    @PluginMethod()
    public void minimise(PluginCall call) {
        Boolean min = call.getBoolean("minimise");
        getActivity().moveTaskToBack(min);
        JSObject ret = new JSObject();
        ret.put("value", true);
        call.success(ret);
    }
    /**
     * 检查是否安装了某应用
     * @param packageName 包名
     * 下面提供常用的app包名  可自己替换：
     * QQ com.tencent.mobileqq
     * 微信 com.tencent.mm
     * QQ音乐 com.tencent.qqmusic
     * 微信读书 com.tencent.weread
     * QQ阅读 com.qq.reader
     * 唱吧 com.changba
     * 网易云音乐 com.netease.cloudmusic
     * 钉钉 com.alibaba.android.rimet
     * 抖音 com.ss.android.ugc.aweme
     * 美团外卖 com.sankuai.meituan.takeoutnew
     * 饿了么 me.ele
     * 摩拜单车 com.mobike.mobikeapp
     * OFO so.ofo.labofo
     * 今日头条 com.ss.android.article.news
     * 新浪微博 com.sina.weibo
     * 网易新闻 com.netease.newsreader.activity
     * 快手 com.smile.gifmaker
     * 知乎 com.zhihu.android
     * 虎牙直播 com.duowan.kiwi
     * 映客直播 com.meelive.ingkee
     * 秒拍 com.yixia.videoeditor
     * 美图秀秀 com.mt.mtxx.mtxx
     * 美颜相机 com.meitu.meiyancamera
     * 携程 ctrip.android.view
     * 陌陌 com.immomo.momo
     * 优酷 com.youku.phone
     * 爱奇艺 com.qiyi.video
     * 滴滴出行 com.sdu.didi.psnger
     * 支付宝 com.eg.android.AlipayGphone
     * 淘宝 com.taobao.taobao
     * 京东 com.jingdong.app.mall
     * 大众点评 com.dianping.v1
     * 搜狗输入法 com.sohu.inputmethod.sogou
     * 百度地图 com.baidu.BaiduMap
     * 高德地图 com.autonavi.minimap
     * 简书 com.jianshu.haruki
     * 喜马拉雅 com.ximalaya.ting.android
     * @return
     */
    @PluginMethod()
    public void  isApkAvilible(PluginCall call) {
        Context mContext = getContext();
        String packageName = call.getString("packageName");
        JSObject ret = new JSObject();
        final PackageManager packageManager =  mContext.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName)) ret.put("value", true);
            call.success(ret);
            return;
        }
        ret.put("value", false);
        call.success(ret);
    }
    /**
     * 拨打电话
     * @param call {phoneNumber}
     */
    @PluginMethod()
    public void callNumber(PluginCall call) {
        String phoneNumber = call.getString("phoneNumber");
        //创建意图对象
        Intent intent = new Intent();
        //把动作封装进入意图中
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        //把动作告诉系统
        getActivity().startActivity(intent);
        JSObject ret = new JSObject();
        ret.put("value", true);
        call.success(ret);
    }
    /**
     * 判断手机是否开启指定权限
     * @param call {permission:""}
     */
    @PluginMethod()
    public void checkPermission(PluginCall call) {
        String permission = call.getString("permission");
        String permissionName = PermissionsUtils.getPermissionName(permission);
        int checkSelfPermission = ContextCompat.checkSelfPermission(getContext(), permissionName);
        Boolean hasPermission=true;
        if(checkSelfPermission == PackageManager.PERMISSION_DENIED){//未申请
            hasPermission=false;
        }
        JSObject ret = new JSObject();
        ret.put("value", hasPermission);
        call.success(ret);
    }
    /**
     * 开始指定授权
     * @param call {permission:""}
     */
    @PluginMethod()
    public void setPermission(PluginCall call){
        String permission = call.getString("permission");
        ArrayList<String> arrList = new ArrayList<String>();
        arrList.add(PermissionsUtils.getPermissionName(permission));
        String [] permissions = arrList.toArray(new String[arrList.size()]);
        PermissionsUtils.getInstance().chekPermissions(getActivity(), permissions, permissionsResult);
    }
}