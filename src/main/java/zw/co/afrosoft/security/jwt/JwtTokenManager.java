package zw.co.afrosoft.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import zw.co.afrosoft.model.Employee;
import zw.co.afrosoft.model.User;
import zw.co.afrosoft.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtTokenManager {

	private final JwtProperties jwtProperties;

	public String generateToken(User user) {

		final String username = user.getUsername();
		final UserRole userRole = user.getUserRole();
		final Long user1 = user.getId();
		final Employee employee = user.getEmployee();





		//@formatter:off
		return JWT.create()
				.withSubject(String.valueOf(employee))
				.withIssuer(jwtProperties.getIssuer())
				.withClaim("role", userRole.name())
				.withClaim("userId",user1)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMinute() * 60 * 1000))
				.sign(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes()));
		//@formatter:on
	}

	public String getUsernameFromToken(String token) {

		final DecodedJWT decodedJWT = getDecodedJWT(token);

		return decodedJWT.getSubject();
	}

	public boolean validateToken(String token, String authenticatedUsername) {

		final String usernameFromToken = getUsernameFromToken(token);

		final boolean equalsUsername = usernameFromToken.equals(authenticatedUsername);
		final boolean tokenExpired = isTokenExpired(token);

		return equalsUsername && !tokenExpired;
	}

	private boolean isTokenExpired(String token) {

		final Date expirationDateFromToken = getExpirationDateFromToken(token);
		return expirationDateFromToken.before(new Date());
	}

	private Date getExpirationDateFromToken(String token) {

		final DecodedJWT decodedJWT = getDecodedJWT(token);

		return decodedJWT.getExpiresAt();
	}

	private DecodedJWT getDecodedJWT(String token) {

		final JWTVerifier jwtVerifier = JWT.require
				(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes())).build();

		return jwtVerifier.verify(token);
	}

}
