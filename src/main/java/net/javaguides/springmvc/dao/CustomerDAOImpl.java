package net.javaguides.springmvc.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.springmvc.entity.Customer;

@Repository
@Transactional
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<Customer> getCustomers() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> root = cq.from(Customer.class);
		cq.select(root);
		Query query = session.createQuery(cq);
		return query.getResultList();
	}

	@Transactional
	public List<Customer> startsWith(String keyword) {
		System.out.println("in searcchdossaaaaaaa    " + keyword);

		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);

		Root<Customer> root = query.from(Customer.class);

		/*
		 * search record containing string
		 * query.select(root).where(builder.like(root.get("firstName"), "%"+keyword + "%"));
		 */
		
		/*
		 * search a record ,starting with this string
		 * query.select(root).where(builder.like(root.get("firstName"), keyword + "%"));
		 */
		
		
		//search a record ,ending with this string
		query.select(root).where(builder.like(root.get("firstName"), "%"+keyword ));
		
		return session.createQuery(query).getResultList();

	}

	@Transactional
	public List<Customer> getByAscOrder() {
		System.out.println("============================================================in dao");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.addOrder(Order.asc("email"));

		return criteria.list();

	}

	@Override
	@Transactional
	public void deleteCustomer(int id) {
		Session session = sessionFactory.getCurrentSession();
		Customer book = session.byId(Customer.class).load(id);
		session.delete(book);
	}

	@Override
	@Transactional
	public void saveCustomer(Customer theCustomer) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	@Transactional
	public Customer getCustomer(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Customer theCustomer = currentSession.get(Customer.class, theId);
		return theCustomer;
	}
	
	public  Long getTotalRecord() {
		Session currentSession = sessionFactory.getCurrentSession();
		
			
			
			CriteriaBuilder builder =  currentSession.getCriteriaBuilder();
			CriteriaQuery<Long> query = builder.createQuery(Long.class);
			Root<Customer> root = query.from(Customer.class);
			query.select(builder.count(root.get("id")));
			Long result = (Long) currentSession.createQuery(query).getSingleResult();
			return result;
			
		
			
			
	}
}