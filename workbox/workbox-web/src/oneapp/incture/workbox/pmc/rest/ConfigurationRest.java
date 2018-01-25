package oneapp.incture.workbox.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oneapp.incture.workbox.pmc.dto.ProcessConfigDto;
import oneapp.incture.workbox.pmc.dto.ProcessListDto;
import oneapp.incture.workbox.pmc.dto.responses.ProcessConfigResponse;
import oneapp.incture.workbox.pmc.dto.responses.WorkloadRangeResponse;
import oneapp.incture.workbox.pmc.services.ConfigurationFacadeLocal;


@Path("/config")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ConfigurationRest {

	@EJB
	ConfigurationFacadeLocal config;

	@GET
	@Path("/processnames")
	public ProcessListDto getAllProcessNames() {
		return config.getAllProcessNames();
	}

	@GET
	@Path("/labels")
	public ProcessConfigResponse getUserBusinessLabels() {
		return config.getUserBusinessLabels();
	}
	@GET
	@Path("/adminLabels")
	public ProcessConfigResponse getAllBusinessLabels() {
		return config.getAllBusinessLabels();
	}
	
	@GET
	@Path("/{processName}")
	public ProcessConfigDto getBusinessLabelByProcessName(@PathParam("processName") String processName) {
		return config.getBusinessLabelByProcessName(processName);
	}

	@GET
	@Path("/workloadrange")
	public WorkloadRangeResponse getWorkLoadRange() {
		return config.getWorkLoadRange();
	}

}
