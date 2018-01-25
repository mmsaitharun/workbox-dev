package oneapp.incture.workbox.pmc.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import oneapp.incture.workbox.pmc.dto.ProcessAgeingResponse;
import oneapp.incture.workbox.pmc.dto.ProcessDetailsDto;
import oneapp.incture.workbox.pmc.dto.ProcessDetailsResponse;
import oneapp.incture.workbox.pmc.dto.UserDetailsDto;
import oneapp.incture.workbox.pmc.dto.UserProcessDetailRequestDto;
import oneapp.incture.workbox.pmc.services.ProcessFacadeLocal;
import oneapp.incture.workbox.poadapter.dto.ProcessEventsDto;

@Path("/process")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ProcessRest {

	@EJB
	private ProcessFacadeLocal process;
	
	@POST
	@Path("/by/duration")
	public ProcessDetailsResponse getProcessesByDuration(ProcessDetailsDto processDetailsDto){
		return process.getProcessesByDuration(processDetailsDto);
	} 

	@POST
	@Path("/by/taskowner")
	public ProcessDetailsResponse getProcessesByTaskOwner(UserProcessDetailRequestDto request){
		return process.getProcessesByTaskOwner(request);
	}

	@GET
	@Path("/details")
	public ProcessEventsDto getProcessDetailsByInstance(@QueryParam("processId") String processId){
		return process.getProcessDetailsByInstance(processId);
	}
	
	@GET
	@Path("/createdBy/{inputString}")
	public List<UserDetailsDto> getCreatedByList(@PathParam("inputString") String inputString){
		return process.getCreatedByList(inputString);
	}
	
	@GET
	@Path("/ageing")
	public ProcessAgeingResponse getProcessAgingNew(@QueryParam("type") String ageingType, @QueryParam("process") String processName){
		return process.getProcessAgeing(ageingType,processName);
	}
}
