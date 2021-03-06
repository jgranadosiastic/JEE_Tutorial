package com.jgranados.journals.journal.model;

import com.jgranados.journals.user.model.Profile;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * JEE Tutorial
 *
 * @author jose - 02.07.2016
 * @Title: Journal
 * @Description: description
 *
 * Changes History
 */
@Entity
@Table(name = "Journal")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Journal.findAll", query = "SELECT j FROM Journal j")})
public class Journal implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idJournal")
	private Integer idJournal;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "name")
	private String name;
	@Size(max = 200)
	@Column(name = "about")
	private String about;
	@Lob
	@Column(name = "image")
	private byte[] image;
	@Size(max = 200)
	@Column(name = "tags")
	private String tags;
	@Basic(optional = false)
	@NotNull
	@Column(name = "active")
	private boolean active;
	@JoinColumn(name = "owner_profile", referencedColumnName = "idProfile")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Profile ownerProfile;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "journal", orphanRemoval = true)
	private Collection<JournalPublication> journalPublicationsCollection;

	public Journal() {
	}

	public Journal(Integer idJournal) {
		this.idJournal = idJournal;
	}

	public Journal(Integer idJournal, String name) {
		this.idJournal = idJournal;
		this.name = name;
	}

	public Integer getIdJournal() {
		return idJournal;
	}

	public void setIdJournal(Integer idJournal) {
		this.idJournal = idJournal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(nillable = true)
	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@XmlElement(nillable = true)
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Profile getOwnerProfile() {
		return ownerProfile;
	}

	public void setOwnerProfile(Profile ownerProfile) {
		this.ownerProfile = ownerProfile;
	}

	@XmlTransient
	public Collection<JournalPublication> getJournalPublicationsCollection() {
		return journalPublicationsCollection;
	}

	public void setJournalPublicationsCollection(Collection<JournalPublication> journalPublicationsCollection) {
		this.journalPublicationsCollection = journalPublicationsCollection;
	}

	@XmlElement(nillable = true)
	public Integer getPublicationsCount() {
		if (this.journalPublicationsCollection != null) {
			return journalPublicationsCollection.size();
		} else {
			return 0;
		}
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idJournal != null ? idJournal.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Journal)) {
			return false;
		}
		Journal other = (Journal) object;
		if ((this.idJournal == null && other.idJournal != null) || (this.idJournal != null && !this.idJournal.equals(other.idJournal))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.josegranados.sajavajournals.book.model.Journal[ idJournal=" + idJournal + " ]";
	}

}
