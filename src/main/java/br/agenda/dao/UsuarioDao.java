package br.agenda.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.agenda.filter.Filtro;
import br.agenda.model.Usuario;
import br.agenda.util.Util;

@Stateless
public class UsuarioDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8646758202192021391L;

	@PersistenceContext(unitName = "AgendaJsfJpaPU")
	EntityManager em;

	public void insere(Usuario usuario) {

		try {

			em.persist(usuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public boolean emailExiste(String email) {
		boolean existe = true;

		try {
			Query query = em.createQuery("select u from Usuario u where u.email=:email");

			query.setParameter("email", email);

			List<Usuario> result = query.getResultList();

			existe = result.isEmpty() ? false : existe;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existe;

	}

	@SuppressWarnings("unchecked")
	public boolean loginExiste(String login) {
		boolean existe = true;

		try {
			Query query = em.createQuery("select u from Usuario u where u.login=:login");

			query.setParameter("login", login);

			List<Usuario> result = query.getResultList();

			existe = result.isEmpty() ? false : existe;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existe;

	}

	@SuppressWarnings("unchecked")
	public boolean checarUsuario(Usuario usuario) {
		boolean existe = true;

		try {
			Query query = em.createQuery("select u from Usuario u where u.login=:login and u.senha=:senha");

			query.setParameter("login", usuario.getLogin());
			query.setParameter("senha", usuario.getSenha());

			List<Usuario> result = query.getResultList();

			existe = result.isEmpty() ? false : existe;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return existe;
	}

	@SuppressWarnings("unchecked")
	public Integer obterIdDoUsuario(String login) {
		Integer id = 0;
		try {
			Query query = em.createQuery("select u.id from Usuario u where u.login=:login");

			query.setParameter("login", login);

			List<Integer> ocorrencias = query.getResultList();

			for (Integer i : ocorrencias) {
				id = i != null && !i.equals("") ? i : id;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;

	}

	public Usuario obterUsuario() {
		Util util = new Util();
		Filtro filtro = new Filtro();
		Usuario usuario = new Usuario();
		// String login = "";
		try {

			Query query = em.createQuery("select u from Usuario u where u.login=:login");

			query.setParameter("login", util.pegarNaSessao());

			usuario = (Usuario) query.getSingleResult();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usuario;

	}

	public void atualiza(Usuario usuario) {
		try {
			em.merge(usuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
