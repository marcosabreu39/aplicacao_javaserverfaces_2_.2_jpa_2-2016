package br.agenda.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.agenda.model.Contato;
import br.agenda.model.Usuario;

@Stateless
public class ContatoDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6291730158989254385L;

	@PersistenceContext(name = "AgendaJsfJpaPU")
	EntityManager em;

	public void insere(Contato contato) {

		try {
			em.persist(contato);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void atualiza(Contato contato) {
		try {
			em.merge(contato);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remove(Contato contato) {
		try {
			em.remove(em.getReference(Contato.class, contato.getId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Contato> buscaPorAtributo(String nomeAtributo, String atributo) {
		List<Contato> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("select c from Contato c where c." + nomeAtributo + "=:" + atributo + "");

			query.setParameter(nomeAtributo, atributo);

			lista = query.getResultList();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}

	@SuppressWarnings("unchecked")
	public List<Contato> listarOrdenadoPorAtributo(Usuario usuario, String atributo) {
		List<Contato> contatosOrdenados = new ArrayList<>();
		
		try {
			Query query = em
					.createQuery("select c from Contato c where c.idUsuario=:idUsuario order by c.".concat(atributo));

			query.setParameter("idUsuario", usuario);
			
			contatosOrdenados = query.getResultList();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contatosOrdenados;
	}

	@SuppressWarnings("unchecked")
	public boolean emailExiste(String email) {
		boolean existe = true;

		try {
			Query query = em.createQuery("select c from Contato c where c.email=:email");

			query.setParameter("email", email);

			List<Usuario> result = query.getResultList();

			existe = result.isEmpty() ? false : existe;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existe;

	}

}
