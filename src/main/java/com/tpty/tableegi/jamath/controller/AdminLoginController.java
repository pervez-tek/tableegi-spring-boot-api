package com.tpty.tableegi.jamath.controller;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.AdminLoginService;
import com.tpty.tableegi.jamath.service.ProfileImageService;
import com.tpty.tableegi.jamath.utils.AdminDtoConverterToEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/tableegi")
@CrossOrigin(origins = "${front.end.react.api.url}") // allow React dev server
@Slf4j
public class AdminLoginController {

    private final AdminLoginService adminLoginService;
    private final ProfileImageService profileImageService;

    @Autowired
    private Cloudinary cloudinary;

    private AdminLoginController(AdminLoginService adminLoginService, ProfileImageService profileImageService) {
        this.adminLoginService = adminLoginService;
        this.profileImageService = profileImageService;
    }

    @PostMapping("/addAdmins")
    public ResponseEntity<Object> addAdmins(@RequestBody JsonNode body) throws InvalidDataException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Add Admin Started by ");
        if (body.isArray()) {
            // ✅ Request body is a JSON array
            List<AdminLoginRequestDto> adminsDto = mapper.convertValue(body, new TypeReference<>() {
            });

            adminsDto.forEach(adminDto -> {
                try {
                    validateUserInputs(adminDto, "Add");
                    adminLoginService.addAdmin(adminDto);
                } catch (InvalidDataException e) {
                    throw new RuntimeException(e);
                }
            });
            log.info("Add Admin Completed =" + adminsDto.size() + ":" + "items");
            return ResponseEntity.ok("Received list with " + adminsDto.size() + " items");
        } else {
            // ✅ Request body is a single JSON object
            AdminLoginRequestDto adminDto = mapper.convertValue(body, AdminLoginRequestDto.class);
            validateUserInputs(adminDto, "Add");

            AdminLoginEntity adminLoginEntity = adminLoginService.addAdmin(adminDto);
            adminDto = AdminDtoConverterToEntity.toDTO(adminLoginEntity);
            log.info("Add Admin Completed by =" + adminLoginEntity.getUsername());
            return ResponseEntity.ok(adminDto);
        }
    }


    @PutMapping("/updateAdmins")
    public ResponseEntity<Object> updateAdmins(@RequestBody JsonNode body) throws InvalidDataException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Update Admin Started by ");
        if (body.isArray()) {
            // ✅ Request body is a JSON array
            List<AdminLoginRequestDto> adminsDto = mapper.convertValue(body, new TypeReference<>() {
            });

            adminsDto.forEach(adminDto -> {
                try {
                    validateUserInputs(adminDto, "Update");
                    adminLoginService.updateAdmin(adminDto);
                } catch (InvalidDataException e) {
                    throw new RuntimeException(e);
                }
            });
            log.info("Update Admin Completed =" + adminsDto.size() + ":" + "items");
            return ResponseEntity.ok("Updated list with " + adminsDto.size() + " items");
        } else {
            // ✅ Request body is a single JSON object
            AdminLoginRequestDto adminDto = mapper.convertValue(body, AdminLoginRequestDto.class);
            validateUserInputs(adminDto, "Update");
            AdminLoginEntity adminLoginEntity = adminLoginService.updateAdmin(adminDto);
            adminDto = AdminDtoConverterToEntity.toDTO(adminLoginEntity);
            log.info("Update Admin Completed by =" + adminLoginEntity.getUsername());
            return ResponseEntity.ok(adminDto);
        }
    }

    @DeleteMapping("/deleteAdmins")
    public ResponseEntity<Object> deleteAdmins(@RequestBody JsonNode body) throws InvalidDataException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Delete Admin Started by ");
        if (body.isArray()) {
            // ✅ Request body is a JSON array
            List<AdminLoginRequestDto> adminsDto = mapper.convertValue(body, new TypeReference<>() {
            });
            adminsDto.forEach(adminDto -> {
                try {
                    validateUserInputs(adminDto, "Delete");
                    adminLoginService.deleteAdmin(adminDto);
                } catch (InvalidDataException e) {
                    throw new RuntimeException(e);
                }
            });
            log.info("Delete Admin Completed =" + adminsDto.size() + ":" + "items");
            return ResponseEntity.ok("Deleted Items list with " + adminsDto.size());
        } else {
            // ✅ Request body is a single JSON object
            AdminLoginRequestDto adminDto = mapper.convertValue(body, AdminLoginRequestDto.class);
            validateUserInputs(adminDto, "Delete");

            adminLoginService.deleteAdmin(adminDto);
            log.info("Delete Admin Completed by =" + adminDto.getUsername());
        }
        return ResponseEntity.ok("Deleted the Admin data");

    }

    @GetMapping("/getAllAdmins")
    public ResponseEntity<Object> getAllAdmins() throws InvalidDataException {
        log.info("getAllAdmins completed ");
        return ResponseEntity.ok(AdminDtoConverterToEntity.toDTOList(adminLoginService.getAllAdmins()));
    }

    @GetMapping("/getAdmin/{admin}")
    public ResponseEntity<Object> getAdminByName
            (@PathVariable String admin) throws InvalidDataException {
        log.info("getAdminByName completed ");
        return ResponseEntity.ok(AdminDtoConverterToEntity.toDTO(adminLoginService.getAdmin(admin)));
    }

    public void validateUserInputs(AdminLoginRequestDto adminDto, String action) throws InvalidDataException {
        log.info("validateUserInputs started ");
        if (action.equals("Add")) {
            if (Objects.isNull(adminDto.getUsername()) || adminDto.getUsername().trim().isBlank()) {
                throw new InvalidDataException("Username is mandatory");
            }
        } else {
            if (Objects.isNull(adminDto.getUsername()) || adminDto.getUsername().trim().isBlank()) {
                throw new InvalidDataException("Username is mandatory");
            }
            if (Objects.isNull(adminDto.getId()) || adminDto.getId().trim().isBlank()) {
                throw new InvalidDataException("Admin Id is mandatory");
            }
            if (Objects.isNull(adminDto.getPassword()) || adminDto.getPassword().trim().isBlank()) {
                throw new InvalidDataException("Password is mandatory");
            }
        }
        log.info("validateUserInputs completed ");
    }

    //ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    @GetMapping("/googleUser")
    public Map<String, Object> getGoogleSinInInfo(@AuthenticationPrincipal OAuth2User principal) throws IOException {
        //return Objects.nonNull(principal) ?principal.getAttributes(): null; // contains email, name, picture
        log.info("getGoogleSinInInfo started ");
        Map<String, Object> attributes1 = Optional.ofNullable(principal)
                .map(OAuth2User::getAttributes)
                .orElse(null);
        Map<String, Object> attributes = null;
        if (Objects.nonNull(principal))
            attributes = new HashMap<>(principal.getAttributes());

        log.info("Map:" + attributes);
        if (attributes != null && attributes.containsKey("picture")) {
            String imageUrl = (String) attributes.get("picture");
            String name = (String) attributes.get("name");
            //String localPath = profileImageService.downloadAndSaveImage(imageUrl, name); // Replace Google URL with local path
            String cloudinaryUrl = uploadImageFromUrl(imageUrl, name);

            log.info("image=" + ":" + imageUrl);
            attributes.put("picture", imageUrl);
            attributes.put("image", cloudinaryUrl);

            log.info("getGoogleSinInInfo completed " + attributes.get("image"));
        }

        return attributes;

    }

    // Upload directly from a URL (Google profile picture)
    public String uploadImageFromUrl(String imageUrl, String name) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(imageUrl,
                ObjectUtils.asMap(
                        "resource_type", "image",
                        "folder", "tableegi-profiles/", // 👈 folder path
                        "public_id", "profile_pic" + name,
                        "overwrite", true // overwrite instead of duplicate
                ));
        return (String) uploadResult.get("secure_url");
    }


}
