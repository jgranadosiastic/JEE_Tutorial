package com.jgranados.journals.journal;

import com.jgranados.journals.authentication.enums.RoleEnum;
import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.journal.model.Journal;
import com.jgranados.journals.journal.model.JournalPublication;
import com.jgranados.journals.journal.query.JournalQueryBean;
import com.jgranados.journals.journal.service.JournalService;
import com.jgranados.journals.user.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * JEE_Tutorial-ejb
 *
 * @author jose - 19.09.2016
 * @Title: JournalFacade
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class JournalFacade implements JournalFacadeLocal {

	@EJB
	private JournalService journalService;
	@EJB
	private JournalQueryBean journalQueryBean;
	@EJB
	private AuthenticationService authenticationService;

	@Override
	public Journal createJournal(Journal newJournal) {
		return journalService.createJournal(newJournal);
	}

	@Override
	public Journal updateJournal(Journal journalValues) {
		return journalService.updateJournal(journalValues);
	}

	@Override
	public void deleteJournal(Integer id) {
		journalService.deleteJournal(id);
	}

	@Override
	public JournalPublication createJournalPublication(JournalPublication newJournalPublication, Integer idJournal, InputStream fileInputStream, String fileName) throws IOException {
		return journalService.createJournalPublication(
			   newJournalPublication, idJournal, fileInputStream, fileName);
	}

	@Override
	public JournalPublication updateJournalPublication(JournalPublication journalPublicationValues, InputStream fileInputStream, String fileName) throws IOException {
		return journalService.updateJournalPublication(
			   journalPublicationValues, fileInputStream, fileName);
	}

	@Override
	public void deleteJournalPublication(Integer id) {
		journalService.deleteJournalPublication(id);
	}

	@Override
	public List<Journal> searchMyJournals(String name, String tags) {
		User user = authenticationService.getAuthenticatedUser();
		Integer idProfile = user.getProfile().getIdProfile();
		if (user.getUserRole().equals(RoleEnum.PUBLIC.toString())) {
			return journalQueryBean.searchJournalsSubscriptionAvailable(name, tags, null);
		} else {
			return journalQueryBean.searchJournals(name, tags, idProfile, null);
		}
	}

	@Override
	public Journal getJournalById(int id) {
		return journalQueryBean.getJournalById(id);
	}

	@Override
	public JournalPublication getJournalPublicationById(int id) {
		return journalQueryBean.getJournalPublicationById(id);
	}

	@Override
	public List<JournalPublication> searchMyJournalPublications(Integer journal, Date publicationDateIni, Date publicationDateEnd) {
		User user = authenticationService.getAuthenticatedUser();
		Integer idProfile = user.getProfile().getIdProfile();
		if (user.getUserRole().equals(RoleEnum.PUBLIC.toString())) {
			idProfile = null;
		}
		return journalQueryBean.searchJournalPublications(
			   journal, publicationDateIni, publicationDateEnd, idProfile);

	}

}
