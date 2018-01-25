package oneapp.incture.workbox.inbox.services;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RespDto {

	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return "RespDto [str=" + str + "]";
	}

}
