package com.jgranados.journals.subscription;

import com.jgranados.journals.subscription.model.JournalSubscription;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * JEE_Tutorial-ejb
 * @author jose - 21.09.2016 
 * @Title: SubscriptionFacadeLocal
 * @Description: description
 *
 * Changes History
 */
@Local
public interface SubscriptionFacadeLocal {
	public JournalSubscription subscribeToJournal(Integer idJournal);
	
	public void removeSubscription(Integer idSubscription);
	
	public List<JournalSubscription> searchMySubscriptions(final String journalName, final Date subscriptionDateIni, final Date subscriptionDateEnd);
	
	public JournalSubscription getJournalSubscriptionById(Integer idJournalSubscription);
}
