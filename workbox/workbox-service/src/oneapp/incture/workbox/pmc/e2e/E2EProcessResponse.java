package oneapp.incture.workbox.pmc.e2e;

import javax.xml.bind.annotation.XmlRootElement;

import oneapp.incture.workbox.pmc.dto.ResponseMessage;

@XmlRootElement
public class E2EProcessResponse {
	
	private String base64String;
	private ResponseMessage responseMessage;
	
	public String getBase64String() {
		return base64String;
	}
	public void setBase64String(String base64String) {
		this.base64String = base64String;
	}
	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	@Override
	public String toString() {
		return "E2EProcessResponse [base64String=" + base64String + ", responseMessage=" + responseMessage + "]";
	}
	
}
