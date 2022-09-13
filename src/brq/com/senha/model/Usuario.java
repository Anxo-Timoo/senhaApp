package brq.com.senha.model;

import java.sql.Date;

public class Usuario {

	private int idUsuario;
	private String email;
	private String loginSap;
	private String senha;
	private Date dataAlteracao;
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLoginSap() {
		return loginSap;
	}
	public void setLoginSap(String loginSap) {
		this.loginSap = loginSap;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	
}

