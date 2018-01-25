package oneapp.incture.workbox.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oneapp.incture.workbox.pmc.dto.DownloadReportResponseDto;
import oneapp.incture.workbox.pmc.dto.ReportPayload;
import oneapp.incture.workbox.pmc.services.ReportFacadeLocal;

@Path("/report")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ReportRest {

	@EJB
	private ReportFacadeLocal reportLocal;

	@POST
	@Path("/download")
	public DownloadReportResponseDto generateReport(ReportPayload payload) {
		return reportLocal.generateReport(payload);
	}
}
