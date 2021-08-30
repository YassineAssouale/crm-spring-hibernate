package crm.hibernate.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="customers")
@NamedQuery(name="MyCusotmer.findByActive", query ="SELECT c FROM MyCustomer c WHERE c.active = :active")
public class MyCustomer implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 100)
	private String lastname;
	
	@Column(length = 100)
	private String firstname;
	
	@Column(length = 200)
	private String company;
	
	@Column(length = 255)
	private String mail;
	
	@Column(length = 15)
	private String phone;
	
	@Column(length = 15)
	private String mobile;
	
	private Boolean active;
	
	@Column(columnDefinition = "TEXT")
	private String notes;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<MyOrder> orders;

	public MyCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyCustomer(Integer id, String lastname, String firstname, String company, String mail, String phone,
			String mobile, Boolean active, String notes) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.company = company;
		this.mail = mail;
		this.phone = phone;
		this.mobile = mobile;
		this.active = active;
		this.notes = notes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<MyOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<MyOrder> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "MyCustomer [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", company=" + company
				+ ", mail=" + mail + ", phone=" + phone + ", mobile=" + mobile + ", active=" + active + ", notes="
				+ notes + "]";
	}

	
	
	
	
}
