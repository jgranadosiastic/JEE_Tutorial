package com.jgranados.journals.journal.view;

import com.jgranados.journals.journal.JournalFacadeLocal;
import com.jgranados.journals.journal.model.Journal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 * JEE_Tutorial-war
 *
 * @author jose - 19.09.2016
 * @Title: JournalsView
 * @Description: description
 *
 * Changes History
 */
@Named
@ViewScoped
public class JournalsView implements Serializable {

	@EJB
	private JournalFacadeLocal journalFacade;

	List<Journal> journals = new ArrayList<>();
	//search criterias
	String nameSearchCriteria;
	String tagSearchCriteria;

	//new or selected journal
	Journal currentJournal;

	public JournalFacadeLocal getJournalFacade() {
		return journalFacade;
	}

	public void setJournalFacade(JournalFacadeLocal journalFacade) {
		this.journalFacade = journalFacade;
	}

	public List<Journal> getJournals() {
		return journals;
	}

	public void setJournals(List<Journal> journals) {
		this.journals = journals;
	}

	public String getNameSearchCriteria() {
		return nameSearchCriteria;
	}

	public void setNameSearchCriteria(String nameSearchCriteria) {
		this.nameSearchCriteria = nameSearchCriteria;
	}

	public String getTagSearchCriteria() {
		return tagSearchCriteria;
	}

	public void setTagSearchCriteria(String tagSearchCriteria) {
		this.tagSearchCriteria = tagSearchCriteria;
	}

	public Journal getCurrentJournal() {
		if (currentJournal == null) {
			currentJournal = new Journal();
		}
		return currentJournal;
	}

	public void setCurrentJournal(Journal currentJournal) {
		this.currentJournal = currentJournal;
	}

	public void searchJournals() {
		setJournals(journalFacade.searchMyJournals(nameSearchCriteria, tagSearchCriteria));
	}
	
	public void saveChanges(String modalIdToClose) {
		if (currentJournal.getIdJournal() != null) {
			journalFacade.updateJournal(currentJournal);
		} else {
			journalFacade.createJournal(currentJournal);
		}
		clearCurrentJournal();
		RequestContext.getCurrentInstance().execute("PF('" + modalIdToClose + "').hide()");
	}
	
	public void deleteJournal(Integer idJournal) {
		currentJournal = journalFacade.getJournalById(idJournal);
		journals.remove(currentJournal);
		journalFacade.deleteJournal(idJournal);
		clearCurrentJournal();
	}
	
	public void clearCurrentJournal() {
		setCurrentJournal(null);
	}

}
