package com.jgranados.journals.journalpublication.view;

import com.jgranados.journals.journal.JournalFacadeLocal;
import com.jgranados.journals.journal.model.Journal;
import com.jgranados.journals.journal.model.JournalPublication;
import com.jgranados.journals.utils.MessagesUtil;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * JEE_Tutorial-war
 *
 * @author jose - 20.09.2016
 * @Title: JournalPublicationsView
 * @Description: description
 *
 * Changes History
 */
@Named
@ViewScoped
public class JournalPublicationsView implements Serializable {

	@EJB
	private JournalFacadeLocal journalFacade;

	List<JournalPublication> publications = new ArrayList<>();

	//search criterias
	Date publicationDateStartSearchCriteria;
	Date publicationDateEndSearchCriteria;

	//new or selecter publication
	JournalPublication currentPublication;
	UploadedFile fileInputStream;

	//current journal data
	Integer idCurrentJournal;
	Journal currentJournal;

	public List<JournalPublication> getPublications() {
		return publications;
	}

	public void setPublications(List<JournalPublication> publications) {
		this.publications = publications;
	}

	public Date getPublicationDateStartSearchCriteria() {
		return publicationDateStartSearchCriteria;
	}

	public void setPublicationDateStartSearchCriteria(Date publicationDateStartSearchCriteria) {
		this.publicationDateStartSearchCriteria = publicationDateStartSearchCriteria;
	}

	public Date getPublicationDateEndSearchCriteria() {
		return publicationDateEndSearchCriteria;
	}

	public void setPublicationDateEndSearchCriteria(Date publicationDateEndSearchCriteria) {
		this.publicationDateEndSearchCriteria = publicationDateEndSearchCriteria;
	}

	public JournalPublication getCurrentPublication() {
		if (currentPublication == null) {
			currentPublication = new JournalPublication();
		}
		return currentPublication;
	}

	public void setCurrentPublication(JournalPublication currentPublication) {
		this.currentPublication = currentPublication;
	}

	public UploadedFile getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(UploadedFile fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public Journal getCurrentJournal() {
		return currentJournal;
	}

	public void setCurrentJournal(Journal currentJournal) {
		this.currentJournal = currentJournal;
	}

	public Integer getIdCurrentJournal() {
		return idCurrentJournal;
	}

	public void setIdCurrentJournal(Integer idCurrentJournal) {
		this.idCurrentJournal = idCurrentJournal;
	}

	public void loadCurrentJournal() {
		if (idCurrentJournal != null) {
			setCurrentJournal(journalFacade.getJournalById(idCurrentJournal));
		}
	}

	public void searchJournalPublications() {
		setPublications(journalFacade.searchMyJournalPublications(
			   idCurrentJournal, publicationDateStartSearchCriteria, publicationDateEndSearchCriteria));
	}

	public void handleFileUpload(FileUploadEvent event) {
		fileInputStream = event.getFile();
	}

	public void saveChanges(String modalIdToClose) {
		try {
			if (currentPublication.getIdJournalPublication() != null) {
				journalFacade.updateJournalPublication(currentPublication,
					   fileInputStream != null ? fileInputStream.getInputstream() : null,
					   fileInputStream != null ? fileInputStream.getFileName() : null);
				currentPublication.setFileName(fileInputStream.getFileName());
				clearCurrentJournalPublication();
				RequestContext.getCurrentInstance().execute("PF('" + modalIdToClose + "').hide()");
			} else if (fileInputStream == null || fileInputStream.getInputstream() == null) {
				MessagesUtil.addErrorMessage(
					   MessagesUtil.getLocalizedMessage("filerequired"));
			} else {
				journalFacade.createJournalPublication(currentPublication, idCurrentJournal, fileInputStream.getInputstream(), fileInputStream.getFileName());
				clearCurrentJournalPublication();
				RequestContext.getCurrentInstance().execute("PF('" + modalIdToClose + "').hide()");
			}
		} catch (Exception e) {
			//LOG
		}
	}

	public DefaultStreamedContent download(JournalPublication publication) {
		DefaultStreamedContent file = new DefaultStreamedContent(
			   new ByteArrayInputStream(publication.getContent()), "application/pdf", publication.getFileName());
		return file;
	}

	public void deleteJournalPublication(Integer idPublication) {
		currentPublication = journalFacade.getJournalPublicationById(idPublication);
		publications.remove(currentPublication);
		journalFacade.deleteJournalPublication(idPublication);
	}

	public void clearCurrentJournalPublication() {
		setCurrentPublication(null);
		setFileInputStream(null);
	}
}
