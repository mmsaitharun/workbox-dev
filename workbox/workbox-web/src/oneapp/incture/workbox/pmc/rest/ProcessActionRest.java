package oneapp.incture.workbox.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oneapp.incture.workbox.pmc.dto.ResponseMessage;
import oneapp.incture.workbox.pmc.dto.ProcessActionDto;
import oneapp.incture.workbox.pmc.services.ProcessActionFacadeWsdlConsumerLocal;

@Path("/processAction")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ProcessActionRest {

	@EJB
	private ProcessActionFacadeWsdlConsumerLocal processAction;
	
	@POST
	@Path("/cancel")
	 public ResponseMessage cancel(ProcessActionDto dto) {
		System.err.println("[PMC][ProcessAction][Rest][cancel] method invoked");
    	return processAction.cancelProcess(dto);
	}
	
	

}
