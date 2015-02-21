package org.cloudfoundry.identity.uaa.login;

public class Brand {

  private final String serviceName;
  private final String accountName;
  private final String accountPhrase;
  private final String name;
  
  public Brand(String name, String serviceName, String accountName, String accountPhrase) {
    this.name = name;
    this.serviceName = serviceName;
    this.accountName = accountName;
    this.accountPhrase = accountPhrase;
  }
  
  /**
   * Returns the name of the brand, e.g. <code>"oss"</code> for the Cloud Foundry brand.
   * Prefer the other methods.
   */
  public String getBrandName() {
    return name;
  }
  
  public String getServiceName() {
    return serviceName;
  }

  public String getAccountName() {
    return accountName;
  }
  
  public String getAccountPhrase() {
    return accountPhrase;
  }
  
}
