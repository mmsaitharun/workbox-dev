package oneapp.incture.workbox.pmc.e2e;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlaDTO {

	private String taskSlaId;
	private List<String> nextTaskSlaId;
	private String procName;
	private String taskDef;
	private String taskMode;
	private Integer lane;

	public String getTaskSlaId() {
		return taskSlaId;
	}

	public void setTaskSlaId(String taskSlaId) {
		this.taskSlaId = taskSlaId;
	}

	public List<String> getNextTaskSlaId() {
		return nextTaskSlaId;
	}

	public void setNextTaskSlaId(List<String> nextTaskSlaId) {
		this.nextTaskSlaId = nextTaskSlaId;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public String getTaskDef() {
		return taskDef;
	}

	public void setTaskDef(String taskDef) {
		this.taskDef = taskDef;
	}

	public Integer getLane() {
		return lane;
	}

	public void setLane(Integer lane) {
		this.lane = lane;
	}

	public String getTaskMode() {
		return taskMode;
	}

	public void setTaskMode(String taskMode) {
		this.taskMode = taskMode;
	}

	@Override
	public String toString() {
		return "SlaDTO [taskSlaId=" + taskSlaId + ", nextTaskSlaId=" + nextTaskSlaId + ", procName=" + procName
				+ ", taskDef=" + taskDef + ", taskMode=" + taskMode + ", lane=" + lane + "]";
	}
}
