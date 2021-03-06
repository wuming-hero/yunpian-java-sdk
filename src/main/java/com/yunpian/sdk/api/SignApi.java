/**
 * 
 */
package com.yunpian.sdk.api;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.Sign;
import com.yunpian.sdk.util.JsonUtil;

/**
 * 
 * https://www.yunpian.com/api2.0/sign.html
 * 
 * @author dzh
 * @date Nov 23, 2016 1:11:12 PM
 * @since 1.2.0
 */
public class SignApi extends YunpianApi {

    public static final String NAME = "sign";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void init(YunpianClient clnt) {
        super.init(clnt);
        host(clnt.getConf().getConf(YP_SIGN_HOST, "https://sms.yunpian.com"));
    }

    /**
     * <h1>添加签名API</h1>
     * 
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * sign String 是 签名内容 云片网
     * </p>
     * <p>
     * notify Boolean 否 是否短信通知结果，默认true true
     * </p>
     * <p>
     * applyVip Boolean 否 是否申请专用通道，默认false false
     * </p>
     * <p>
     * isOnlyGlobal Boolean 否 是否仅发国际短信，默认false false
     * </p>
     * <p>
     * industryType String 否 所属行业，默认“其它” 物联网 其他值例如:1. 游戏 2. 移动应用 3. 视频 4. 教育 5.
     * IT/通信/电子服务 6. 电子商务 7. 金融 8. 网站 9. 商业服务 10. 房地产/建筑 11. 零售/租赁/贸易 12.
     * 生产/加工/制造 13. 交通/物流 14. 文化传媒 15. 能源/电气 16. 政府企业 17. 农业 18. 物联网 19. 其它
     * </p>
     * 
     * @param param
     *            sign notify applyVip isOnlyGlobal industryType
     * @return
     */
    public Result<Sign> add(Map<String, String> param) {
        Result<Sign> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, SIGN);
        if (r.getCode() != Code.OK)
            return r;
        String data = format2Form(list);

        MapResultHandler<Sign> h = new MapResultHandler<Sign>() {
            @Override
            public Sign data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V2:
                    return JsonUtil.fromJson(rsp.get(SIGN), Sign.class);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, SignApi.this.version());
            }
        };
        try {
            return path("add.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

}
