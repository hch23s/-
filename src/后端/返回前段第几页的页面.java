package ���;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.qmhd.telecom.common.JsonUtil;

public class ����ǰ�εڼ�ҳ��ҳ�� {
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	/**
	 * ������ʷҳ�����·��������
	 * @param request
	 * @param n �ڼ���ҳ�� 
	 */
	public void saveHistoryRequest(HttpServletRequest request, int n) {
		redisTemplate.boundHashOps("USER_REQUEST_HISTORY_URI").put(this.getCurrentUser(request).getUsername() + n,
				request.getRequestURI());
		String json = JsonUtil.toJson(request.getParameterMap());
		redisTemplate.boundHashOps("USER_REQUEST_HISTORY").put(this.getCurrentUser(request).getUsername() + n,
				json);
	}
	
	//��ҳ�з��ڵ�һ�� 
	this.saveHistoryRequest(req, 112);
	
	
	function back() {
        window.location.href = "/back?n=112";

    }
}
