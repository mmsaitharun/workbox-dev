package oneapp.incture.workbox.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import oneapp.incture.workbox.inbox.dto.WorkBoxActionDto;
import oneapp.incture.workbox.pmc.dto.ManageTasksRequestDto;
import oneapp.incture.workbox.pmc.dto.ManageTasksResponseDto;
import oneapp.incture.workbox.pmc.dto.TaskAgeingResponse;
import oneapp.incture.workbox.pmc.dto.responses.TaskEventsResponse;
import oneapp.incture.workbox.pmc.services.TaskFacadeLocal;
import oneapp.incture.workbox.poadapter.dto.TaskOwnersListDto;

@Path("/task")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class TaskRest {

	@EJB
	private TaskFacadeLocal task;
	
	@GET
	@Path("/details")
	public TaskEventsResponse getTaskDetailsByProcessInstance(@QueryParam("processId") String processId){
		return task.getTaskDetailsByProcessInstance(processId);
	}
	
	@GET
	@Path("/ageing")
	public TaskAgeingResponse getProcessAging(@QueryParam("processName") String processName, @QueryParam("userGroup") String userGroup, @QueryParam("status") String status, @QueryParam("requestId") String requestId, @QueryParam("labelValue") String labelValue){
		return task.getTaskAgeing(processName, userGroup, status, requestId, labelValue);
	}
	
	@POST
	@Path("/manager")
	public ManageTasksResponseDto getTasksByUserAndDuration(ManageTasksRequestDto request){
		return task.getTasksByUserAndDuration(request);
	}
	
	@POST
	@Path("/getOwners")
	public TaskOwnersListDto getTaskOwners(WorkBoxActionDto dto){
		return task.getTaskOwners(dto);
	}
}
