package com.finsightai.model;

import jakarta.persistence.*;

@Entity @Table(name="app_users")
public class AppUser {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String username;
    @Column(nullable=false) private String password;
    @Column(nullable=false) private String role;
    @Column(nullable=false) private boolean enabled = true;
    public Long getId(){return id;} public String getUsername(){return username;} public String getPassword(){return password;} public String getRole(){return role;} public boolean isEnabled(){return enabled;}
    public void setId(Long v){id=v;} public void setUsername(String v){username=v;} public void setPassword(String v){password=v;} public void setRole(String v){role=v;} public void setEnabled(boolean v){enabled=v;}
}