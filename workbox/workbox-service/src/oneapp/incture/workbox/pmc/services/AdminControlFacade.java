package oneapp.incture.workbox.pmc.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import oneapp.incture.workbox.pmc.dto.AdminControlDto;
import oneapp.incture.workbox.pmc.dto.AgingRangeConfigDto;
import oneapp.incture.workbox.pmc.dto.ProcessConfigDto;
import oneapp.incture.workbox.pmc.dto.ReportAgingDto;
import oneapp.incture.workbox.pmc.dto.ResponseMessage;
import oneapp.incture.workbox.pmc.dto.WorkloadRangeDto;
import oneapp.incture.workbox.poadapter.dao.ProcessConfigDao;
import oneapp.incture.workbox.poadapter.dao.ReportAgingDao;
import oneapp.incture.workbox.poadapter.dao.WorkloadRangeDao;
import oneapp.incture.workbox.util.NoResultFault;
import oneapp.incture.workbox.util.PMCConstant;
import oneapp.incture.workbox.util.ServicesUtil;

/**
 * Session Bean implementation class AdminControlBean
 */
@WebService(name = "AdminControlFacade", portName = "AdminControlFacadePort", serviceName = "AdminControlFacadeService", targetNamespace = "http://incture.com/pmc/services/")
@Stateless
public class AdminControlFacade implements AdminControlFacadeLocal {

	@EJB
	ConfigurationFacadeLocal config;

	public AdminControlFacade() {
	}

	@EJB
	private EntityManagerProviderLocal em;

	@WebMethod(operationName = "deleteProcessConfig", exclude = false)
	@Override
	public ResponseMessage deleteProcessConfig(@WebParam(name = "processName") String processName) {
		ProcessConfigDao processConfigDao = new ProcessConfigDao(em.getEntityManager());
		ResponseMessage responseDto=new ResponseMessage();
		try {
			ProcessConfigDto processConfigDto = new ProcessConfigDto();
			processConfigDto.setProcessName(processName);
			processConfigDao.delete(processConfigDto);
			responseDto.setMessage("SUCCESS");
			responseDto.setStatus("SUCCESS");
			responseDto.setStatusCode("0");
		} 
		catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			responseDto.setStatus("FAILURE");
			responseDto.setStatusCode("1");
		}
		return responseDto;
	}
	
	@WebMethod(operationName = "getAdminConfigurationData", exclude = false)
	@Override
	public AdminControlDto getAdminConfigurationData() {

		AdminControlDto resultDto = new AdminControlDto();

		ProcessConfigDao configDao = new ProcessConfigDao(em.getEntityManager());
		List<ProcessConfigDto> configDtos = null;

		try {
			configDtos = configDao.getAllProcessConfigEntry();
		} catch (NoResultFault e) {
			System.err.println("NO Result found for any process Configured");
		}

		List<WorkloadRangeDto> workloadRangeDtos = config.getWorkLoadRange().getWorkloadRangeDtos();

		ReportAgingDao agingDao = new ReportAgingDao(em.getEntityManager());

		List<ReportAgingDto> reportAgingDtos = agingDao.getAllReportConfiguration();

		List<AgingRangeConfigDto> agingRangeConfigDtos = new ArrayList<AgingRangeConfigDto>();
		AgingRangeConfigDto agingRangeConfigDto = null;
		int processCount = 0;
		int taskCount = 0;
		int taskStatusCount = 0;

		if (!ServicesUtil.isEmpty(reportAgingDtos)) {
			Iterator<ReportAgingDto> it = reportAgingDtos.iterator();
			while (it.hasNext()) {
				ReportAgingDto dto = it.next();
				if (!ServicesUtil.isEmpty(dto.getAgingRange())) {
					agingRangeConfigDto = new AgingRangeConfigDto();
					agingRangeConfigDto.setAgingRange(dto.getAgingRange());
					agingRangeConfigDto.setReportName(dto.getReportName());
					agingRangeConfigDto.setReportId(dto.getId());
					agingRangeConfigDtos.add(agingRangeConfigDto);
					it.remove();
				} else {
					if (dto.getReportName().equals(PMCConstant.PROCESS_AGING_REPORT)) {
						++processCount;
					} else if (dto.getReportName().equals(PMCConstant.TASK_AGING_REPORT)) {
						++taskCount;
					} else if (dto.getReportName().equals(PMCConstant.USER_TASK_STATUS_GRAPH)) {
						++taskStatusCount;
					}
				}
			}
		}
		resultDto.setProcessConfigDtos(configDtos);
		resultDto.setWorkloadRangeDtos(workloadRangeDtos);
		resultDto.setAgingReportConfigDtos(reportAgingDtos);
		resultDto.setAgingRangeConfigDto(agingRangeConfigDtos);
		resultDto.setProcessCount(processCount);
		resultDto.setTaskCount(taskCount);
		resultDto.setTaskStatusCount(taskStatusCount);
		return resultDto;

	}

	@WebMethod(operationName = "createUpdateDataAdmin", exclude = false)
	@Override
	public ResponseMessage createUpdateDataAdmin(@WebParam(name = "adminControlDto") AdminControlDto adminControlDto) {
		
		ResponseMessage responseDto=new ResponseMessage();
		ProcessConfigDao processConfigDao = new ProcessConfigDao(em.getEntityManager());
		WorkloadRangeDao workloadRangeDao = new WorkloadRangeDao(em.getEntityManager());
		ReportAgingDao reportAgingDao = new ReportAgingDao(em.getEntityManager());

		if (!ServicesUtil.isEmpty(adminControlDto)) {
			List<ProcessConfigDto> processConfigDtos = adminControlDto.getProcessConfigDtos();
			List<WorkloadRangeDto> workloadRangeDtos = adminControlDto.getWorkloadRangeDtos();
			List<ReportAgingDto> reportAgingDtos = adminControlDto.getAgingReportConfigDtos();
			List<AgingRangeConfigDto> agingRangeConfigDtos = adminControlDto.getAgingRangeConfigDto();

			try{
			
			if (!ServicesUtil.isEmpty(processConfigDtos)) {
				for (ProcessConfigDto dto : processConfigDtos) {
					try {
						processConfigDao.update(dto);
						System.err.println("Process Config Updated");
						
					} catch (NoResultFault e) {
						try {
							processConfigDao.create(dto);
							System.err.println("Process Config Created");
						} catch (Exception eProcessConfig) {
							System.err.println("Exception : " + eProcessConfig.getMessage());
						}
					} catch (Exception eProcessConfig) {
						System.err.println("Exception : " + eProcessConfig.getMessage());
					}
				}
			}

			if (!ServicesUtil.isEmpty(workloadRangeDtos)) {

				for (WorkloadRangeDto dto1 : workloadRangeDtos) {
					try {
						workloadRangeDao.update(dto1);
						System.err.println("Workload Range Updated");
					} catch (NoResultFault e) {
						try {
							workloadRangeDao.create(dto1);
							System.err.println("WorkloadRange Created");
						} catch (Exception eWorkloadRange) {
							System.err.println("Exception : " + eWorkloadRange.getMessage());
						}
					} catch (Exception eWorkloadRange) {
						System.err.println("Exception : " + eWorkloadRange.getMessage());
					}
				}
			}

			if (!ServicesUtil.isEmpty(reportAgingDtos)) {

				for (ReportAgingDto dto2 : reportAgingDtos) {
					try {
						reportAgingDao.update(dto2);
						System.err.println("Reporting Aging Updated");
					} catch (NoResultFault e) {
						try {
							reportAgingDao.create(dto2);
							System.err.println("Reporting Aging Created");
						} catch (Exception eReportingAging) {
							System.err.println("Exception : " + eReportingAging.getMessage());
						}
					} catch (Exception eReportingAging) {
						System.err.println("Exception : " + eReportingAging.getMessage());
					}
				}
			}

			if (!ServicesUtil.isEmpty(agingRangeConfigDtos)) {
				for (AgingRangeConfigDto dto4 : agingRangeConfigDtos) {
					ReportAgingDto dto = new ReportAgingDto();
					dto.setAgingRange(dto4.getAgingRange());
					dto.setReportName(dto4.getReportName());
					dto.setId(dto4.getReportId());
					try {
						reportAgingDao.update(dto);
						System.err.println("Aging Range Updated");
					} catch (NoResultFault e) {
						try {
							reportAgingDao.create(dto);
							System.err.println("Aging Range Created");
						} catch (Exception eAgingRange) {
							System.err.println("Exception1 : " + eAgingRange.getMessage());
						}
					} catch (Exception eAgingRange) {
						System.err.println("Exception2 : " + eAgingRange.getMessage());
					}
				}
			}
			responseDto.setMessage("SUCCESS");
			responseDto.setStatus("SUCCESS");
			responseDto.setStatusCode("0");
		
		}
		catch(Exception e)
		{
			System.err.println("Exception : " + e.getMessage());
			responseDto.setMessage("Failed");
			responseDto.setStatus("Failed");
			responseDto.setStatusCode("1");
				
		}
		}
		return responseDto;	
	}

}
