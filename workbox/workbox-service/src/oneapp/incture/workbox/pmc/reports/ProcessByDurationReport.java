package oneapp.incture.workbox.pmc.reports;

import javax.persistence.EntityManager;

import oneapp.incture.workbox.pmc.dto.PMCReportBaseDto;
import oneapp.incture.workbox.pmc.dto.ProcessDetailsDto;
import oneapp.incture.workbox.pmc.dto.ReportPayload;
import oneapp.incture.workbox.poadapter.dao.ProcessEventsDao;
import oneapp.incture.workbox.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class ProcessByDurationReport implements Report {

	@Override
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManager) {
		System.err.println("[PMC] REPORT - ProcessByDurationReport  - getData() - Started with ReportPayload - " + payload);
		if (!ServicesUtil.isEmpty(payload) && !ServicesUtil.isEmpty(entityManager)) {
			ProcessEventsDao dao = new ProcessEventsDao(entityManager);
			return dao.getProcessByDuration(new ProcessDetailsDto(payload.getProcessName(), payload.getStartDayFrom(), payload.getStartDayTo(), payload.getPage()));
		}
		return null;
	}

}
