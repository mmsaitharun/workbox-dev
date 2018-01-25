package oneapp.incture.workbox.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oneapp.incture.workbox.pmc.e2e.E2EProcessResponse;
import oneapp.incture.workbox.pmc.e2e.E2EProcessViewServiceLocal;

@Path("/e2e")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class E2EProcessViewRest {
	
	@EJB
	private E2EProcessViewServiceLocal processView;
	
	@GET
	@Path("/view/{processName}")
	public E2EProcessResponse e2eProcess(@PathParam("processName") String processName) {
		return processView.drawImage(processName);
	}
}
