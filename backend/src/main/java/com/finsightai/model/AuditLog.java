package com.finsightai.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="audit_logs")
public class AuditLog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String username;
    @Column(nullable=false) private String action;
    @Column(nullable=false) private String entityType;
    private String entityId;
    @Column(length=2000) private String details;
    @Column(nullable=false) private Instant timestamp;
    public Long getId(){return id;} public void setId(Long v){id=v;} public String getUsername(){return username;} public void setUsername(String v){username=v;} public String getAction(){return action;} public void setAction(String v){action=v;} public String getEntityType(){return entityType;} public void setEntityType(String v){entityType=v;} public String getEntityId(){return entityId;} public void setEntityId(String v){entityId=v;} public String getDetails(){return details;} public void setDetails(String v){details=v;} public Instant getTimestamp(){return timestamp;} public void setTimestamp(Instant v){timestamp=v;}
}