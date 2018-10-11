package com.darren.rongyundemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.rong.imlib.RongIMClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private static final String TOKEN = "nlJ+2gsK9+cPhA7YpD21sumuCohL076YpI869iKXhKXeiSlQH7nYoXHst8LCZlxOEm+1xrXEzYkcye9Gl8Rc8tU3VNTMXsCp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
         * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。
         * @param token 从服务端获取的用户身份令牌（Token）。
         * @return RongIMClient  客户端核心类的实例。
         */
        RongIMClient.connect(TOKEN, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                            2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                LoggerUtils.e(TAG,"onTokenIncorrect()");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                LoggerUtils.d(TAG,"融云.userid=" + userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LoggerUtils.d(TAG,"融云.onError=" + errorCode.getValue());
            }
        });
    }
}
