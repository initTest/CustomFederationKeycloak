package com.lockin.auth.provider.user;

import java.sql.Connection;
import java.util.List;

import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

public class CustomUserStorageProviderFactory implements UserStorageProviderFactory<CustomUserStorageProvider> {

	protected final List<ProviderConfigProperty> configMetadata;

	public CustomUserStorageProviderFactory() {
		configMetadata = ProviderConfigurationBuilder.create().property().name("driver").label("JDBC Driver Class")
				.type(ProviderConfigProperty.STRING_TYPE).defaultValue("org.h2.Driver")
				.helpText("Fully qualified class name of the JDBC driver").add().property().name("url")
				.label("JDBC URL").type(ProviderConfigProperty.STRING_TYPE).defaultValue("jdbc:h2:mem:customdb")
				.helpText("JDBC URL used to connect to the user database").add().property().name("username")
				.label("Database User").type(ProviderConfigProperty.STRING_TYPE)
				.helpText("Username used to connect to the database").add().property().name("password")
				.label("Database Password").type(ProviderConfigProperty.STRING_TYPE)
				.helpText("Password used to connect to the database").secret(true).add().property()
				.name("validationQuery").label("SQL Validation Query").type(ProviderConfigProperty.STRING_TYPE)
				.helpText("SQL query used to validate a connection").defaultValue("select 1").add().build();
	}
	
	@Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configMetadata;
    }

	@Override
    public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config)
      throws ComponentValidationException {
       try (Connection c = DbUtil.getConnection(config)) {
           c.createStatement().execute(config.get("validationQuery"));
       }
       catch(Exception ex) {
           throw new ComponentValidationException("Unable to validate database connection",ex);
       }
    }

	@Override
	public String getId() {
		return "custom-user-provider";
	}

	@Override
	public CustomUserStorageProvider create(KeycloakSession ksession, ComponentModel model) {
		return new CustomUserStorageProvider(ksession, model);
	}
}
