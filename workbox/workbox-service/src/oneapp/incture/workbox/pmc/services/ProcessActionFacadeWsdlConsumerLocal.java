package oneapp.incture.workbox.pmc.services;

import javax.ejb.Local;

import oneapp.incture.workbox.pmc.dto.ProcessActionDto;
import oneapp.incture.workbox.pmc.dto.ResponseMessage;

@Local
public interface ProcessActionFacadeWsdlConsumerLocal {

	ResponseMessage cancelProcess(ProcessActionDto processList);

}
