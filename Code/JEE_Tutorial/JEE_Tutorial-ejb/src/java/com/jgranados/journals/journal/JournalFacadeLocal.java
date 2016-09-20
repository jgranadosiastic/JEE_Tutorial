package com.jgranados.journals.journal;

import com.jgranados.journals.journal.model.Journal;
import com.jgranados.journals.journal.model.JournalPublication;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * JEE_Tutorial-ejb
 *
 * @author jose - 19.09.2016
 * @Title: JournalFacadeLocal
 * @Description: description
 *
 * Changes History
 */
@Local
public interface JournalFacadeLocal {

	public Journal createJournal(Journal newJournal);

	public Journal updateJournal(Journal journalValues);

	public void deleteJournal(Integer id);

	public JournalPublication createJournalPublication(final JournalPublication newJournalPublication, final Integer idJournal, final InputStream fileInputStream, final String fileName) throws IOException;

	public JournalPublication updateJournalPublication(JournalPublication journalPublicationValues, final InputStream fileInputStream, final String fileName) throws IOException;

	public void deleteJournalPublication(Integer id);

	public List<Journal> searchJournals(final String name, final String tags, final Integer ownerProfile, final Boolean active);

	public Journal getJournalById(final int id);

	public JournalPublication getJournalPublicationById(final int id);

	public List<JournalPublication> searchJournalPublications(final Integer journal, final Date publicationDateIni, final Date publicationDateEnd, final Integer ownerProfile);

	public List<Journal> searchJournalsSubscriptionAvailable(final String name, final String tags, final Integer ownerProfile);

}
