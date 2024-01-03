package com.camunda.training.configuration.security;

import org.camunda.bpm.engine.impl.plugin.AdministratorAuthorizationPlugin;
import org.camunda.bpm.identity.impl.ldap.plugin.LdapIdentityProviderPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class LdapConfiguration {

    @Bean
    public LdapIdentityProviderPlugin ldapIdentityProviderPlugin(){

        LdapIdentityProviderPlugin ldapIdentityProviderPlugin = new LdapIdentityProviderPlugin();

        ldapIdentityProviderPlugin.setServerUrl("ldap://localhost:389");
        ldapIdentityProviderPlugin.setAcceptUntrustedCertificates(true);
        ldapIdentityProviderPlugin.setManagerDn("cn=admin,dc=camunda,dc=com");
        ldapIdentityProviderPlugin.setManagerPassword("admin");
        ldapIdentityProviderPlugin.setBaseDn("dc=camunda,dc=com");
        ldapIdentityProviderPlugin.setUserSearchFilter("(objectclass=person)");
        ldapIdentityProviderPlugin.setUserIdAttribute("uid");
        ldapIdentityProviderPlugin.setUserFirstnameAttribute("cn");
        ldapIdentityProviderPlugin.setUserLastnameAttribute("sn");
        ldapIdentityProviderPlugin.setUserEmailAttribute("mail");
        ldapIdentityProviderPlugin.setUserPasswordAttribute("userpassword");
        ldapIdentityProviderPlugin.setGroupSearchBase("ou=groups");
        ldapIdentityProviderPlugin.setGroupSearchFilter("(objectclass=groupOfUniqueNames)");
        ldapIdentityProviderPlugin.setGroupIdAttribute("cn");
        ldapIdentityProviderPlugin.setGroupNameAttribute("cn");
        ldapIdentityProviderPlugin.setGroupMemberAttribute("uniqueMember");
        ldapIdentityProviderPlugin.setSortControlSupported(false);

        return ldapIdentityProviderPlugin;
    }

    @Bean
    public AdministratorAuthorizationPlugin administratorAuthorizationPlugin(){
        AdministratorAuthorizationPlugin administratorAuthorizationPlugin = new AdministratorAuthorizationPlugin();

        administratorAuthorizationPlugin.setAdministratorUserName("norman");
        return administratorAuthorizationPlugin;
    }
}
