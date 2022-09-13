package brq.com.senha.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import brq.com.senha.dao.UsuarioDAO;
import brq.com.senha.model.Usuario;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/register")
public class UsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private UsuarioDAO usuarioDao = new UsuarioDAO();
     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int result = 0;
		String retorno = "";
		int sysubrc = 0;
		String usuarioSap = request.getParameter("usuario");
		String email = request.getParameter("email");
		
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setLoginSap(usuarioSap);
		
		try {
			result = usuarioDao.registerUserSAP(usuario);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//response.sendRedirect("");
		//doGet(request, response);
		
		if (result > 0)
		{
			retorno = "Solicitação realizada com sucesso.Um email foi enviado para " + usuario.getEmail() + " com a nova senha";
			request.getSession().setAttribute("message", retorno);
			request.getSession().setAttribute("sysubrc", 0);
	        //response.sendRedirect("index.jsp");
	        request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else 
		{
			retorno = "Ocorreu um erro ao realizar sua solicitação. Por favor tente mais tarde";
			request.getSession().setAttribute("message", retorno);
			request.getSession().setAttribute("sysubrc", 1);
			request.getRequestDispatcher("index.jsp").forward(request, response);
	        //response.sendRedirect("index.jsp");
		}
	}

}
