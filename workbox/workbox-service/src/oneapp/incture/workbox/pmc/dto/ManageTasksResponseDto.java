package oneapp.incture.workbox.pmc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ManageTasksResponseDto extends PMCReportBaseDto {

	private List<ManageTasksDto> tasks;

	private ResponseMessage message;

	public List<ManageTasksDto> getTasks() {
		return tasks;
	}

	public void setTasks(List<ManageTasksDto> tasks) {
		this.tasks = tasks;
	}

	public ResponseMessage getMessage() {
		return message;
	}

	public void setMessage(ResponseMessage message) {
		this.message = message;
	}

}
