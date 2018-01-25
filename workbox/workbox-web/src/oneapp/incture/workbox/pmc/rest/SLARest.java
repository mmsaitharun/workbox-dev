package oneapp.incture.workbox.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oneapp.incture.workbox.pmc.dto.ResponseMessage;
import oneapp.incture.workbox.pmc.dto.SlaListDto;
import oneapp.incture.workbox.pmc.dto.responses.SlaProcessNamesResponse;
import oneapp.incture.workbox.pmc.services.SlaManagementFacadeLocal;


@Path("/sla")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class SLARest {

	@EJB
	private SlaManagementFacadeLocal sla;
	
	@GET
	@Path("/process")
	public SlaProcessNamesResponse getAllProcessNames() {
		return sla.getAllProcessNames();
	}
	
	@GET
	@Path("/details/{processName}")
    public SlaListDto getSlaDetails(@PathParam("processName") String processName) {
		
		return sla.getSlaDetails(processName);
	}
	
	@POST
	@Path("/updateSla")
	public ResponseMessage getTasksByUserAndDuration(SlaListDto slaDto){
		return sla.updateSla(slaDto);
	}
	
}
