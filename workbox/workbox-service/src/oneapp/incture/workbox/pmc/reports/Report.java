package oneapp.incture.workbox.pmc.reports;

import javax.persistence.EntityManager;

import oneapp.incture.workbox.pmc.dto.PMCReportBaseDto;
import oneapp.incture.workbox.pmc.dto.ReportPayload;

/**
 * @author Saurabh
 *
 */
public interface Report {
	
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManager);

}
