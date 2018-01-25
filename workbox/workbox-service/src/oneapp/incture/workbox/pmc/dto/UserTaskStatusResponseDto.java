package oneapp.incture.workbox.pmc.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserTaskStatusResponseDto {
	private Map<String, TaskStatusDto> taskCountDetail;
	private ResponseMessage responseMessage;

	public Map<String, TaskStatusDto> getTaskCountDetail() {
		return taskCountDetail;
	}

	public void setTaskCountDetail(Map<String, TaskStatusDto> taskCountDetail) {
		this.taskCountDetail = taskCountDetail;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "UserTaskStatusResponseDto [taskCountDetail=" + taskCountDetail + ", responseMessage=" + responseMessage + "]";
	}
}
