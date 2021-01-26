package ae.shj.deg.weatherNotes;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherNotesApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void loginWithValidUserThenAuthenticated() throws Exception {

		mockMvc.perform(formLogin("/login").user("user@test.com").password("12345"))
		.andExpect(authenticated().withUsername("user@test.com"));

	}

	@Test
	public void loginWithInvalidUserThenUnauthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
				.user("invalid")
				.password("invalidpassword");

		mockMvc.perform(login)
				.andExpect(unauthenticated());
	}

	@Test
	public void accessRegistrationThenOk() throws Exception {
		mockMvc.perform(get("/registration"))
				.andExpect(status().isOk());
	}

	@Test
	public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
		mockMvc.perform(get("/home"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser
	public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
		mockMvc.perform(get("/home"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER" )
	public void loginWithRoleUserThenExpectAdminPageForbidden() throws Exception {
		mockMvc.perform(get("/admin/notes"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void loginWithRoleAdminThenExpectAdminContent() throws Exception {
		mockMvc.perform(get("/admin/notes"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Manage Notes")));
	}

	@Test
	public void loginWithRoleUserThenExpectIndexPageRedirect() throws Exception {

		mockMvc.perform(formLogin().user("user@test.com").password("12345"))
				.andExpect(authenticated().withUsername("user@test.com"))
		.andExpect(redirectedUrl("/home"));
	}



}
