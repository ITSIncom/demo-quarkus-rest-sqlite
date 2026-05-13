package it.itsincom.resource;

import io.smallrye.jwt.build.Jwt;
import it.itsincom.entity.User;
import it.itsincom.model.LoginRequest;
import it.itsincom.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.security.MessageDigest;
import java.time.Duration;
import java.util.Base64;
import java.util.Set;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserRepository userRepository;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @POST
    @Path("/register")
    @Transactional
    public Response register(LoginRequest req) {
        if (userRepository.findByUsername(req.username) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"username already taken\"}")
                    .build();
        }
        User user = new User();
        user.username = req.username;
        user.passwordHash = hash(req.password);
        user.role = "user";
        userRepository.persist(user);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        User user = userRepository.findByUsername(req.username);
        if (user == null || !user.passwordHash.equals(hash(req.password))) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"invalid credentials\"}")
                    .build();
        }
        String token = Jwt.issuer(issuer)
                .subject(user.username)
                .groups(Set.of(user.role))
                .expiresIn(Duration.ofHours(1))
                .sign();
        return Response.ok("{\"token\":\"" + token + "\"}").build();
    }

    private String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return Base64.getEncoder().encodeToString(md.digest(input.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
