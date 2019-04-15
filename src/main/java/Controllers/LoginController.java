package Controllers;

import Authetication.UserBean;
import Authetication.UserDTO;
import Authetication.UserPrivilege;
import Models.User;
import Models.UserLogin;
import Response.JsonResponse;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("authJAAS")
public class LoginController {
    @EJB
    private UserBean userBean;

    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Response register(UserDTO newUser, @Context HttpServletRequest req) {

        JsonResponse json = new JsonResponse();
        json.setData(newUser); // just return the date we received

        if (newUser.getPassword1().length() == 0 || !newUser.getPassword1().equals(newUser.getPassword2())) {
            json.setErrorMsg("Both passwords have to be the same - typo?");
            json.setStatus("FAILED");
            return Response.ok().entity(json).build();
        }

        User user = new User(newUser);

        List<UserPrivilege> roles = new ArrayList<UserPrivilege>();
        roles.add(UserPrivilege.Admin);
        roles.add(UserPrivilege.User);
        roles.add(UserPrivilege.Stats);

        user.setPrivileges(roles);

        userBean.save(user);
        req.getServletContext().log("successfully registered new user: '" + newUser.getEmail() + "':'" + newUser.getPassword1() + "'");

        req.getServletContext().log("execute login now: '" + newUser.getEmail() + "':'" + newUser.getPassword1() + "'");
        try {
            req.login(newUser.getEmail(), newUser.getPassword1());
            json.setStatus("SUCCESS");
        } catch (ServletException e) {
            e.printStackTrace();
            json.setErrorMsg("User Account created, but login failed. Please try again later.");
            json.setStatus("FAILED");
            return Response.serverError().entity(json).build();
        }

        return Response.ok().entity(json).build();
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Response login(UserLogin userLogin,
                          @Context HttpServletRequest req) {

        JsonResponse json = new JsonResponse();

        if(req.getUserPrincipal() == null){
            try {
                req.login(userLogin.getEmail(), userLogin.getPassword());
                req.getServletContext().log("Authentication Demo: successfully logged in " + userLogin.getEmail());
            } catch (ServletException e) {
                e.printStackTrace();
                json.setStatus("FAILED");
                json.setErrorMsg("Authentication failed" + e);
                return Response.serverError().entity(json).build();
            }
        }else{
            req.getServletContext().log("Skip logged because already logged in: " + userLogin.getEmail());
        }

        json.setStatus("SUCCESS");

        User user = userBean.find(userLogin.getEmail());
        req.getServletContext().log("Authentication Demo: successfully retrieved User Profile from DB for " + userLogin.getEmail());
        json.setData(user);

        userBean.detach(user);
        user.setPassword(null);
        user.setPrivileges(null);
        return Response.ok().entity(json).build();
    }

    @GET
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context HttpServletRequest req) {

        JsonResponse json = new JsonResponse();

        try {
            req.logout();
            json.setStatus("SUCCESS");
            req.getSession().invalidate();
        } catch (ServletException e) {
            e.printStackTrace();
            json.setStatus("FAILED");
            json.setErrorMsg("Logout failed on backend");
        }
        return Response.ok().entity(json).build();
    }
}
