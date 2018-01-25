package oneapp.incture.workbox.pmc.dto;

import javax.xml.bind.annotation.XmlRootElement;

import oneapp.incture.workbox.util.EnOperation;
import oneapp.incture.workbox.util.InvalidInputFault;

@XmlRootElement
public class ReportAgingDto extends BaseDto {

	private String id;
	private String reportName;
	private Integer lowerSegment;
	private Integer higherSegment;
	private String displayName;
	private Integer agingRange;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getLowerSegment() {
		return lowerSegment;
	}

	public void setLowerSegment(Integer lowerSegment) {
		this.lowerSegment = lowerSegment;
	}

	public Integer getHigherSegment() {
		return higherSegment;
	}

	public void setHigherSegment(Integer higherSegment) {
		this.higherSegment = higherSegment;
	}

	/**
	 * @return the agingRange
	 */
	public Integer getAgingRange() {
		return agingRange;
	}

	/**
	 * @param agingRange
	 *            the agingRange to set
	 */
	public void setAgingRange(Integer agingRange) {
		this.agingRange = agingRange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportAgingDto [id=" + id + ", reportName=" + reportName + ", lowerSegment=" + lowerSegment
				+ ", higherSegment=" + higherSegment + ", displayName=" + displayName + ", agingRange=" + agingRange
				+ "]";
	}

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		// TODO Auto-generated method stub

	}

}
