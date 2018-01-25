package oneapp.incture.workbox.pmc.reports;

import oneapp.incture.workbox.util.PMCConstant;
import oneapp.incture.workbox.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class FileFactory extends ServiceFactory {

	@Override
	public File getFile(String fileFormate) {
		System.err.println("[PMC] REPORT - FileFactory  - getFile() - Started with FileFormate - " + fileFormate);
		if (!ServicesUtil.isEmpty(fileFormate)) {
			if (PMCConstant.REPORT_EXCEL.equalsIgnoreCase(fileFormate.trim())) {
				System.err.println("Excel Formate");
				return new Excel();
			} else if (PMCConstant.REPORT_PDF.equalsIgnoreCase(fileFormate.trim())) {
				System.err.println("PDF Formate");
				return new PDF();
			}
		}
		return null;
	}
	
	
	@Override
	public Report getReport(String reportName) {
		System.err.println("NO IMPLEMENTATION -- Get Implementation in ReportFactory class");
		return null;
	}


}
