package oneapp.incture.workbox.pmc.e2e;

public class MappingDTO {
	private String mappingId;
	private String currSla;
	private String nextSla;
	private String rejectFlag;

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public String getCurrSla() {
		return currSla;
	}

	public void setCurrSla(String currSla) {
		this.currSla = currSla;
	}

	public String getNextSla() {
		return nextSla;
	}

	public void setNextSla(String nextSla) {
		this.nextSla = nextSla;
	}

	public String getRejectFlag() {
		return rejectFlag;
	}

	public void setRejectFlag(String rejectFlag) {
		this.rejectFlag = rejectFlag;
	}

	@Override
	public String toString() {
		return "MappingDTO [mappingId=" + mappingId + ", currSla=" + currSla + ", nextSla=" + nextSla + ", rejectFlag="
				+ rejectFlag + "]";
	}

}
