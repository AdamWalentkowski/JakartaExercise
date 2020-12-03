package com.eti.pg.user.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
//@BasicAuthenticationMechanismDefinition(realmName = "Jakarta Exercise")
@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/auth/form/login.xhtml",
                errorPage = "/auth/form/login_error.xhtml"
        )
)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/JakartaExerciseBoards",
        callerQuery =  "#{'select password from users where login = ?'}",
        groupsQuery = "#{'select role from users where login = ?'}",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        hashAlgorithmParameters = {
                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA256",
                "Pbkdf2PasswordHash.Iterations=2048",
                "Pbkdf2PasswordHash.SaltSizeBytes=32",
                "Pbkdf2PasswordHash.KeySizeBytes=32"
        }
)
public class Config {
}
