package br.agenda.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Contato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4437765969366120350L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	@Size(min = 4, message = "Nome com no mínimo 4 caracteres.")
	private String nome;

	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^([\\w\\-]+\\.)*[\\w\\- ]+@([\\w\\- ]+\\.)+([\\w\\-]{2,3})$", message = "Insira um e-mail válido.")
	private String email;

	private String endereco;

	@Pattern(regexp = "^\\d{0}|\\d{10}|\\d{6}-\\d{4}|[(]\\d{2}[)]\\d{8}|[(]\\d{2}[)]\\d{4}-\\d{4}$", message = "formatação incorreta.")
	private String telefone;

	@Column(nullable = false)
	@Pattern(regexp = "^\\d{11}|\\d{7}-\\d{4}|[(]\\d{2}[)]\\d{9}|[(]\\d{2}[)]\\d{5}-\\d{4}$", message = "formatação incorreta.")
	private String celular;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	private String observacao;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fkusuario", referencedColumnName = "id", nullable = false)
	private Usuario idUsuario;

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = new Date(System.currentTimeMillis());
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
