package oneapp.incture.workbox.pmc.services;

import javax.ejb.Local;

import oneapp.incture.workbox.pmc.dto.DownloadReportResponseDto;
import oneapp.incture.workbox.pmc.dto.ReportPayload;

@Local
public interface ReportFacadeLocal {

	DownloadReportResponseDto generateReport(ReportPayload payload);

}
