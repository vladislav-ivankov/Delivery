package by.ivankov.delivery.service.impl;

import by.ivankov.delivery.config.WebMvcConfig;
import by.ivankov.delivery.exception.ServiceException;
import by.ivankov.delivery.model.User;
import by.ivankov.delivery.repository.UserRepository;
import by.ivankov.delivery.security.RegistrationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final WebMvcConfig mvcConfig;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, WebMvcConfig mvcConfig) {
        this.userRepository = userRepository;
        this.mvcConfig = mvcConfig;
    }

    public void save(User user, String role) throws ServiceException {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new ServiceException("Password mismatch");
        }
        if (user.getPassword().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isEmpty() ||
                user.getPhoneNumber().describeConstable().isEmpty() || user.getConfirmPassword().isEmpty()) {
            throw new ServiceException("All fields must be filled");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ServiceException("Username already exists");
        }
        user.setPassword(mvcConfig.getPasswordEncoder().encode(user.getPassword()));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return new RegistrationDetails(user.get());
    }

    public void givePagesByRole(Authentication authentication, Model model) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("loginUrl", "/guest/login");
            model.addAttribute("regUrl", "/guest/reg");
        } else {
            model.addAttribute("loginUrl", "/user/cart");
            model.addAttribute("regUrl", "/user/logout");
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                model.addAttribute("role", "ADMIN");
                model.addAttribute("editUsersUrl", "/admin/editUser");
                model.addAttribute("editProductsUrl", "/admin/editProduct");
            } else {
                model.addAttribute("role", "USER");
            }
        }
    }
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
