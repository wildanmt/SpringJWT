package com.ownproject.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	@PrePersist
	public void onCreated() {
		this.createdDate = new Date();
	}
	
	@PreUpdate
	public void onUpdated() {
		this.modifiedBy = modifiedBy;
		this.modifiedDate = new Date();
	}
}	
	
	
	
