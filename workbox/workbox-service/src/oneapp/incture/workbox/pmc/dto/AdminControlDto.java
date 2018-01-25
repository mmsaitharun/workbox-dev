package oneapp.incture.workbox.pmc.dto;


import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdminControlDto {

	private List<ProcessConfigDto> processConfigDtos;
	private List<WorkloadRangeDto> workloadRangeDtos;
	private List<ReportAgingDto> agingReportConfigDtos;
	private List<AgingRangeConfigDto> agingRangeConfigDto;
	private Integer processCount;
	private Integer taskCount;
	private Integer taskStatusCount;
	
	
	
	
	/**
	 * @return the processConfigDtos
	 */
	public List<ProcessConfigDto> getProcessConfigDtos() {
		return processConfigDtos;
	}
	/**
	 * @param processConfigDtos the processConfigDtos to set
	 */
	public void setProcessConfigDtos(List<ProcessConfigDto> processConfigDtos) {
		this.processConfigDtos = processConfigDtos;
	}
	/**
	 * @return the workloadRangeDtos
	 */
	public List<WorkloadRangeDto> getWorkloadRangeDtos() {
		return workloadRangeDtos;
	}
	/**
	 * @param workloadRangeDtos the workloadRangeDtos to set
	 */
	public void setWorkloadRangeDtos(List<WorkloadRangeDto> workloadRangeDtos) {
		this.workloadRangeDtos = workloadRangeDtos;
	}
	
	
	/**
	 * @return the agingReportConfigDtos
	 */
	public List<ReportAgingDto> getAgingReportConfigDtos() {
		return agingReportConfigDtos;
	}
	/**
	 * @param agingReportConfigDtos the agingReportConfigDtos to set
	 */
	public void setAgingReportConfigDtos(List<ReportAgingDto> agingReportConfigDtos) {
		this.agingReportConfigDtos = agingReportConfigDtos;
	}
	/**
	 * @return the agingRangeConfigDto
	 */
	public List<AgingRangeConfigDto> getAgingRangeConfigDto() {
		return agingRangeConfigDto;
	}
	/**
	 * @param agingRangeConfigDto the agingRangeConfigDto to set
	 */
	public void setAgingRangeConfigDto(List<AgingRangeConfigDto> agingRangeConfigDto) {
		this.agingRangeConfigDto = agingRangeConfigDto;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdminControlFacadeDto [processConfigDtos=" + processConfigDtos + ", workloadRangeDtos="
				+ workloadRangeDtos + ", agingReportConfigDtos=" + agingReportConfigDtos + ", agingRangeConfigDto="
				+ agingRangeConfigDto + "]";
	}
	/**
	 * @return the processCount
	 */
	public Integer getProcessCount() {
		return processCount;
	}
	/**
	 * @param processCount the processCount to set
	 */
	public void setProcessCount(Integer processCount) {
		this.processCount = processCount;
	}
	/**
	 * @return the taskCount
	 */
	public Integer getTaskCount() {
		return taskCount;
	}
	/**
	 * @param taskCount the taskCount to set
	 */
	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}
	/**
	 * @return the taskStatusCount
	 */
	public Integer getTaskStatusCount() {
		return taskStatusCount;
	}
	/**
	 * @param taskStatusCount the taskStatusCount to set
	 */
	public void setTaskStatusCount(Integer taskStatusCount) {
		this.taskStatusCount = taskStatusCount;
	}
	
	
	
	
}
