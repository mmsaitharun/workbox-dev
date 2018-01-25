package oneapp.incture.workbox.pmc.e2e;

import javax.ejb.Local;

@Local
public interface E2EProcessViewServiceLocal {

	E2EProcessResponse drawImage(String processName);

}
