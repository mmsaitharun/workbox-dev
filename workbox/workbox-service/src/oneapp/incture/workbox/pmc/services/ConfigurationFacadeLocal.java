package oneapp.incture.workbox.pmc.services;

import java.util.List;

import javax.ejb.Local;

import oneapp.incture.workbox.pmc.dto.ProcessConfigDto;
import oneapp.incture.workbox.pmc.dto.ProcessListDto;
import oneapp.incture.workbox.pmc.dto.ReportAgingDto;
import oneapp.incture.workbox.pmc.dto.responses.ProcessConfigResponse;
import oneapp.incture.workbox.pmc.dto.responses.WorkloadRangeResponse;

@Local
public interface ConfigurationFacadeLocal {

	ProcessListDto getAllProcessNames();

	ProcessConfigResponse getAllBusinessLabels();

	ProcessConfigDto getBusinessLabelByProcessName(String processName);

	WorkloadRangeResponse getWorkLoadRange();

	List<ReportAgingDto> getAgeingBuckets(String reportName);

	ProcessConfigResponse getUserBusinessLabels();

}
