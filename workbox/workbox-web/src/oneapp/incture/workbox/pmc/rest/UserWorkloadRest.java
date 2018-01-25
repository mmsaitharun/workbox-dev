package oneapp.incture.workbox.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oneapp.incture.workbox.pmc.dto.UserProcessDetailRequestDto;
import oneapp.incture.workbox.pmc.dto.UserSearchRequestDto;
import oneapp.incture.workbox.pmc.dto.UserTaskStatusResponseDto;
import oneapp.incture.workbox.pmc.dto.UserWorkloadResponseDto;
import oneapp.incture.workbox.pmc.services.UserWorkloadFacadeLocal;
import oneapp.incture.workbox.poadapter.dto.TaskCountDto;

@Path("/userload")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserWorkloadRest {

	@EJB
	private UserWorkloadFacadeLocal services;

	@Path("/heatmap")
	@POST
	public UserWorkloadResponseDto getUserWorkLoadHeatMap(UserSearchRequestDto searchRequestDto) {
		return services.getUserWorkLoadHeatMap(searchRequestDto);
	}
	
	@Path("/trend/graph")
	@POST
	public TaskCountDto getUserWorkLoadTrendGraph(UserProcessDetailRequestDto request){
		return services.getUserWorkLoadTrendGraph(request);
	}

	
	
	@Path("/status/graph")
	@POST
	public UserTaskStatusResponseDto getUserWorkLoadTaskStausGraph(UserProcessDetailRequestDto request){
		return services.getUserWorkLoadTaskStausGraph(request);
	}

}
