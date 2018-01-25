package oneapp.incture.workbox.dbproviders;

public class DBPropertiesDto {
	
	private Class<?> provider;
	private String cacheSharedDefault;
	private String jdbcUrl;
	private String userId;
	private String password;
	private String jdbcDriver;
	
	public Class<?> getProvider() {
		return provider;
	}
	public void setProvider(Class<?> provider) {
		this.provider = provider;
	}
	public String getCacheSharedDefault() {
		return cacheSharedDefault;
	}
	public void setCacheSharedDefault(String cacheSharedDefault) {
		this.cacheSharedDefault = cacheSharedDefault;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}
}
