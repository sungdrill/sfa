package com.cellbiotech.model.sfa;

import javax.persistence.*;


/**
 * Created by Q u i c K
 * Date : 2016. 12. 30..
 * Description : 사용자 권한을 부여한다. 보통 User, Admin, Guest와 같은 개념을 부여한다
 */
@Entity
@Table(name = "S_ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private int id;
    @Column(name = "ROLE")
    private String role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
    
    
}
