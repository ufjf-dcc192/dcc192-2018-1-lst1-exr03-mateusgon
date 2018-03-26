import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "JurosServlet", urlPatterns = {"/index.html"})
public class JurosServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        Double valor05 = 1000.0;
        Double valor1 = 1000.0;
        Double valor15 = 1000.0;
        Double valorUsuario = 1000.0;
        int qtddMeses;
        double porcentJuros;
        try (PrintWriter out = resp.getWriter()) {
            if (req.getParameter("qtddMeses") != null && !"".equals(req.getParameter("qtddMeses")))
            {
                qtddMeses = Integer.parseInt(req.getParameter("qtddMeses"));
            }
            else 
            {
                qtddMeses = 12;
            }
            if (req.getParameter("porcentJuros") != null && !"".equals(req.getParameter("porcentJuros"))) 
            {
                porcentJuros = Double.parseDouble(req.getParameter("porcentJuros")) / 100;
            }
            else 
            {
                porcentJuros = 0.010;
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title> Disposição dos Juros </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p> Simulação dos juros </p>");
            LocalDate atual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM/YYYY");
            out.println("<table border="+1+">");
            out.println("<tr>");
            out.println("<th>Meses</th>");
            out.println("<th>Juros de 0,5%</th>");
            out.println("<th>Juros de 1,0%</th>");
            out.println("<th>Juros de 1,5%</th>");
            if (porcentJuros != 0.010)
            {
                 out.println("<th>" + "Juros de " + porcentJuros + "</th>");
            }
            out.println("</tr>");
            out.println("<tr>");
            for (int j = 0; j < qtddMeses; j++)
            {
                LocalDate data = atual.plusMonths(j);
                out.println ("<tr>");
                out.println ("<td>" + data.format(formatter) + "</td>");
                valor05 = calculaValor (j,  0.005, valor05);
                valor1 = calculaValor (j, 0.010, valor1);
                valor15 = calculaValor (j, 0.015, valor15);
                out.println ("<td>" + valor05 + "</td>");
                out.println ("<td> " + valor1 + "  </td>");
                out.println ("<td>" + valor15 + "  </td>");
                if (porcentJuros != 0.01)
                {
                    valorUsuario = calculaValor (j, porcentJuros, valorUsuario);
                    out.println("<td>" + valorUsuario + "  </td>");
                }
                out.println ("</tr>");
            }   
            out.println ("</tr>");
            out.println("</table>");
            out.println("<form>");
            out.println("<label>Juros pretendido: </label><input type=\"text\" name=\"porcentJuros\"><br>");
            out.println("<label>Meses pretendido: </label><input type=\"text\" name=\"qtddMeses\"><br>");
            out.println("<button type=\"submit\">Submeter</button>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private Double calculaValor(int mes, double juros, double valor) {
        return valor * (1 + mes * (juros));
    }

    
}
