package oneapp.incture.workbox.inbox.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oneapp.incture.workbox.inbox.dto.ResponseMessage;
import oneapp.incture.workbox.inbox.dto.WorkBoxActionDto;
import oneapp.incture.workbox.inbox.dto.WorkBoxActionListDto;
import oneapp.incture.workbox.pmc.wsdlconsumers.WorkBoxActionsConsumer;

@Path("/workboxAction")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class WorkboxActionRest {

	/*@EJB
	private WorkBoxActionFacadeWsdlConsumerLocal workboxAction;*/
	WorkBoxActionsConsumer wbActionConsumer = null;
	
	@POST
	@Path("/claim")
	 public ResponseMessage claimTask(WorkBoxActionDto dto) {
		wbActionConsumer = new WorkBoxActionsConsumer();
		System.err.println("[PMC][WorkBoxAction][Rest][claim] method invoked");
    	return wbActionConsumer.claimTask(dto);
	}
	
	@POST
	@Path("/release")
    public ResponseMessage release(WorkBoxActionDto dto) {
		wbActionConsumer = new WorkBoxActionsConsumer();
		System.err.println("[PMC][WorkBoxAction][Rest][release] method invoked");
    	return wbActionConsumer.release(dto);
	}

	@POST
	@Path("/delegate")
    public ResponseMessage delegate(WorkBoxActionDto dto) {
		wbActionConsumer = new WorkBoxActionsConsumer();
		System.err.println("[PMC][WorkBoxAction][Rest][delegate] method invoked");
    	return wbActionConsumer.delegate(dto);
	}
	@POST
	@Path("/nominate")
    public ResponseMessage nominate(WorkBoxActionDto dto) {
		wbActionConsumer = new WorkBoxActionsConsumer();
		System.err.println("[PMC][WorkBoxAction][Rest][nominate] method invoked");
    	return wbActionConsumer.nominate(dto);
	}

	@POST
	@Path("/addNote")
    public ResponseMessage addNote(WorkBoxActionDto dto) {
		wbActionConsumer = new WorkBoxActionsConsumer();
		System.err.println("[PMC][WorkBoxAction][Rest][addNote] method invoked");
    	return wbActionConsumer.addNote(dto);
	}
	
	@POST
	@Path("/complete")
    public ResponseMessage complete(WorkBoxActionListDto dto) {
		wbActionConsumer = new WorkBoxActionsConsumer();
		System.err.println("[PMC][WorkBoxAction][Rest][complete] method invoked");
    	return wbActionConsumer.complete(dto);
	}

	@POST
	@Path("/claimDelegate")
    public ResponseMessage claimAndDelegate(WorkBoxActionDto dto) {
		wbActionConsumer = new WorkBoxActionsConsumer();
		System.err.println("[PMC][WorkBoxAction][Rest][claimDelegate] method invoked");
    	return wbActionConsumer.claimAndDelegate(dto);
	}
}
