package com.jgranados.journals.subscription.query;

import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.journal.model.Journal_;
import com.jgranados.journals.subscription.model.JournalSubscription;
import com.jgranados.journals.subscription.model.JournalSubscription_;
import com.jgranados.journals.user.model.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * JEE Tutorial
 *
 * @author jose - 09.07.2016
 * @Title: SubscriptionQueryBean
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class SubscriptionQueryBean {

	@EJB
	private AuthenticationService authenticationService;

	@PersistenceContext(unitName = "JEE_Tutorial-PU")
	private EntityManager em;
	
	public JournalSubscription getJournalSubscriptionById(Integer idJournalSubscription) {
		return em.find(JournalSubscription.class, idJournalSubscription);
	}

	public List<JournalSubscription> searchMySubscriptions(final String journalName, final Date subscriptionDateIni, final Date subscriptionDateEnd) {
		CriteriaBuilder journalSubscriptionBuilder = em.getCriteriaBuilder();
		CriteriaQuery<JournalSubscription> query = journalSubscriptionBuilder.createQuery(JournalSubscription.class);
		Root<JournalSubscription> journalSubscriptionRoot = query.from(JournalSubscription.class);
		query.select(journalSubscriptionRoot);

		List<Predicate> predicateList = new ArrayList<>();

		Predicate journalPredicate, subscriptionDatePredicate, subscriberPredicate;

		if (journalName != null && !journalName.isEmpty()) {
			journalPredicate = journalSubscriptionBuilder.like(
				   journalSubscriptionBuilder.upper(journalSubscriptionRoot.get(JournalSubscription_.journal).get(Journal_.name)), "%" + journalName + "%");
			predicateList.add(journalPredicate);
		}

		if (subscriptionDateIni != null && subscriptionDateEnd != null) {
			subscriptionDatePredicate = journalSubscriptionBuilder.between(
				   journalSubscriptionRoot.get(JournalSubscription_.subscriptionDate), subscriptionDateIni, subscriptionDateEnd);
			predicateList.add(subscriptionDatePredicate);
		}

		User currentUser = authenticationService.getAuthenticatedUser();
		subscriberPredicate = journalSubscriptionBuilder.equal(journalSubscriptionRoot.get(JournalSubscription_.user), em.find(User.class, currentUser.getIdUser()));
		predicateList.add(subscriberPredicate);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);

		return em.createQuery(query).getResultList();
	}

}
