package Controllers;

import Authetication.UserDTO;
import Authetication.UserPrivilege;
import Configuration.JwtTokenUtil;
import Configuration.RandomPasswordGenerator;
import Mail.EmailClient;
import Models.User.User;
import Models.User.UserLogin;
import MyInterceptors.MyInterceptor;
import Repository.UserRepository;
import Response.JsonResponse;
import io.jsonwebtoken.Claims;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("auth")
@Stateless
@Interceptors(MyInterceptor.class)
public class JwtAuthenticationController {
    // Provides us with the necessary Token methods
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    @EJB
    private EmailClient emailClient = new EmailClient();

    @EJB
    private UserRepository userRepository;

    @POST
    @Path("sendRandomPassword")
    public Response mailPassword(UserLogin userLogin, @Context HttpServletRequest request) throws MessagingException{
        String randomPass = RandomPasswordGenerator.generatePassword(8);
        JSONObject response = new JSONObject();

        if(userRepository.login(userLogin.getEmail(), DigestUtils.sha512Hex(userLogin.getPassword()))){
            User user = userRepository.find(userLogin.getEmail());

            user.setReceived(new Date());
            user.setRandomPassword(DigestUtils.sha512Hex(randomPass));

            userRepository.update(user);

            response.put("user", user.toJsonCustom());

            emailClient.sendAsHtml(userLogin.getEmail(), "Password boy", randomPass);
        }

        return Response.ok(response.toString(2)).build();
    }

    @POST
    @Path("login")
    public Response authenticate(UserLogin userLogin, @Context HttpServletRequest request) throws MessagingException {
        JsonResponse json = new JsonResponse();
        String token = null;
        json.setData(userLogin);

        try {
            if(userRepository.twoFactorLogin(userLogin.getEmail(), DigestUtils.sha512Hex(userLogin.getPassword()))){
                User user = userRepository.find(userLogin.getEmail());
                token = jwtTokenUtil.generateToken(user);

                json.setData(token);
                json.setStatus("SUCCES");

                return Response.ok().header(AUTHORIZATION, "Bearer " + token).entity(json).build();
            } else{
                json.setStatus("FAILED");
                json.setErrorMsg("We failed to authenticate: " + json.toString());

                return Response.status(401).entity(json).build();
            }
        } catch (Exception e){
            json.setStatus("FAILED");
            json.setErrorMsg("We failed to authenticate: " + json.toString());

            return Response.status(401).entity(json).build();
        }
    }

    @POST
    @Path("register")
    public Response registerUser(UserDTO newUser, @Context HttpServletRequest request) {
        JsonResponse json = new JsonResponse();
        json.setData(newUser);

        if (newUser.getPassword1().length() == 0 || !newUser.getPassword1().equals(newUser.getPassword2())) {
            json.setErrorMsg("Both password have to be the same.");
            json.setStatus("FAILED");

            return Response.status(500).entity(json).build();
        }

        User user = new User(newUser);

        List<UserPrivilege> privileges = new ArrayList<UserPrivilege>();
        privileges.add(UserPrivilege.USER);
        user.setPrivileges(privileges);

        try{
            userRepository.save(user);
            json.setData(user);

            return Response.ok().entity(json).build();
        }catch (Exception e){
            json.setStatus("FAILED");
            json.setErrorMsg("Creating user failed");

            return Response.status(500).entity(json).build();
        }

    }


    @GET
    @Path("user")
    public Response getUser(@Context HttpServletRequest req)
    {
        String token = req.getHeader("Authorization").substring("Bearer".length()).trim();

        User user = userRepository.find(jwtTokenUtil.getUsernameFromToken(token));
        userRepository.detach(user);
        user.setPassword(null);

        if(jwtTokenUtil.validateAdmin(token)){
            return Response.ok().entity(user).build();
        } else {
            return Response.status(403).build();
        }
    }


}
