package com.assHoleDeveloper.eCommerce.eCommerce.model;


import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseModel implements Serializable{

    @CreatedBy
    @Column(name="INS_USR")
    protected String insUsr;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name="INS_TMST")
    protected Date insTmst;


    @Column(name="UPD_USR")
    @LastModifiedBy
    protected String updUsr;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name="UPD_TMST")
    protected Date updTmst;

    @PrePersist
    public void prePersist() {
        String createdByUser = getUsernameOfAuthenticatedUser();
        this.insUsr = createdByUser;
        this.insTmst = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        String modifiedByUser = getUsernameOfAuthenticatedUser();
        this.updUsr = modifiedByUser;
        this.updTmst = new Date();
    }

    private String getUsernameOfAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if(principal instanceof String){
            return (String)principal;
        } else {
            return ((UserSecurity)principal).getUsername();
        }
    }

}
