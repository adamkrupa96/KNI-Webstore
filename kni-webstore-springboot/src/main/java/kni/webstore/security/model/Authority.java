package kni.webstore.security.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "AUTHORITY")
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthorityEnum name;

	@ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
	private List<User> users;

	public Authority(AuthorityEnum name) {
		this.name = name;
	}
	
	public Authority() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuthorityEnum getName() {
		return name;
	}

	public void setName(AuthorityEnum name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}