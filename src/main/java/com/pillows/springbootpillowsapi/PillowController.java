package com.pillows.springbootpillowsapi;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.pillows.springbootpillowsapi.domain.Pillow;
import com.pillows.springbootpillowsapi.repo.PillowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/products")
public class PillowController {

    @Autowired
    private PillowRepo pillowRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("")
    public List<Pillow> getPillows ()
    {
        List<Pillow> pillowsList = pillowRepo.findAll();
        return pillowsList;

    }

    @GetMapping("/{id}")
    public Pillow getPillowById (@PathVariable(value = "id") Integer pillowId)
    {
        Pillow pillow = pillowRepo.findById(pillowId).get();

        return pillow;
    }

    @PostMapping("")
    public Pillow createPillow (@RequestParam("pillowName") String pillowName,
                                @RequestParam("shortDscrpt") String shortDscrpt,
                                @RequestParam("description") String description,
                                @RequestParam("cloth") String cloth,
                                @RequestParam("fabricStructure") String fabricStructure,
                                @RequestParam("filler") String filler,
                                @RequestParam("size") String size,
                                @RequestParam("price") Double price,
                                @RequestParam("file") MultipartFile file,
                                HttpServletRequest request) throws IOException {

        String user = (String) request.getAttribute("userName");
        System.out.println(user);

        Pillow pillow = new Pillow(null, pillowName, shortDscrpt, description, cloth, fabricStructure, filler, size, price, null);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String uploadDirectory = System.getProperty("user.dir") + "/uploads";
            File uploadDir = new File(uploadDirectory);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + '.' + file.getOriginalFilename();
            file.transferTo(new File(uploadDirectory + "/" + resultFileName));

            pillow.setFileName(resultFileName);
        }

        Pillow savedPillow = pillowRepo.save(pillow);

        return savedPillow;
    }

    @PostMapping("/image/{id}")
    public ResponseEntity<Pillow> addPillowImage (@PathVariable(value = "id") Integer pillowId,
                                @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String uploadDirectory = System.getProperty("user.dir") + "/uploads";
            File uploadDir = new File(uploadDirectory);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + '.' + file.getOriginalFilename();
            file.transferTo(new File(uploadDirectory + "/" + resultFileName));

            Pillow pillow = pillowRepo.findById(pillowId).get();
            pillow.setFileName(resultFileName);
            final Pillow newPillow = pillowRepo.save(pillow);
            return ResponseEntity.ok(newPillow);
        }

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pillow> updatePillow (@PathVariable(value = "id") Integer pillowId,
                                                @RequestParam("pillowName") String pillowName,
                                                @RequestParam("shortDscrpt") String shortDscrpt,
                                                @RequestParam("description") String description,
                                                @RequestParam("cloth") String cloth,
                                                @RequestParam("fabricStructure") String fabricStructure,
                                                @RequestParam("filler") String filler,
                                                @RequestParam("size") String size,
                                                @RequestParam("price") String price,
                                                @RequestParam(value = "file", required = false) MultipartFile file,
                                                HttpServletRequest request) throws IOException {
        Pillow pillow = pillowRepo.findById(pillowId).get();

        pillow.setPillowName(pillowName);
        pillow.setShortDscrpt(shortDscrpt);
        pillow.setDescription(description);
        pillow.setCloth(cloth);
        pillow.setFabricStructure(fabricStructure);
        pillow.setFiller(filler);
        pillow.setSize(size);
        pillow.setPrice(Double.parseDouble((price)));

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String uploadDirectory = System.getProperty("user.dir") + "/uploads";
            File uploadDir = new File(uploadDirectory);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + '.' + file.getOriginalFilename();
            file.transferTo(new File(uploadDirectory + "/" + resultFileName));

            pillow.setFileName(resultFileName);
        }

        final Pillow newPillow = pillowRepo.save(pillow);
        return ResponseEntity.ok(newPillow);
    }

//    @DeleteMapping("/{id}")
//    public Map<String, Boolean> deletePillow (@PathVariable(value = "id") Integer pillowId)
//    {
//        Pillow pillow = pillowRepo.findById(pillowId).get();
//
//        pillowRepo.delete(pillow);
//
//        System.out.println(user);
//
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return response;
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePillow (@PathVariable(value = "id") Integer pillowId)
    {
        if (pillowRepo.findById(pillowId).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        Pillow pillow = pillowRepo.findById(pillowId).get();

        pillowRepo.delete(pillow);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
