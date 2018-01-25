package oneapp.incture.workbox.pmc.e2e;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CoordinatesDTO {

	private char Flag;
	private String taskSlaId;
	private int xCoord;
	private int yCoord;
	private String nextTaskSlaId;
	private int nextXCoord;
	private int nextYCoord;

	public String getTaskSlaId() {
		return taskSlaId;
	}

	public void setTaskSlaId(String taskSlaId) {
		this.taskSlaId = taskSlaId;
	}

	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public String getNextTaskSlaId() {
		return nextTaskSlaId;
	}

	public void setNextTaskSlaId(String nextTaskSlaId) {
		this.nextTaskSlaId = nextTaskSlaId;
	}

	public int getNextXCoord() {
		return nextXCoord;
	}

	public void setNextXCoord(int nextXCoord) {
		this.nextXCoord = nextXCoord;
	}

	public int getNextYCoord() {
		return nextYCoord;
	}

	public void setNextYCoord(int nextYCoord) {
		this.nextYCoord = nextYCoord;
	}

	public char getFlag() {
		return Flag;
	}

	public void setFlag(char flag) {
		Flag = flag;
	}

	@Override
	public String toString() {
		return "CoordinatesDTO [Flag=" + Flag + ", taskSlaId=" + taskSlaId + ", xCoord=" + xCoord + ", yCoord=" + yCoord
				+ ", nextTaskSlaId=" + nextTaskSlaId + ", nextXCoord=" + nextXCoord + ", nextYCoord=" + nextYCoord
				+ "]";
	}

}
