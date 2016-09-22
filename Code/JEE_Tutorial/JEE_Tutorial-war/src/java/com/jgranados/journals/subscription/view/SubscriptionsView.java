package com.jgranados.journals.subscription.view;

import com.jgranados.journals.journal.view.JournalsView;
import com.jgranados.journals.subscription.SubscriptionFacadeLocal;
import com.jgranados.journals.subscription.model.JournalSubscription;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * JEE_Tutorial-war
 *
 * @author jose - 21.09.2016
 * @Title: SubscriptionsView
 * @Description: description
 *
 * Changes History
 */
@Named
@ViewScoped
public class SubscriptionsView implements Serializable {

	@EJB
	private SubscriptionFacadeLocal subscriptionFacade;

	private List<JournalSubscription> subscriptions = new ArrayList<>();

	//search criterias
	private String journalNameSearchCriteria;
	Date subscriptionDateStartSearchCriteria;
	Date subscriptionDateEndSearchCriteria;

	//journal search data
	@Inject
	private JournalsView journalsView;

	public List<JournalSubscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<JournalSubscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public String getJournalNameSearchCriteria() {
		return journalNameSearchCriteria;
	}

	public void setJournalNameSearchCriteria(String journalNameSearchCriteria) {
		this.journalNameSearchCriteria = journalNameSearchCriteria;
	}

	public Date getSubscriptionDateStartSearchCriteria() {
		return subscriptionDateStartSearchCriteria;
	}

	public void setSubscriptionDateStartSearchCriteria(Date subscriptionDateStartSearchCriteria) {
		this.subscriptionDateStartSearchCriteria = subscriptionDateStartSearchCriteria;
	}

	public Date getSubscriptionDateEndSearchCriteria() {
		return subscriptionDateEndSearchCriteria;
	}

	public void setSubscriptionDateEndSearchCriteria(Date subscriptionDateEndSearchCriteria) {
		this.subscriptionDateEndSearchCriteria = subscriptionDateEndSearchCriteria;
	}

	public JournalsView getJournalsView() {
		return journalsView;
	}

	public void setJournalsView(JournalsView journalsView) {
		this.journalsView = journalsView;
	}

	public void searchJournalSubscriptions() {
		setSubscriptions(subscriptionFacade.searchMySubscriptions(
			   journalNameSearchCriteria, subscriptionDateStartSearchCriteria,
			   subscriptionDateEndSearchCriteria));
	}
	
	public void subscribeToJournal(Integer idJournal) {
		subscriptionFacade.subscribeToJournal(idJournal);
		journalsView.setCurrentJournal(journalsView.getJournalFacade().getJournalById(idJournal));
		journalsView.getJournals().remove(journalsView.getCurrentJournal());
	}
	
	public void deleteSubscription(Integer idSubscription) {
		JournalSubscription current = subscriptionFacade.getJournalSubscriptionById(idSubscription);
		subscriptionFacade.removeSubscription(idSubscription);
		subscriptions.remove(current);
	}

}
