import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.andrz.project3.entity.Role;
import com.andrz.project3.repository.UserRepository;
import com.andrz.project3.service.UserService;
import com.andrz.project3.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void testMapRolesToAuthorities() {
        // Create a mock list of roles
        List<Role> roles = Arrays.asList(new Role("ROLE_ADMIN"), new Role("ROLE_USER"));

        // Create a UserService implementation
        UserService userService = new UserServiceImpl(userRepository);

        // Define the behavior of userRepository in the UserServiceImpl constructor
        // For example: when(userRepository.someMethod()).thenReturn(someValue);

        // Call the mapRolesToAuthorities method
        Collection<? extends GrantedAuthority> authorities = userService.mapRolesToAuthorities(roles);

        // Assert that the authorities collection is not null
        assertNotNull(authorities);

        // Assert the size and content of the authorities collection
        assertEquals(2, authorities.size());
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }
}