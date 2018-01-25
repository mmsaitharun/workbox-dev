package oneapp.incture.workbox.pmc.services;

import javax.ejb.Local;

import oneapp.incture.workbox.pmc.dto.MailRequestDto;
import oneapp.incture.workbox.pmc.dto.ResponseMessage;

@Local
public interface NotificationFacadeLocal {

	public ResponseMessage sendNotification(MailRequestDto requestDto);

	ResponseMessage sendProcessRemainder();

	ResponseMessage sendTaskRemainder();

}
