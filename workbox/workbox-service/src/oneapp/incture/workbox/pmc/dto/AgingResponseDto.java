package oneapp.incture.workbox.pmc.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AgingResponseDto {
	private List<AgingTableDto> tupleDtos;
	
	private Map<String, BigDecimal> headerMap;
	
	private List<AgingHeaderMapDto> agingHeaderMap;
	
	public List<AgingHeaderMapDto> getAgingHeaderMap() {
		return agingHeaderMap;
	}

	public void setAgingHeaderMap(List<AgingHeaderMapDto> agingHeaderMap) {
		this.agingHeaderMap = agingHeaderMap;
	}

	private String status;


	public List<AgingTableDto> getTupleDtos() {
		return tupleDtos;
	}

	public void setTupleDtos(List<AgingTableDto> tupleDtos) {
		this.tupleDtos = tupleDtos;
	}

	public Map<String, BigDecimal> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, BigDecimal> headerMap) {
		this.headerMap = headerMap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AgingResponseDto [tupleDtos=" + tupleDtos + ", headerMap=" + headerMap + "]";
	}

}
