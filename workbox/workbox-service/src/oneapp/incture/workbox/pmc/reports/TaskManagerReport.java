package oneapp.incture.workbox.pmc.reports;

import javax.persistence.EntityManager;

import oneapp.incture.workbox.pmc.dto.ManageTasksRequestDto;
import oneapp.incture.workbox.pmc.dto.PMCReportBaseDto;
import oneapp.incture.workbox.pmc.dto.ReportPayload;
import oneapp.incture.workbox.poadapter.dao.TaskEventsDao;
import oneapp.incture.workbox.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class TaskManagerReport implements Report {

	@Override
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManager) {
		System.err.println("[PMC] REPORT - TaskManagerReport  - getData() - Started with ReportPayload - " + payload);
		if (!ServicesUtil.isEmpty(payload) && !ServicesUtil.isEmpty(entityManager)) {
			TaskEventsDao dao = new TaskEventsDao(entityManager);
			return dao.getTasksByUserAndDuration(new ManageTasksRequestDto(payload.getUserId(), payload.getStatus(), payload.getProcessName(), payload.getStartDayFrom(), payload.getStartDayTo(),
					payload.getPage(), payload.getRequestId(), payload.getLabelValue()));
		}
		return null;
	}

}
