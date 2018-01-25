package oneapp.incture.workbox.inbox.dto;

import javax.xml.bind.annotation.XmlRootElement;

import oneapp.incture.workbox.pmc.dto.BaseDto;
import oneapp.incture.workbox.util.EnOperation;
import oneapp.incture.workbox.util.InvalidInputFault;

@XmlRootElement
public class TaskOwnersDto extends BaseDto{

	private String eventId;
	private String taskOwner;
	private Boolean isProcessed;
	private String taskOwnerDisplayName;
	private String ownerEmail;
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public Boolean getIsProcessed() {
		return isProcessed;
	}

	public void setIsProcessed(Boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public String getTaskOwnerDisplayName() {
		return taskOwnerDisplayName;
	}

	public void setTaskOwnerDisplayName(String taskOwnerDisplayName) {
		this.taskOwnerDisplayName = taskOwnerDisplayName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	
	@Override
	public String toString() {
		return "TaskOwnersDto [eventId=" + eventId + ", taskOwner=" + taskOwner + ", isProcessed=" + isProcessed
				+ ", taskOwnerDisplayName=" + taskOwnerDisplayName + ", ownerEmail=" + ownerEmail + "]";
	}

	@Override
	public Boolean getValidForUsage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
	}

}
