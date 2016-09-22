package com.jgranados.journals.subscription;

import com.jgranados.journals.subscription.model.JournalSubscription;
import com.jgranados.journals.subscription.query.SubscriptionQueryBean;
import com.jgranados.journals.subscription.service.SubscriptionServiceBean;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * JEE_Tutorial-ejb
 *
 * @author jose - 21.09.2016
 * @Title: SubscriptionFacade
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class SubscriptionFacade implements SubscriptionFacadeLocal {

	@EJB
	private SubscriptionQueryBean subscriptionQueryBean;

	@EJB
	private SubscriptionServiceBean subscriptionServiceBean;

	@Override
	public JournalSubscription subscribeToJournal(Integer idJournal) {
		return subscriptionServiceBean.subscribeToJournal(idJournal);
	}

	@Override
	public void removeSubscription(Integer idSubscription) {
		subscriptionServiceBean.removeSubscription(idSubscription);
	}

	@Override
	public List<JournalSubscription> searchMySubscriptions(String journalName, Date subscriptionDateIni, Date subscriptionDateEnd) {
		return subscriptionQueryBean.searchMySubscriptions(journalName, subscriptionDateIni, subscriptionDateEnd);
	}
	
	@Override
	public JournalSubscription getJournalSubscriptionById(Integer idJournalSubscription) {
		return subscriptionQueryBean.getJournalSubscriptionById(idJournalSubscription);
	}

}
