package oneapp.incture.workbox.pmc.reports;

import javax.persistence.EntityManager;

import oneapp.incture.workbox.pmc.dto.PMCReportBaseDto;
import oneapp.incture.workbox.pmc.dto.ReportPayload;
import oneapp.incture.workbox.poadapter.dao.TaskOwnersDao;
import oneapp.incture.workbox.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class TaskAegingReport implements Report {

	@Override
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManager) {
		System.err.println("[PMC] REPORT - TaskAegingReport  - getData() - Started with ReportPayload - " + payload);
		if (!ServicesUtil.isEmpty(payload) && !ServicesUtil.isEmpty(entityManager)) {
			TaskOwnersDao dao = new TaskOwnersDao(entityManager);
			return dao.getTaskAgeing(payload.getProcessName(), payload.getUsersList(), payload.getStatus(),payload.getRequestId(), payload.getLabelValue());
		}
		return null;
	}
}
