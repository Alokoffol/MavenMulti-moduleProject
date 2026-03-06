
import com.example.repository.UserRepository;
import com.example.models.User;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository mockRepository;

    @Test
    public void testRegisterNewUser() {
        // 1. Настраиваем мок
        when(mockRepository.findByEmail("new@mail.com")).thenReturn(null);

        // 2. Создаём сервис с мок
        UserService service = new UserService(mockRepository);

        // 3. Вызываем метод
        boolean result = service.registerUser("new@mail.com");

        // 4. Проверяем результат
        assertTrue(result);

        // 5. Проверяем что save был вызван
        verify(mockRepository).save(any(User.class));
    }

    @Test
    public void testRegisterExistingUser() {
        // 1. Настраиваем мок
        User existingUser = new User("existing@mail.com");
        when(mockRepository.findByEmail("existing@mail.com")).thenReturn(existingUser);

        // 2. Создаём сервис
        UserService service = new UserService(mockRepository);

        // 3. Вызываем метод
        boolean result = service.registerUser("existing@mail.com");

        // 4. Проверяем результат
        assertFalse(result);

        // 5. Проверяем что save НЕ вызывался
        verify(mockRepository, never()).save(any());
    }

    @Test
    public void testVerifyMethods() {
        UserRepository mock = mock(UserRepository.class);
        UserService service = new UserService(mock);

        service.registerUser("test@mail.com");

        // Проверка: метод вызывался ровно 1 раз
        verify(mock).findByEmail("test@mail.com");

        // Проверка: метод вызывался 2 раза
        verify(mock, times(1)).findByEmail(anyString());

        // Проверка: метод НЕ вызывался
        verify(mock, never()).delete(anyString());

        // Проверка: метод вызывался хотя бы 1 раз
        verify(mock, atLeastOnce()).findByEmail(anyString());

        // Проверка: метод вызывался не больше 2 раз
        verify(mock, atMost(2)).findByEmail(anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterWithInvalidEmail() {
        UserRepository mock = mock(UserRepository.class);

        // может бросать исключения
        when(mock.findByEmail(null)).thenThrow(new IllegalArgumentException("Email не может быть null"));

        UserService service = new UserService(mock);
        service.registerUser(null); // должно бросить исключение
    }

    @Test
    public void testDeleteUser() {
        UserRepository mock = mock(UserRepository.class);
        User user = new User("delete@mail.com");

        when(mock.findByEmail("delete@mail.com")).thenReturn(user);

        UserService service = new UserService(mock);
        boolean result = service.deleteUser("delete@mail.com");

        assertTrue(result);
        verify(mock).delete("delete@mail.com");
    }

    @Test
    public void testDeleteNonExistingUser() {
        UserRepository mock = mock(UserRepository.class);

        when(mock.findByEmail("notfound@mail.com")).thenReturn(null);

        UserService service = new UserService(mock);
        boolean result = service.deleteUser("notfound@mail.com");

        assertFalse(result);
        verify(mock, never()).delete(anyString());
    }
}