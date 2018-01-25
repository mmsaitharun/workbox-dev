package oneapp.incture.workbox.pmc.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import oneapp.incture.workbox.dbproviders.DatabasePropertyProvider;

public class SingletonEMProvider {

	private static SingletonEMProvider emProvider = null;
	
	public EntityManager entityManager;
	
	private SingletonEMProvider() {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("workbox_pu", DatabasePropertyProvider.getConnectionProperties("hana"));
		this.setEntityManager(emFactory.createEntityManager());
	}
	
	public static SingletonEMProvider getInstance() {
		if(emProvider == null) {
			emProvider = new SingletonEMProvider();
		}
		return emProvider;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
/*	public static void main(String[] args) {
		SingletonEMProvider emProvider = SingletonEMProvider.getInstance();
		System.out.println("My EntityManager : "+emProvider.entityManager);
	}*/
}