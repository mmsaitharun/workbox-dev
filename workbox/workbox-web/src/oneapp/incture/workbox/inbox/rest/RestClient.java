package oneapp.incture.workbox.inbox.rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oneapp.incture.workbox.inbox.services.RespDto;
import oneapp.incture.workbox.inbox.services.RestBeanLocal;

@Path("/r")
@Produces(MediaType.APPLICATION_JSON)
public class RestClient {

	@EJB
	RestBeanLocal local;
	
	@Path("/m")
	@GET
	public RespDto method(){
		return local.myMethod();
	}
}
