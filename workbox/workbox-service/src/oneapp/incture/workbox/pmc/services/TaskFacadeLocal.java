package oneapp.incture.workbox.pmc.services;

import javax.ejb.Local;

import oneapp.incture.workbox.inbox.dto.WorkBoxActionDto;
import oneapp.incture.workbox.pmc.dto.ManageTasksRequestDto;
import oneapp.incture.workbox.pmc.dto.ManageTasksResponseDto;
import oneapp.incture.workbox.pmc.dto.TaskAgeingResponse;
import oneapp.incture.workbox.pmc.dto.responses.TaskEventsResponse;
import oneapp.incture.workbox.poadapter.dto.TaskOwnersListDto;

@Local
public interface TaskFacadeLocal {

	TaskEventsResponse getTaskDetailsByProcessInstance(String processId);
	
	ManageTasksResponseDto getTasksByUserAndDuration(ManageTasksRequestDto request);

	TaskOwnersListDto getTaskOwners(WorkBoxActionDto dto);

	TaskAgeingResponse getTaskAgeing(String processName, String userGroup, String status, String requestId, String labelValue);

}
