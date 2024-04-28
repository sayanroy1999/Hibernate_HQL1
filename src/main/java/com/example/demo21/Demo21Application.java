package com.example.demo21;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Demo21Application {

	public static void main(String[] args) {

		SpringApplication.run(Demo21Application.class, args);

		Configuration cfg=new Configuration();
		cfg.configure("hibernate.config.xml");
		SessionFactory factory=cfg.buildSessionFactory();

		Student st1=new Student();
		st1.setId(001);
		st1.setName("PIKACHU"); //Also added another data for these 3 fields
		st1.setCity("TOKYO");

		Certificate certificate1=new Certificate();
		certificate1.setCourse("GFG 1");
		certificate1.setDuration("2 months");
		st1.setCerti(certificate1);

		Student st2=new Student();
		st2.setId(002);
		st2.setName("CHARIZARD"); //Also added another data for these 3 fields
		st2.setCity("CHAR ISLAND");

		Certificate certificate2=new Certificate();
		certificate2.setCourse("GFG 2");
		certificate2.setDuration("1.5 months");
		st2.setCerti(certificate2);

		Session session=factory.openSession();

		Transaction tx=session.beginTransaction();
		session.save(st1);
		session.save(st2);
		tx.commit(); //To commit the changes in our DB

		//HQL query
		//Syntax:-
		// 1) String query="from Student";

		//We can also use the query as-
		//2) String query="from Student where city='Tokyo'";

		//We can also use the query as-
		String query="from Student as s where city=:x and s.name=:n"; //making it dynamic

		Query q=session.createQuery(query);
		q.setParameter("x","Tokyo");
		q.setParameter("n","Pikachu");

		//Now from the query v can fetch either multiple result or single result
		//For single result- q.uniqueResult();

		//For multiple results-
		List<Student> list=q.getResultList();
		for(Student s:list)
		{
			System.out.println(s.getName());
			System.out.println(s.getCerti().getCourse());
		}

		session.close();
		factory.close();

		System.out.println("Done");

	}

}
