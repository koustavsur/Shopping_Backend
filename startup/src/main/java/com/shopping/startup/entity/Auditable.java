package com.shopping.startup.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    @CreatedBy
    @JsonIgnore
    protected String createdBy;

    @LastModifiedBy
    @JsonIgnore
    protected String lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @JsonIgnore
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @JsonIgnore
    protected Date lastModifiedDate;

    @Version
    @JsonIgnore
    protected long version;


}
