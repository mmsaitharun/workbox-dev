package oneapp.incture.workbox.pmc.services;

import java.util.List;

import javax.ejb.Local;

import oneapp.incture.workbox.pmc.dto.ResponseMessage;
import oneapp.incture.workbox.pmc.dto.RuleManagementDto;
import oneapp.incture.workbox.pmc.dto.RuleManagementResponseDto;

@Local
public interface RuleManagementFacadeLocal {

	RuleManagementResponseDto getRules(String processName);

	ResponseMessage onSubmit(List<RuleManagementDto> dtoList);

}
