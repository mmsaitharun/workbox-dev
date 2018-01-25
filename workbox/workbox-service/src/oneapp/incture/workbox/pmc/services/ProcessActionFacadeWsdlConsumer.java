package oneapp.incture.workbox.pmc.services;

import java.util.List;

import javax.ejb.Stateless;

import oneapp.incture.workbox.pmc.dto.ProcessActionDto;
import oneapp.incture.workbox.pmc.dto.ResponseMessage;
import oneapp.incture.workbox.pmc.wsdlconsumers.ProcessActionConsumer;
import oneapp.incture.workbox.util.ServicesUtil;

/**
 * Session Bean implementation class UserManagementFacadeWsdlConsumer
 */
@Stateless
public class ProcessActionFacadeWsdlConsumer implements ProcessActionFacadeWsdlConsumerLocal {

	public ProcessActionFacadeWsdlConsumer() {
	}

	/*@WebServiceRef(name = "ProcessActionFacadeService")
	private ProcessActionFacadeService actionServices;*/
	
	ProcessActionConsumer processActionConsumer = null;

	@Override
	public ResponseMessage cancelProcess(ProcessActionDto processList) {
		System.err.println("[PMC][services][processAction][cancelProcess] method invoked with input" + processList);
		
		ResponseMessage responseDto = new ResponseMessage();
		processActionConsumer = new ProcessActionConsumer();
		List<String> processInstanceList = processList.getProcessInstanceList(); 
		if(!ServicesUtil.isEmpty(processInstanceList)){
			for(String processInstance : processInstanceList){
				System.err.println("[PMC][services][processAction][cancelProcess] in loop  with instance " + processInstance);
				if (!ServicesUtil.isEmpty(processInstance)) {
					try {
						System.err.println("[PMC][services][processAction][cancelProcess][taskInstanceId] " + processInstance);
						responseDto.setMessage(processActionConsumer.cancelProcess(processInstance));
					} catch (Exception e) {
						responseDto.setMessage("Process cancellation Failed because" + e.getMessage());
					}
				} else {
					responseDto.setMessage("Instance Id required to cancel");
					break;
				}
			} 
		}
		else {
			responseDto.setMessage(" No Instance Id sent to to cancel");
		}
		if(responseDto.getMessage().equals("SUCCESS")){
			responseDto.setStatus("SUCCESS");
			responseDto.setStatusCode("0");	
		}else{
			responseDto.setStatus("FAILURE");
			responseDto.setStatusCode("1");
		}
		return responseDto;
	}


}
