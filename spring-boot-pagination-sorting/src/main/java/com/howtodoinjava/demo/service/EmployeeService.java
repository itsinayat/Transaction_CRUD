package com.howtodoinjava.demo.service;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.howtodoinjava.demo.exception.RecordNotFoundException;
import com.howtodoinjava.demo.model.EmployeeEntity;
import com.howtodoinjava.demo.repository.EmployeeRepository;
import com.opencsv.CSVReader;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;

	public List<EmployeeEntity> getAllEmployees(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<EmployeeEntity> pagedResult = repository.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<EmployeeEntity>();
		}
	}

	public EmployeeEntity getEmployeeById(Long id) throws RecordNotFoundException {
		Optional<EmployeeEntity> employee = repository.findById(id);

		if (employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}

	public EmployeeEntity createOrUpdateEmployee(@RequestBody EmployeeEntity entity) throws RecordNotFoundException {
		Long id = entity.getId();
		Optional<EmployeeEntity> employee = null;
		if (id != null) {
			employee = repository.findById(entity.getId());
			EmployeeEntity newEntity = employee.get();
			if (employee.isPresent()) {
				newEntity.setAccountNo(entity.getAccountNo());
				newEntity.setBalanceAmt(entity.getBalanceAmt());
				newEntity.setDate(entity.getDate());
				newEntity.setDepositAmt(entity.getDepositAmt());
				newEntity.setTransactionDetails(entity.getTransactionDetails());
				newEntity.setValueDate(entity.getValueDate());
				newEntity.setWithdrwalAmt(entity.getWithdrwalAmt());
				newEntity = repository.save(newEntity);

			}
			return newEntity;
		} else {
			entity = repository.save(entity);
			return entity;
		}
	}

	public void deleteEmployeeById(Long id) throws RecordNotFoundException {
		Optional<EmployeeEntity> employee = repository.findById(id);

		if (employee.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}

	// define field for entitymanager
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public EmployeeService(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	private static final String FILE_NAME = "src/main/resources/Bank_transactions.csv";

	public void importDate() {

		CSVReader reader = null;
		EmployeeEntity entity = null;
		try {
			reader = new CSVReader(new FileReader(FILE_NAME));

			String[] line;
			EntityManager reEntityManager = null;
			reader.readNext();
			Session currentSession = entityManager.unwrap(Session.class);

			String sqlStr = "insert into  esridb.bank_transactions "
					+ "(account_no,date,transaction_details,value_date," + "withdrawal_amt,deposit_amt,balance_amt )"
					+ " values (?, ?, ?, ?, ? , ?, ?)";
			while ((line = reader.readNext()) != null) {
				// System.out.println(line[1]);
				// System.out.println(Long.parseLong(line[0]));
				entity = new EmployeeEntity();
				entity.setAccountNo(Long.parseLong(line[0]));
				entity.setDate(dateUtil(line[1]));
				entity.setTransactionDetails(line[2]);
				entity.setValueDate(dateUtil(line[3]));
				entity.setWithdrwalAmt(Double.parseDouble((line[4].isEmpty()) ? "0.00" : line[4].replaceAll(",", "")));
				entity.setDepositAmt(Double.parseDouble((line[5].isEmpty()) ? "0.00" : line[5].replaceAll(",", "")));
				entity.setBalanceAmt(Double.parseDouble((line[6].isEmpty()) ? "0.00" : line[6].replaceAll(",", "")));
				entity = repository.save(entity);
				// System.out.println(entity);
				entity = null;

//				int m = currentSession.createQuery(sqlStr)
//				.setParameter(1, line[0])
//				.setParameter(2, dateUtil(line[1]))
//				.setParameter(3, line[2])
//				.setParameter(4, dateUtil(line[3]))
//				.setParameter(5, Double.parseDouble((line[4].isEmpty())? "0.00" : line[4].replaceAll(",", "")))
//				.setParameter(6, Double.parseDouble((line[5].isEmpty())? "0.00" : line[5].replaceAll(",", "")))
//				.setParameter(7, Double.parseDouble((line[6].isEmpty())? "0.00" : line[6].replaceAll(",", ""))).executeUpdate();
//				System.out.println(m);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String dateUtil(String str) {
		// String str = "29 Jun 17";
		String[] arrOfStr = str.split(" ");
		String month = arrOfStr[1];
		String mon = "";
		switch (month) {
		case "Jan":
			mon = "01";
			break;
		case "Feb":
			mon = "02";
			break;
		case "Mar":
			mon = "03";
			break;
		case "Apr":
			mon = "04";
			break;
		case "May":
			mon = "05";
			break;
		case "Jun":
			mon = "06";
			break;
		case "Jul":
			mon = "07";
			break;
		case "Aug":
			mon = "08";
			break;
		case "Sep":
			mon = "09";
			break;
		case "Oct":
			mon = "10";
			break;
		case "Nov":
			mon = "11";
			break;
		case "Dec":
			mon = "12";
			break;
		}
		int year = 2000 + Integer.parseInt(arrOfStr[2]);
		String dateInString = arrOfStr[0] + "-" + mon + "-" + year;
		// System.out.println(dateInString);
		DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = inputFormat.parse(dateInString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);

	}

}