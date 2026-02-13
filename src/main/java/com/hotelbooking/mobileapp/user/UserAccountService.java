package com.hotelbooking.mobileapp.user;

import com.hotelbooking.mobileapp.util.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    public UserAccount createUserAccount(UserAccount userAccount) {
        if (userAccount.getId() == null || userAccount.getId().isEmpty()) {
            userAccount.setId(idGeneratorService.generateUserId());
        }
        return userAccountRepository.save(userAccount);
    }

    public Optional<UserAccount> findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    public Optional<UserAccount> findById(String id) {
        return userAccountRepository.findById(id);
    }

    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }

    public boolean existsByEmail(String email) {
        return userAccountRepository.existsByEmail(email);
    }

    public Optional<UserAccount> findByPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return Optional.empty();
        }
        
        // Try exact match first
        Optional<UserAccount> exactMatch = userAccountRepository.findByPhone(phone);
        if (exactMatch.isPresent()) {
            return exactMatch;
        }
        
        // Try phone variations (normalize and try different formats)
        String normalized = normalizePhone(phone);
        String withoutPlus = normalized.startsWith("+") ? normalized.substring(1) : normalized;
        String withoutCountry = normalized.startsWith("+91") && normalized.length() == 13 
            ? normalized.substring(3) 
            : (normalized.length() > 10 ? normalized.substring(normalized.length() - 10) : normalized);
        
        List<UserAccount> variations = userAccountRepository.findByPhoneVariations(
            normalized, 
            withoutPlus, 
            withoutCountry
        );
        
        if (!variations.isEmpty()) {
            return Optional.of(variations.get(0));
        }
        
        return Optional.empty();
    }
    
    /**
     * Normalize phone number (same logic as OtpService).
     */
    private String normalizePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) return "";
        
        String cleaned = phone.trim()
            .replaceAll("\\s+", "")
            .replaceAll("-", "")
            .replaceAll("[()]", "");
        
        if (cleaned.startsWith("+91") && cleaned.length() == 13) {
            return cleaned;
        }
        if (cleaned.startsWith("91") && cleaned.length() == 12) {
            return "+" + cleaned;
        }
        if (cleaned.length() == 10 && !cleaned.startsWith("+") && !cleaned.startsWith("91")) {
            return "+91" + cleaned;
        }
        if (cleaned.length() == 11 && cleaned.startsWith("0")) {
            return "+91" + cleaned.substring(1);
        }
        
        return cleaned.startsWith("+") ? cleaned : "+" + cleaned;
    }

    public boolean existsByPhone(String phone) {
        return findByPhone(phone).isPresent();
    }

    public UserAccount updateUserAccount(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }
}

