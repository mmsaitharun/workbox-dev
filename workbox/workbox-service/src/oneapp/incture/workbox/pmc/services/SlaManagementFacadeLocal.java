package oneapp.incture.workbox.pmc.services;

import javax.ejb.Local;

import oneapp.incture.workbox.pmc.dto.ResponseMessage;
import oneapp.incture.workbox.pmc.dto.SlaListDto;
import oneapp.incture.workbox.pmc.dto.responses.SlaProcessNamesResponse;

@Local
public interface SlaManagementFacadeLocal {

	SlaProcessNamesResponse getAllProcessNames();

	SlaListDto getSlaDetails(String processName);

	ResponseMessage updateSla(SlaListDto dto);

}
