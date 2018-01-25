package oneapp.incture.workbox.poadapter.dto;

import java.math.BigDecimal;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskCountDto {
	private String userId;
	private Map<String, BigDecimal> taskCountDetail;
	private BigDecimal maxCount = new BigDecimal(5);

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<String, BigDecimal> getTaskCountDetail() {
		return taskCountDetail;
	}

	public void setTaskCountDetail(Map<String, BigDecimal> taskCountDetail) {
		this.taskCountDetail = taskCountDetail;
	}

	
	public BigDecimal getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(BigDecimal maxCount) {
		this.maxCount = maxCount;
	}

	@Override
	public String toString() {
		return "TaskCountDto [userId=" + userId + ", taskCountDetail=" + taskCountDetail + ", maxCount=" + maxCount + "]";
	}


}
