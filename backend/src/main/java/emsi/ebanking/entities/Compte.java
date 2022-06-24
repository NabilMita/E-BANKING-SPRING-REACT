package emsi.ebanking.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor // Getters, setters, constructeur de paramètres et par défaut sont générés
					// automatiquement
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "number")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Compte {

	@Id
	@Column(nullable = false, unique = true)
	private Long number;

	@Column(nullable = false, unique = true)
	private String fullname;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private double balance;

	@Column(length = 15, nullable = false)
	private String type;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date dateOuverture;

	@Column(nullable = false, updatable = false)
	private String role;

	@OneToMany(mappedBy = "compte", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIncludeProperties(value = { "title", "amount", "type", "date" })
	private List<Transaction> transactions = new ArrayList<Transaction>();

	@OneToMany(mappedBy = "compte", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIncludeProperties(value = { "title", "amount" })
	private List<Budget> budgets = new ArrayList<Budget>();

}