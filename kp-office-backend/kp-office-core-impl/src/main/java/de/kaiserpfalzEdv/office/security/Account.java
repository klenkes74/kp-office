/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.kaiserpfalzEdv.office.security;

import de.kaiserpfalzEdv.office.OfficeSystemException;
import de.kaiserpfalzEdv.office.core.KPOTenantHoldingEntity;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Entity
@Table(
        schema = "SECURITY",
        name = "ACCOUNTS",
        indexes = {
                @Index(name = "TENANTS_USERS_UK", columnList = "TENANT_,DISPLAY_NUMBER_", unique = true),
                @Index(name = "ACCOUNTS_IDENTITY_K", columnList = "IDENTITY_")
        }
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("BASE")
public abstract class Account extends KPOTenantHoldingEntity {
    @Column(name = "PASSWORD_", length=100, nullable = false)
    private String password;

    @Column(name = "SALT_", length=8, nullable = false)
    private String salt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDENTITY_", referencedColumnName = "ID_")
    private Identity identity;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            schema = "SECURITY", name = "ACCOUNTS_ROLES",
            joinColumns = {@JoinColumn(name = "ACCOUNT_ID_", referencedColumnName = "ID_")},
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID_", referencedColumnName = "ID_")},
            indexes = {
                    @Index(name = "ACC_ROL_ACCOUNT_K", columnList="ACCOUNT_ID_"),
                    @Index(name = "ACC_ROL_ROLE_K", columnList="ROLE_ID_")
            }
    )
    final private Set<Role> roles = new HashSet<>();


    @Deprecated // Only for JPA, JAX-B, Jackson and so on ...
    public Account() {
        try {
            salt = generateSalt();
        } catch (NoSuchAlgorithmException e) {
            throw new OfficeSystemException("Can't create the password salt!");
        }
    }

    public Account(@NotNull UUID id,
                   @NotNull final Identity identity,
                   final String account,
                   final String name,
                   @NotNull final String password) {
        super(id, name, account, identity.getTenantId());

        try {
            salt = generateSalt();

            setPassword(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new OfficeSystemException("Can't encode the password!");
        }

        this.identity = identity;

    }


    public String getAccountName() {
        return getDisplayNumber();
    }


    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void setRoles(final Set<Role> roles) {
        this.roles.clear();

        if (roles != null) {
            this.roles.addAll(roles);
        }
    }

    public void addRole(final Role role) {
        this.roles.add(role);
    }

    public void removeRole(final Role role) {
        this.roles.remove(role);
    }


    public void setPassword(final String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        salt = generateSalt();
        this.password = encrypt(password);
    }

    private String encrypt(final String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160;
        int iterations = 20000;


        KeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), iterations, derivedKeyLength);

        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

        return Base64.getEncoder().encodeToString(f.generateSecret(spec).getEncoded());
    }

    private String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
        byte[] salt = new byte[8];
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }


    public boolean checkPassword(final String passwordToCheck) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String encryptedPasswordToCheck = encrypt(passwordToCheck);

        return password.equals(encryptedPasswordToCheck);
    }
}
