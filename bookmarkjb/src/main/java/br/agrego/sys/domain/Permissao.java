package br.agrego.sys.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="DEF_PERMISSAO")
public class Permissao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static Permissao valueOf(Grupo grupo){
		Permissao permissao = new Permissao();
		permissao.grupo = grupo;
		return permissao;
	};
	public static Permissao valueOf(EnumMenu menu){
		Permissao permissao = new Permissao();
		permissao.menu = menu;
		return permissao;
	};
	public static Permissao valueOf(EnumMenu menu, EnumTipoPermissao visualizar){
		Permissao permissao = new Permissao();
		permissao.menu = menu;
		permissao.visualizar = visualizar;
		return permissao;
	};
	public static Permissao valueOf(Grupo grupo, EnumMenu menu){
		Permissao permissao = new Permissao();
		permissao.menu = menu;
		permissao.grupo = grupo;
		return permissao;
	};

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	//visualizar, criar, atualizar, excluir, imprimir
	//0=neutro 1=permitido negativo=negado(-127 valor minimo to tipo byte)
	//negado tem precedencia sobre permitido e neutro
	//permitido tem precedencia sobre neutro
	@Column(name="VISUALIZAR") @Enumerated(EnumType.STRING)
	private EnumTipoPermissao visualizar=EnumTipoPermissao.INDETERMINADO;

	@Column(name="CRIAR") @Enumerated(EnumType.STRING)
	private EnumTipoPermissao criar=EnumTipoPermissao.INDETERMINADO;

	@Column(name="ALTERAR") @Enumerated(EnumType.STRING)
	private EnumTipoPermissao alterar=EnumTipoPermissao.INDETERMINADO;

	@Column(name="EXCLUIR") @Enumerated(EnumType.STRING)
	private EnumTipoPermissao excluir=EnumTipoPermissao.INDETERMINADO;

	@Column(name="IMPRIMIR") @Enumerated(EnumType.STRING)
	private EnumTipoPermissao imprimir=EnumTipoPermissao.INDETERMINADO;
	
	@ManyToOne(cascade = { CascadeType.MERGE }, optional = false, targetEntity = Grupo.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "GRUPO_ID")
	private Grupo grupo;
	
//	@ManyToOne(cascade = { CascadeType.ALL }, optional = false, targetEntity = EnumMenu.class, fetch = FetchType.EAGER)
//	@JoinColumn(name = "MENU_ID")
	@Column(name="MENU_ID") @Enumerated(EnumType.STRING)
	private EnumMenu menu;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EnumTipoPermissao getVisualizar() {
		return visualizar;
	}

	public void setVisualizar(EnumTipoPermissao visualizar) {
		this.visualizar = visualizar;
	}

	public EnumTipoPermissao getCriar() {
		return criar;
	}

	public void setCriar(EnumTipoPermissao criar) {
		this.criar = criar;
	}

	public EnumTipoPermissao getAlterar() {
		return alterar;
	}

	public void setAlterar(EnumTipoPermissao alterar) {
		this.alterar = alterar;
	}

	public EnumTipoPermissao getExcluir() {
		return excluir;
	}

	public void setExcluir(EnumTipoPermissao excluir) {
		this.excluir = excluir;
	}

	public EnumTipoPermissao getImprimir() {
		return imprimir;
	}

	public void setImprimir(EnumTipoPermissao imprimir) {
		this.imprimir = imprimir;
	}
	
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public EnumMenu getMenu() {
		return menu;
	}

	public void setMenu(EnumMenu menu) {
		this.menu = menu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permissao other = (Permissao) obj;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (menu != other.menu)
			return false;
		return true;
	}

	

}

