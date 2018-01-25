package oneapp.incture.workbox.inbox.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class DemoBean
 */
@Stateless
@LocalBean
public class RestBean implements RestBeanLocal {

    public RestBean() {
    }
    
    @Override
    public RespDto myMethod(){
    	RespDto dto = new RespDto();
    	dto.setStr("Hello From EJB");
    	return dto;
    }
}
