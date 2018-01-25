package oneapp.incture.workbox.pmc.reports;

import oneapp.incture.workbox.util.PMCConstant;
import oneapp.incture.workbox.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class ReportFactory extends ServiceFactory {

	@Override
	public Report getReport(String reportName) {
		System.err.println("[PMC] REPORT - ReportFactory  - getReport() - Started with reportName - " + reportName);
		if (!ServicesUtil.isEmpty(reportName)) {
			if (PMCConstant.USER_TASK_REPORT.equalsIgnoreCase(reportName.trim())) {
				System.err.println(PMCConstant.USER_TASK_REPORT);
				return new UserWorkloadReport();
			} else if (PMCConstant.PROCESS_TRACKER.equalsIgnoreCase(reportName.trim())) {
				System.err.println(PMCConstant.PROCESS_TRACKER);
				return new ProcessReport();
			} else if (PMCConstant.TASK_AEGING.equalsIgnoreCase(reportName.trim())) {
				System.err.println(PMCConstant.TASK_AEGING);
				return new TaskAegingReport();
			} else if (PMCConstant.PROCESS_BY_DURATION.equalsIgnoreCase(reportName.trim())) {
				System.err.println(PMCConstant.PROCESS_BY_DURATION);
				return new ProcessByDurationReport();
			} else if (PMCConstant.TASK_MANAGER.equalsIgnoreCase(reportName.trim())) {
				System.err.println(PMCConstant.TASK_MANAGER);
				return new TaskManagerReport();
			}
		}
		System.err.println("No Report Found");
		return null;
	}

	@Override
	public File getFile(String fileFormate) {
		System.err.println("NO IMPLEMENTATION -- Get Implementation in FileFactory class");
		return null;
	}

}
