package 后端;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.qmhd.telecom.common.JsonUtil;

public class 返回前段第几页的页面 {
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	/**
	 * 保存历史页面访问路径及参数
	 * @param request
	 * @param n 第几级页面 
	 */
	public void saveHistoryRequest(HttpServletRequest request, int n) {
		redisTemplate.boundHashOps("USER_REQUEST_HISTORY_URI").put(this.getCurrentUser(request).getUsername() + n,
				request.getRequestURI());
		String json = JsonUtil.toJson(request.getParameterMap());
		redisTemplate.boundHashOps("USER_REQUEST_HISTORY").put(this.getCurrentUser(request).getUsername() + n,
				json);
	}
	
	//分页中放在第一局 
	this.saveHistoryRequest(req, 112);
	
	
	function back() {
        window.location.href = "/back?n=112";

    }
}
