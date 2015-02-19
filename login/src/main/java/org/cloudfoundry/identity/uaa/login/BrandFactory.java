package org.cloudfoundry.identity.uaa.login;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Creates the brand based on settings catering for the build-in brands.
 */
public class BrandFactory extends AbstractFactoryBean<Brand> {

  // These are exposed for the sake of tests.
  public static final Brand PIVOTAL = new Brand("pivotal", "Pivotal", "Pivotal ID", "a Pivotal ID", null);
  public static final Brand OSS = new Brand("oss", "Cloud Foundry", "account", "an account", null);

  private static final List<Brand> BUILT_IN = Arrays.asList(PIVOTAL, OSS);
  
  private String name;
  private String serviceName;
  private String accountName;
  private String accountPhrase;
  private String adminEmailAddress;

  @Override
  public Class<?> getObjectType() {
    return Brand.class;
  }

  public void setBrandName(String name) {
    this.name = name;
  }
  
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }
  
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }
  
  public void setAccountPhrase(String accountPhrase) {
    this.accountPhrase = accountPhrase;
  }
  
  public void setAdminEmailAddress(String adminEmailAddress) {
    this.adminEmailAddress = adminEmailAddress;
  }
  
  @Override
  protected Brand createInstance() throws Exception {
    for (Brand brand : BUILT_IN) {
      if (brand.getBrandName().equals(name)) {
        if (serviceName != null || accountName != null || accountPhrase != null || adminEmailAddress != null) {
          throw new IllegalStateException("For built-in brands (pivotal/oss) only provide the login.brand setting."); 
        }
        return brand;
      }
    }
    if (serviceName == null || accountName == null || accountPhrase == null || adminEmailAddress == null) {
      throw new IllegalStateException("For custom brand '" + name + "' all settings are required: serviceName, accountName, accountPhrase, adminEmailAddress"); 
    }
    return new Brand(name, serviceName, accountName, accountPhrase, adminEmailAddress);
  }
  
}
